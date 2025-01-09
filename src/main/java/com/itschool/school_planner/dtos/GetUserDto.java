package com.itschool.school_planner.dtos;

import com.itschool.school_planner.models.Subject;

import java.time.LocalDate;
import java.util.List;

public class GetUserDto {
    private Long id;
    private String username;
    private String email;
    private boolean active;
    private LocalDate registrationDate;
    private List<Subject> subjects;

    public GetUserDto(){

    }

    public GetUserDto(Long id, String username, String email, boolean active, LocalDate registrationDate, List<Subject> subjects){
        this.id = id;
        this.username = username;
        this.email = email;
        this.active = active;
        this.registrationDate = registrationDate;
        this.subjects = subjects;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public boolean isActive() {
        return active;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
