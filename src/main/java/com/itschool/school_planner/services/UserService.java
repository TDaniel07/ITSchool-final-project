package com.itschool.school_planner.services;

import com.itschool.school_planner.dtos.CreateUserDto;
import com.itschool.school_planner.models.User;

public interface UserService {
    public User addUser(CreateUserDto userDto);
    public User getUserByUsername(String username);
}
