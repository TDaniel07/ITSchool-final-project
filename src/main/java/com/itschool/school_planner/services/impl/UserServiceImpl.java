package com.itschool.school_planner.services.impl;

import com.itschool.school_planner.dtos.CreateUserDto;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.repositories.UserRepository;
import com.itschool.school_planner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(CreateUserDto userDto){
        if(userDto == null)
            throw new IllegalArgumentException("User is null");
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new IllegalArgumentException("Email already exists");
        if(userRepository.existsByUsername(userDto.getUsername()))
            throw new IllegalArgumentException("Username already exists");

        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .registrationDate(LocalDate.now())
                .active(true)
                .build();

        userRepository.save(user);
        return user;
    }
}
