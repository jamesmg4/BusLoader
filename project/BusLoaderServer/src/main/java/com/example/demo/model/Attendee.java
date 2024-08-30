package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean onBus;
    private boolean absent;
    
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean isOnBus() {
        return onBus;
    }

    public void setOnBus(boolean onBus) {
        this.onBus = onBus;
    }
    public boolean isAbsent() {
        return absent;
    }
    public void setAbsent(boolean isAbsent) {
        this.absent = isAbsent;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    
    
}
