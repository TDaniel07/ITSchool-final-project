package com.itschool.school_planner.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

    public User(){

    }

    public User(String username, String password, String email, boolean active, LocalDate registrationDate){
        validatePassword(password);
        validateEmail(email);
        validateUsername(username);

        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.registrationDate = registrationDate;
    }

    private void validatePassword(String password){
        if(password == null || password.length() < 8)
            throw new IllegalArgumentException("Invalid Password Entered");
    }

    private void validateEmail(String email){
        if(email == null || !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
            throw new IllegalArgumentException("Invalid email entered");
    }

    private void validateUsername(String username){
        if(username == null)
            throw new IllegalArgumentException("Username cannot be null");

        if(username.length() < 5 || username.length() > 15)
            throw new IllegalArgumentException("Username must be between 5 and 15 characters long");
    }

    public static class Builder{
        private String username;
        private String password;
        private String email;
        private boolean active;
        private LocalDate registrationDate;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Builder registrationDate(LocalDate registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public User build(){
            return new User(username, password, email, active, registrationDate);
        }
    }

    public static Builder builder(){
        return new Builder();
    }

}
