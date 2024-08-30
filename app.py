

import os

from cs50 import SQL
from flask import Flask, flash, redirect, render_template, request, session
from flask_session import Session
from tempfile import mkdtemp
from werkzeug.security import check_password_hash, generate_password_hash

from helpers import apology, login_required, lookup, usd

# Configure application
app = Flask(__name__)

# Ensure templates are auto-reloaded
app.config["TEMPLATES_AUTO_RELOAD"] = True

# Custom filter
app.jinja_env.filters["usd"] = usd

# Configure session to use filesystem (instead of signed cookies)
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

# Configure CS50 Library to use SQLite database
db = SQL("sqlite:///finance.db")
#db.execute("CREATE TABLE portfolio (stock TEXT, shares INT, id INT, FOREIGN KEY (id) REFERENCES users(id))")
# Make sure API key is set
# export API_KEY=pk_fd06e9ea9b1742f69122832581d14692
if not os.environ.get("API_KEY"):
    raise RuntimeError("API_KEY not set")


@app.after_request
def after_request(response):
    """Ensure responses aren't cached"""
    response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
    response.headers["Expires"] = 0
    response.headers["Pragma"] = "no-cache"
    return response


@app.route("/")
@login_required
def index():
    """Show portfolio of stocks"""
    # get data ready to pass to the render template so that we can use jinja
    arr = db.execute("SELECT stock, SUM(shares) as shares FROM portfolio WHERE id = ? GROUP BY stock", session["user_id"])
    total = 0
    for i in arr:
        dict = lookup(i["stock"])
        i["name"] = dict["name"]
        i["price"] = dict["price"]
        total += i["price"] * i["shares"]
        i["total"] = usd(i["price"] * i["shares"])
        i["price"] = usd(i["price"])
    cash = db.execute("SELECT cash FROM users WHERE id = ?", session["user_id"])
    total += cash[0]["cash"]
    total = usd(total)
    usdCash = usd(cash[0]["cash"])

    return render_template("index.html", purchases=arr, cash=usdCash, total=total)


@app.route("/buy", methods=["GET", "POST"])
@login_required
def buy():
    # make sure request is post
    if request.method == "POST":
        symbol = request.form.get("symbol")
        shares = request.form.get("shares")
        # make sure an int is inputted
        try:
            shares = int(shares)
        except:
            return apology("Please enter an INTEGER")
        # make sure symbol can be worked with
        symbol = symbol.upper()
        # test all bad cases
        if lookup(symbol) == None:
            return apology("Enter a valid symbol")
        if not (shares) or shares < 1:
            return apology("Enter valid amount of shares")
        shareDict = lookup(symbol)
        sharePrice = shareDict["price"] * shares
        money = db.execute("SELECT cash, id FROM users WHERE id = ?", session["user_id"])
        if sharePrice > money[0]["cash"]:
            return apology("Not enough money")
        # update tables
        db.execute("INSERT INTO portfolio (stock, shares, cost, id, transactionDate) VALUES (?,?,?,?, datetime())",
                   symbol, shares, sharePrice, money[0]["id"])
        db.execute("UPDATE users SET cash = (cash - ?) WHERE id = ?", sharePrice, session["user_id"])
        return redirect("/")
    else:
        return render_template("buyStock.html")


@app.route("/history")
@login_required
def history():
    """Show history of transactions"""
    # prepare data for jinja
    arr = db.execute("SELECT stock, shares as shares, transactionDate FROM portfolio WHERE id = ?", session["user_id"])
    for i in arr:
        dict = lookup(i["stock"])
        i["name"] = dict["name"]
        i["price"] = dict["price"]
        i["total"] = usd(i["price"] * i["shares"])
        i["price"] = usd(i["price"])
    cash = db.execute("SELECT cash FROM users WHERE id = ?", session["user_id"])
    usdCash = usd(cash[0]["cash"])
    # input data into the template
    return render_template("history.html", purchases=arr, cash=usdCash)


@app.route("/login", methods=["GET", "POST"])
def login():
    """Log user in"""

    # Forget any user_id
    session.clear()

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

        # Ensure username was submitted
        if not request.form.get("username"):
            return apology("must provide username", 403)

        # Ensure password was submitted
        elif not request.form.get("password"):
            return apology("must provide password", 403)

        # Query database for username
        rows = db.execute("SELECT * FROM users WHERE username = ?", request.form.get("username"))

        # Ensure username exists and password is correct
        if len(rows) != 1 or not check_password_hash(rows[0]["hash"], request.form.get("password")):
            return apology("invalid username and/or password", 403)

        # Remember which user has logged in
        session["user_id"] = rows[0]["id"]

        # Redirect user to home page
        return redirect("/")

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("login.html")


@app.route("/logout")
def logout():
    """Log user out"""

    # Forget any user_id
    session.clear()

    # Redirect user to login form
    return redirect("/")


@app.route("/quote", methods=["GET", "POST"])
@login_required
def quote():
    """Get stock quote."""
    # make sure it is post
    if request.method == "POST":
        symbol = request.form.get("symbol")
        quoteDict = lookup(symbol)
        if quoteDict == None:
            return apology("Symbol does not exist")
        # return the dictionary from look up
        return render_template("quoteResults.html", name=quoteDict["name"], price=usd(quoteDict["price"]), symbol=quoteDict["symbol"])
    else:
        return render_template("quote.html")


@app.route("/register", methods=["GET", "POST"])
def register():
    """Register user"""
    if request.method == "POST":
        # assign inputted info
        username = request.form.get("username")
        password = request.form.get("password")
        confirmation = request.form.get("confirmation")
        # check bad cases
        if not username:
            return apology("Please enter a username.")
        if not password:
            return apology("Please enter a password.")
        if not confirmation:
            return apology("Please confirm your password.")
        if confirmation != password:
            return apology("Please match password and confirmation password.")
        if db.execute("SELECT username FROM users WHERE username = (?)", username):
            return apology("Sorry, username already taken. Please enter another.")

        hash = generate_password_hash(password)

        db.execute("INSERT INTO users (username, hash, cash) VALUES(?,?,?)", username, hash, 10000)
        #session[db.execute("SELECT id FROM users WHERE username = username")]
        return render_template("login.html")

    else:
        return render_template("register.html")


@app.route("/sell", methods=["GET", "POST"])
@login_required
def sell():
    """Sell shares of stock"""
    if request.method == "POST":
        # assign varibles to inputs
        symbol = request.form.get("symbol")
        shares = request.form.get("shares")
        symbol = symbol.upper()
        try:
            shares = int(shares)
        except:
            return apology("Please enter an INTEGER")
        # check bad cases
        if not symbol:
            return apology("Please enter a symbol")
        if not shares:
            return apology("Please enter a share amount")
        if shares < 1:
            return apology("Please enter a share amount above 0")
        if lookup(symbol) == None:
            return apology("Enter a valid symbol")
        portfolio = db.execute(
            "SELECT SUM(shares) as shares FROM portfolio WHERE id = ? AND stock = ? GROUP BY stock", session["user_id"], symbol)
        if len(portfolio) != 1:
            return apology("length is not 1")
        if portfolio[0]["shares"] < shares:
            return apology("You do not have that many shares to sell")
        # get all the data ready to input into the webpage
        shareDict = lookup(symbol)
        sharePrice = shareDict["price"] * shares
        shares *= -1
        money = db.execute("SELECT cash, id FROM users WHERE id = ?", session["user_id"])
        # put all data into webpage
        db.execute("INSERT INTO portfolio (stock, shares, cost, id, transactionDate) VALUES (?,?,?,?, datetime())",
                   symbol, shares, sharePrice, money[0]["id"])
        db.execute("UPDATE users SET cash = (cash + ?) WHERE id = ?", sharePrice, session["user_id"])
        return redirect("/")
    else:
        stocks = db.execute("SELECT DISTINCT stock FROM portfolio WHERE id = ?", session["user_id"])
        return render_template("sell.html", arr=stocks)


@app.route("/addCash", methods=["GET", "POST"])
@login_required
def addCash():
    if request.method == "POST":
        # make sure input
        money = request.form.get("cashAdding")
        try:
            money = int(money)
        except:
            return apology("Please enter an INTEGER")
        if not money:
            return apology("Please enter a cash amount")
        if money < 1:
            return apology("Please enter a cash amount that is great than 0")
        # add money
        db.execute("UPDATE users SET cash = (cash + ?) WHERE id = ?", money, session["user_id"])
        # back to homepage
        return redirect("/")
    else:
        return render_template("addCash.html")