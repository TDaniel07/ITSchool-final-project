package com.itschool.school_planner.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    private String username;
    private String password;
    private String email;

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getUsername() {
        return username;
    }
}
