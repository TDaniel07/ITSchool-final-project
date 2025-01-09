package com.itschool.school_planner.services;

import com.itschool.school_planner.dtos.CreateUserDto;
import com.itschool.school_planner.dtos.GetUserDto;
import com.itschool.school_planner.models.Grade;
import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;

import java.util.List;

public interface UserService {
    public User addUser(CreateUserDto userDto);
    public GetUserDto getUserByUsername(String username);
    public GetUserDto getUserById(long id);
    public List<GetUserDto> getAllUsers();
    public void deleteUserByUsername(String username);
}
