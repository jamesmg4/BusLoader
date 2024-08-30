package com.example.demo.events;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import com.example.demo.model.Attendee;
import com.example.demo.model.Event;
import com.example.demo.model.Student;
import com.example.demo.repositories.AttendeeRepository;
import com.example.demo.repositories.StudentRepository;

@RepositoryEventHandler
public class EventEventHandler {
    
    Logger logger = Logger.getLogger("Class EventEventHandler");

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AttendeeRepository attendeeRepository;

    public EventEventHandler()
    {
        logger.info("Constructed EventEventHandler");
    }

    @HandleBeforeCreate
    public void handleBeforeCreate(Event event){
        logger.info("Hello handleEventBeforeSave!");
        Iterable<Student> students = studentRepository.findAll();
        List<Attendee> attendeeList = new ArrayList<Attendee>();
        for(Student s : students){
            logger.info(s.getFirstName());
            Attendee attendee = new Attendee();
            attendee.setStudent(s);
            attendeeRepository.save(attendee);
            attendeeList.add(attendee);
        }
        for(Attendee a : attendeeList){
            a.setEvent(event);
        }
        
        event.setAttendees(attendeeList);
    }
}
