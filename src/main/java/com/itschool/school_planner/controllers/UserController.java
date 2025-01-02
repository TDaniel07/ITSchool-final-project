package com.itschool.school_planner.controllers;

import com.itschool.school_planner.dtos.CreateGradeDto;
import com.itschool.school_planner.dtos.CreateSubjectDto;
import com.itschool.school_planner.dtos.CreateUserDto;
import com.itschool.school_planner.models.Grade;
import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.services.GradeService;
import com.itschool.school_planner.services.SubjectService;
import com.itschool.school_planner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final SubjectService subjectService;
    public final GradeService gradeService;

    public UserController(UserService userService, SubjectService subjectService, GradeService gradeService){
        this.userService = userService;
        this.subjectService = subjectService;
        this.gradeService = gradeService;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        try {
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok().body(user);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try{
            User user = userService.getUserById(id);
            return ResponseEntity.ok().body(user);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/username/{username}/subjects")
    public ResponseEntity<?> getUserSubjectsByUsername(@PathVariable String username){
        try {
            List<Subject> subjects = userService.getUserSubjectsByUsername(username);
            return ResponseEntity.ok().body(subjects);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/username/{username}/subjects")
    public ResponseEntity<?> addSubjectByUsername(@PathVariable String username, @RequestBody CreateSubjectDto subjectDto){
        try{
            Subject subject = subjectService.addSubjectByUsername(username, subjectDto);
            return ResponseEntity.ok().body(subject);
        }
        catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("User doesn't exist");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody CreateUserDto userDto){
        try {
            User savedUser = userService.addUser(userDto);
            return ResponseEntity.ok().body(savedUser);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/username/{username}/subjects/{subjectName}/grades")
    public ResponseEntity<?> addGradeByUsername(@PathVariable String username, @PathVariable String subjectName, @RequestBody CreateGradeDto gradeDto){
        try {
            Grade grade = gradeService.addGradeByUsername(username, subjectName, gradeDto);
            return ResponseEntity.ok().body(grade);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username){
        try{
            userService.deleteUserByUsername(username);
            return ResponseEntity.ok("User deleted");
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/username/{username}/subjects/{subjectName}")
    public ResponseEntity<?> deleteSubjectByUsername(@PathVariable String username, @PathVariable String subjectName){
        try{
            subjectService.deleteSubjectByUsername(username, subjectName);
            return ResponseEntity.ok("Subject deleted");
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/grade/{gradeId}")
    public ResponseEntity<?> deleteGrade(@PathVariable Long gradeId){
        try{
            gradeService.deleteGrade(gradeId);
            return ResponseEntity.ok("grade deleted");
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
