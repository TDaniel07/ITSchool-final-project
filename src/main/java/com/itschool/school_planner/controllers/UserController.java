package com.itschool.school_planner.controllers;

import com.itschool.school_planner.dtos.CreateGradeDto;
import com.itschool.school_planner.dtos.CreateSubjectDto;
import com.itschool.school_planner.dtos.CreateUserDto;
import com.itschool.school_planner.dtos.GetUserDto;
import com.itschool.school_planner.models.Grade;
import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.services.GradeService;
import com.itschool.school_planner.services.SubjectService;
import com.itschool.school_planner.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "APIs for managing users, subjects and grades")
public class UserController {
    private final UserService userService;
    private final SubjectService subjectService;
    public final GradeService gradeService;

    public UserController(UserService userService, SubjectService subjectService, GradeService gradeService){
        this.userService = userService;
        this.subjectService = subjectService;
        this.gradeService = gradeService;
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users.")
    @GetMapping
    public ResponseEntity<?> getUsers(){
        List<GetUserDto> getUserDtos = userService.getAllUsers();

        return ResponseEntity.ok(getUserDtos);
    }

    @Operation(summary = "Get user by username", description = "Retrieve a user by their username.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found."),
            @ApiResponse(responseCode = "400", description = "User not found.")
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        try {
            GetUserDto getUserDto = userService.getUserByUsername(username);
            return ResponseEntity.ok().body(getUserDto);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @Operation(summary = "Get user subjects by username", description = "Retrieve all subjects of a user by their username.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subjects."),
            @ApiResponse(responseCode = "400", description = "User not found.")
    })
    @GetMapping("/username/{username}/subjects")
    public ResponseEntity<?> getUserSubjectsByUsername(@PathVariable String username){
        try {
            List<Subject> subjects = subjectService.getSubjectsByUsername(username);
            return ResponseEntity.ok().body(subjects);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found."),
            @ApiResponse(responseCode = "400", description = "User not found.")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try{
            GetUserDto getUserDto = userService.getUserById(id);
            return ResponseEntity.ok().body(getUserDto);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @Operation(summary = "Get user subjects by ID", description = "Retrieve all subjects of a user by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subjects."),
            @ApiResponse(responseCode = "400", description = "User not found.")
    })
    @GetMapping("/id/{id}/subjects")
    public ResponseEntity<?> getUserSubjectsById(@PathVariable long id){
        try {
            List<Subject> subjects = subjectService.getSubjectsById(id);

            return ResponseEntity.ok(subjects);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Add subject by username", description = "Add a subject to a user by their username.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject added successfully."),
            @ApiResponse(responseCode = "400", description = "User not found or invalid input.")
    })
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

    @Operation(summary = "Add a new user", description = "Create and save a new user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input.")
    })
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

    @Operation(summary = "Add grade by username", description = "Add a grade to a specific subject for a user by their username.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grade added successfully."),
            @ApiResponse(responseCode = "400", description = "User or subject not found or invalid input.")
    })
    @PostMapping("/username/{username}/subjects/{subjectName}/grades")
    public ResponseEntity<?> addGradeByUsername(@PathVariable String username, @PathVariable String subjectName, @RequestBody CreateGradeDto gradeDto){
        try {
            Grade grade = gradeService.addGradeByUsername(username, subjectName, gradeDto);
            return ResponseEntity.ok().body(grade);
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete user by username", description = "Delete a user by their username.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully."),
            @ApiResponse(responseCode = "400", description = "User not found.")
    })
    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username){
        try{
            userService.deleteUserByUsername(username);
            return ResponseEntity.ok("User deleted");
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete subject by username", description = "Delete a subject for a user by their username and subject name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject deleted successfully."),
            @ApiResponse(responseCode = "400", description = "User or subject not found.")
    })
    @DeleteMapping("/username/{username}/subjects/{subjectName}")
    public ResponseEntity<?> deleteSubjectByUsername(@PathVariable String username, @PathVariable String subjectName){
        try{
            subjectService.deleteSubjectByUsername(username, subjectName);
            return ResponseEntity.ok("Subject deleted");
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete grade by ID", description = "Delete a grade by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grade deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Grade not found.")
    })
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
