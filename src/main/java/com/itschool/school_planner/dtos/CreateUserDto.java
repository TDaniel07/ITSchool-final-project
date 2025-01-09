package com.itschool.school_planner.dtos;

public class CreateUserDto{
    private String username;
    private String password;
    private String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    
    public CreateUserDto(){
        
    }
    
    public CreateUserDto(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
