package com.itschool.school_planner.services;

import com.itschool.school_planner.dtos.CreateUserDto;
import com.itschool.school_planner.models.Grade;
import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;

import java.util.List;

public interface UserService {
    public User addUser(CreateUserDto userDto);
    public User getUserByUsername(String username);
    public User getUserById(long id);
    public void deleteUserByUsername(String username);
}
