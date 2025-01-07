package com.itschool.school_planner.services.impl;

import com.itschool.school_planner.dtos.CreateUserDto;
import com.itschool.school_planner.dtos.GetUserDto;
import com.itschool.school_planner.models.Grade;
import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.repositories.UserRepository;
import com.itschool.school_planner.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

        User.validatePassword(userDto.getPassword());
        User.validateEmail(userDto.getEmail());
        User.validateUsername(userDto.getUsername());

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

    @Override
    public GetUserDto getUserByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow();

        return new GetUserDto(user.getId(), user.getUsername(), user.getEmail(), user.isActive(), user.getRegistrationDate(), user.getSubjects());
    }

    @Override
    public GetUserDto getUserById(long id){
        User user = userRepository.findById(id).orElseThrow();

        return new GetUserDto(user.getId(), user.getUsername(), user.getEmail(), user.isActive(), user.getRegistrationDate(), user.getSubjects());
    }

    @Override
    public List<GetUserDto> getAllUsers(){
        List<User> users = userRepository.findAll();

        List<GetUserDto> userDtos = users.stream()
                .map((user) -> new GetUserDto(user.getId(), user.getUsername(), user.getEmail(), user.isActive(), user.getRegistrationDate(), user.getSubjects()))
                .toList();

        return userDtos;
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username){
        if(!userRepository.existsByUsername(username))
            throw new NoSuchElementException("User doesn't exist");

        userRepository.deleteByUsername(username);
    }
}
