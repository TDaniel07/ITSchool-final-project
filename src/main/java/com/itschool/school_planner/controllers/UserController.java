package com.itschool.school_planner.controllers;

import com.itschool.school_planner.dtos.CreateUserDto;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody CreateUserDto userDto){
        try {
            User savedUser = userService.addUser(userDto);
            return ResponseEntity.ok(savedUser);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
