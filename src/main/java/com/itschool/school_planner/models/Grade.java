package com.itschool.school_planner.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    int value;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonBackReference
    private Subject subject;

    public Grade(){

    }

    public Grade(int value){
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getValue() {
        return value;
    }

    public Subject getSubject() {
        return subject;
    }


}
