package com.example.demo.Projections;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.example.demo.model.Event;
//import com.example.demo.model.Bus;

@Projection(name = "customEvent", types = { Event.class })
public interface CustomEvent {
    @Value("#{target.ident}")
    long getId();

    String getName();

    String getNotes();

    LocalDateTime getDateTime();

    boolean isActivated();
    
    boolean isComplete();

    //@Value("#(target.getStudent().getBus())")
    //Bus getBus();
}
