cat student.dat | xargs -I % curl -X POST -d % -H "Content-Type:  application/json" localhost:8080/students
cat bus.dat | xargs -I % curl -X POST -d % -H "Content-Type:  application/json" localhost:8080/busses
cat student_bus1.dat  | xargs -I % curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/busses/1"  %
cat student_bus2.dat  | xargs -I % curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/busses/2"  %
cat student_bus3.dat  | xargs -I % curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/busses/3"  %
cat event.dat | xargs -I % curl -X POST -d % -H "Content-Type:  application/json" localhost:8080/events

