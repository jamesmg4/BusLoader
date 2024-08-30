# BUSLOADER
#### Video Demo:  https://youtu.be/gLOuBV9DMsw
## Description:

My problem is that my current highschool band program uses pieces of paper to account for over three hundred band members when traveling to and from events. Band members are transported to and from events using multiple bus. Each bus has a student assigned as a bus captain who records whether a band member is present on the bus using a piece of paper with everyone's name who should be present on the bus. The roster is assigned by the band directors to the bus captains and the band directors have to trust the student bus captains ensure the bus gets loaded properly. This can prove problematic as it does not provide my band directors any ability to see the loading process. By automating this process, it allows for a permanenant record of band member attendences which makes the whole process eaiser and more reliable. In addition, it allows for the band directors to see updates in attendence in real time.

My solution is to develop an application that the bus captains can use to administer an electronic roster of band member attendence at band events. The solution will comprise of a web-based server using Spring Boot, and Spring Data REST that is accessed by an iOS client application.

In order to limit the scope of the solution and develop something within a reasonable timeframe, a number of simplifying assuptions and limitations have been used to contrain the solution. The main limitations include:

* No security - anyone can call the web services and there is no authentication or authorization implemented. The client application does not provide authentication, and there is basic capabilty to pick the bus captain.
  
* One event = one journey - Typically, an event has an inbound and outbound journey. In my application, it only accounts for one or the other, so in order to model an event, two application events will have to be made for the inbound journey and outbound journey.
  
* Changes occur on server and not automatically sent to the client - If two users are modifying a dataset, changes made by one user will not immediately be visable to the second user. The second user will need to manually refresh their client application to see the data changes made by the first user.
  
* No validation - In the current implementation, no code has been added to validate whether duplicate entities exsist. For example, it is possible to add 10 of the same attendees to one event.
  
* No reporting abilities - There is no dashboard for admins, or band directors, to view the loading process.
  
* No user interface to configure students, buses, and events - All configuration would need to be done using the webservices directly using a tool such as cURL. This includes marking an event as complete.

* No persistant database - For development purposes, I used an in-memory database to allow schema changes to be made without having to delete or migrate data.
  
All of these limitation are things that ultimately would be addressed in a more refined version of the application.

I chose Spring Boot and Spring Data REST to avoid having to write a large code base of SQL queries and persistance, and conversion from SQL structures to web service structures. This would allow me to focus on application code.

### Server Code Walkthrough

The server consists of a Spring Boot project that consists of the Spring Data REST library, the H2 database library, and QueryDSL.

The project consists of the follow folder stucture
|Folder|Description|
|--------|------|
|Model| The entities are used to represent real world objects with their real world properties. Eg. Event, Student|
|Respositories| Using Annotations from the libraries, these interfaces define an entry point for each property designed in the model. Respositories allows for sorting, paging, and filtering of data.|
|Events|Contains handlers to pre or post process object creation|
|Projections|Control which properties are included for each entity and can be applied to repositories|
|Config|Configuration for database and registration for event handlers defined in the event folder|


!["BusLoader Entitiy Diagram"](Images/BusAppRelationshipModel.png)

This diagram depicts the entities and relationships in my project.

Each event can have many attendees, and an attendee represents a single student, who is assigned to a bus. One student for each bus is assigned the role bus captain. As indicated above, the model is a representation of these entities and relationships and the repositories provide access to these entities through REST-based web services. Eg. in order to add an event, an event object in JSON can be POST'ed to the event URL:

curl -X POST -d '{"name" : "Football Game", "notes" : "These are some notes about Football Game in November", "dateTime" : "2022-11-17T18:00:00", "activated" : true, "complete" : true}' -H "Content-Type:  application/json" localhost:8080/events

In order to automate initization of the database, I created a loaddata.sh script to excute curl statements like above to provide a set of test data.

### Client Code Walkthrough

The client consists of an iOS app that uses swift and swiftUI. 

|Folder|Description|
|---|---|
|Model|The entities are used to represent real world objects with their real world properties. Eg. Event, Student. This is the client analogy of the server model entities.|
|<-Root->|Classes to represent the views in the UI as well a class to load data from the web services.|

The flow of the application is as followed: 
 
1. The bus captain logs in by choosing his or her name from the list of avalible bus captains
2. The user is presented with a list of events ordered by date in descending order. The events are colored based on their activation and completion status.
   |Activated|Completed|Color|
   |---|---|---|
   |false|false|red|
   |false|true|N/A|
   |true|false|yellow|
   |true|true|green|
3. Selecting an event takes the user to a detail screen that displays the event's information and attendees on the bus for which the user is a bus captain.
4. The user can navigate to the notes view where the band director can give additional details about the event.
5. From the event detail view, the user can mark the attendees as present on the bus by swiping left and clicking on the revealed button. The text of the attendee will change from red to green indictating their presence on the bus.
   









