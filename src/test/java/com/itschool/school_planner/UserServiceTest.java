package com.itschool.school_planner;

import com.itschool.school_planner.dtos.CreateUserDto;
import com.itschool.school_planner.dtos.GetUserDto;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.repositories.UserRepository;
import com.itschool.school_planner.services.UserService;
import com.itschool.school_planner.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void addUser_ShouldAddUser(){
        CreateUserDto createUserDto = new CreateUserDto("john_doe", "password", "john@example.com");
        User user = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("password123")
                .registrationDate(LocalDate.now())
                .active(true)
                .build();

        when(userRepository.existsByEmail(createUserDto.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(createUserDto.getUsername())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User addedUser = userService.addUser(createUserDto);

        assertNotNull(addedUser);
        assertEquals(createUserDto.getUsername(), addedUser.getUsername());
        assertEquals(createUserDto.getEmail(), addedUser.getEmail());
        assertEquals(createUserDto.getPassword(), addedUser.getPassword());
        assertNotNull(addedUser.getRegistrationDate());
        assertTrue(addedUser.isActive());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void addUser_ShouldThrowException_WhenEmailAlreadyExists(){
        CreateUserDto createUserDto = new CreateUserDto("john_doe", "password", "john@example.com");

        when(userRepository.existsByEmail(createUserDto.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.addUser(createUserDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void addUser_ShouldThrowException_whenUsernameAlreadyExists(){
        CreateUserDto createUserDto = new CreateUserDto("john_doe", "password", "john@example.com");

        when(userRepository.existsByUsername(createUserDto.getUsername())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.addUser(createUserDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserByUsername_ShouldReturnUser(){
        User user = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("password")
                .registrationDate(LocalDate.now())
                .active(true)
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        GetUserDto userDto = userService.getUserByUsername(user.getUsername());

        assertNotNull(userDto);
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getEmail(), user.getEmail());
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void getUserByUsername_ShouldThrowException_WhenUserNotFound(){
        String unusedUsername = "username";
        when(userRepository.findByUsername(unusedUsername)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getUserByUsername(unusedUsername));
        verify(userRepository).findByUsername(unusedUsername);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers(){
        User user1 = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("password")
                .registrationDate(LocalDate.now())
                .active(true)
                .build();

        User user2 = User.builder()
                .username("jane_doe")
                .email("jane@example.com")
                .password("password")
                .registrationDate(LocalDate.now())
                .active(true)
                .build();

        List<User> allUsers = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(allUsers);

        List<GetUserDto> foundUsers = userService.getAllUsers();

        assertNotNull(foundUsers);
        assertEquals(allUsers.size(), foundUsers.size());
        verify(userRepository).findAll();
    }

    @Test
    void deleteUserByUsername_ShouldDeleteUser(){
        String existingUsername = "username";

        when(userRepository.existsByUsername(existingUsername)).thenReturn(true);

        userService.deleteUserByUsername(existingUsername);

        verify(userRepository).deleteByUsername(existingUsername);
    }

    @Test
    void deleteUserByUsername_ShouldThrowException_WhenUserNotFound(){
        String unusedUsername = "username";

        when(userRepository.existsByUsername(unusedUsername)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> userService.deleteUserByUsername(unusedUsername));
        verify(userRepository, never()).deleteByUsername(unusedUsername);
    }
}
