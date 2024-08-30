package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int busNumber;

    @OneToMany(mappedBy = "bus")
    private List<Student> students;


    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getBusNumber() {
        return busNumber;
    }
    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }
    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    // standard getters and setters
}
