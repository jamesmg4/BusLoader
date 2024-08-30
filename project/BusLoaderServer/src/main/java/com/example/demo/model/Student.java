package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean busCaptain;

    @OneToMany(mappedBy = "student")
    private List<Attendee> attendee;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isBusCaptain() {
        return busCaptain;
    }
    public void setBusCaptain(boolean isBusCaptain) {
        this.busCaptain = isBusCaptain;
    }
    public List<Attendee> getAttendee() {
        return attendee;
    }
    public void setAttendee(List<Attendee> attendee) {
        this.attendee = attendee;
    }
    public Bus getBus() {
        return bus;
    }
    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public int getBusNumber() {
        return bus !=null ? bus.getBusNumber() : -1;
    }
    // standard getters and setters
}
