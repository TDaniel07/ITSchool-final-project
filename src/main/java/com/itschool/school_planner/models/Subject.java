package com.itschool.school_planner.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Grade> grades = new ArrayList<>();

    public Subject(){

    }

    public Subject(String name, LocalDate dateCreated){
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }
}
