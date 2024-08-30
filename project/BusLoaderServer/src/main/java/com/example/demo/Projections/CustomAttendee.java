package com.example.demo.Projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.example.demo.model.Attendee;
import com.example.demo.model.Event;
//import com.example.demo.model.Bus;
import com.example.demo.model.Student;

@Projection(name = "customAttendee", types = { Attendee.class })
public interface CustomAttendee {
    @Value("#{target.id}")
    long getId();

    Student getStudent();

    boolean isOnBus();
    
    boolean isAbsent();

    Event getEvent();
    //@Value("#(target.getStudent().getBus())")
    //Bus getBus();
}
