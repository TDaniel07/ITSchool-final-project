package com.itschool.school_planner;

import com.itschool.school_planner.dtos.CreateSubjectDto;
import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.repositories.SubjectRepository;
import com.itschool.school_planner.repositories.UserRepository;
import com.itschool.school_planner.services.impl.SubjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    private User user;

    @BeforeEach
    void setup(){
        user = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("password")
                .registrationDate(LocalDate.now())
                .active(true)
                .build();

        Subject subject1 = new Subject("Matematica", LocalDate.now());
        subject1.setId(1);
        subject1.setUser(user);

        Subject subject2 = new Subject("Fizica", LocalDate.now());
        subject2.setId(2);
        subject2.setUser(user);

        user.getSubjects().add(subject1);
        user.getSubjects().add(subject2);
    }

    @Test
    void getSubjectsByUsername_ShouldReturnSubjects(){
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        when(subjectRepository.findByUserUsername(user.getUsername())).thenReturn(user.getSubjects());

        List<Subject> subjects = subjectService.getSubjectsByUsername(user.getUsername());

        assertNotNull(subjects);
        assertEquals(user.getSubjects().size(), subjects.size());
        verify(userRepository).existsByUsername(user.getUsername());
        verify(subjectRepository).findByUserUsername(user.getUsername());
    }

    @Test
    void getSubjectsByUsername_ShouldThrowException_WhenUserNotFound(){
        String unusedUsername = "username";

        when(userRepository.existsByUsername(unusedUsername)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> subjectService.getSubjectsByUsername(unusedUsername));
        verify(userRepository).existsByUsername(unusedUsername);
    }

    @Test
    void addSubjectByUsername_ShouldAddSubjectSuccessfully(){
        String unusedSubjectName = "chimie";

        CreateSubjectDto createSubjectDto = new CreateSubjectDto(unusedSubjectName);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.existsByUsernameAndSubjectsName(user.getUsername(), createSubjectDto.getName())).thenReturn(false);

        Subject addedSubject = subjectService.addSubjectByUsername(user.getUsername(), createSubjectDto);

        assertNotNull(addedSubject);
        assertEquals(createSubjectDto.getName(), addedSubject.getName());
        assertEquals(user, addedSubject.getUser());
        verify(userRepository).findByUsername(user.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void addSubjectByUsername_ShouldThrowException_WhenSubjectAlreadyExists(){
        String existingSubjectName = user.getSubjects().getFirst().getName();

        CreateSubjectDto createSubjectDto = new CreateSubjectDto(existingSubjectName);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.existsByUsernameAndSubjectsName(user.getUsername(), existingSubjectName)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> subjectService.addSubjectByUsername(user.getUsername(), createSubjectDto));
        verify(userRepository).findByUsername(user.getUsername());
        verify(userRepository, never()).save(user);
    }

    @Test
    void deleteSubjectByUsername_ShouldDeleteSubjectSuccesfully(){
        String existingSubjectName = user.getSubjects().getFirst().getName();

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        when(userRepository.existsByUsernameAndSubjectsName(user.getUsername(), existingSubjectName)).thenReturn(true);

        subjectService.deleteSubjectByUsername(user.getUsername(), existingSubjectName);

        verify(subjectRepository).deleteByNameAndUserUsername(existingSubjectName, user.getUsername());
    }

    @Test
    void deleteSubjectByUsername_ShouldThrowException_WhenUserNotFound(){
        String unusedUsername = "username";
        String randomSubjectName = "matematica";

        when(userRepository.existsByUsername(unusedUsername)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> subjectService.deleteSubjectByUsername(unusedUsername, randomSubjectName));
        verify(subjectRepository, never()).deleteByNameAndUserUsername(unusedUsername, randomSubjectName);
    }

    @Test
    void deleteSubjectByUsername_ShouldThrowException_WhenSubjectNotFound(){
        String unusedSubjectName = "biologie";

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        when(userRepository.existsByUsernameAndSubjectsName(user.getUsername(), unusedSubjectName)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> subjectService.deleteSubjectByUsername(user.getUsername(), unusedSubjectName));
        verify(subjectRepository, never()).deleteByNameAndUserUsername(unusedSubjectName, user.getUsername());
    }

    @Test
    void getSubjectsById_ShouldReturnSubjects(){
        long existingUserId = 1;

        when(userRepository.existsById(existingUserId)).thenReturn(true);
        when(subjectRepository.findByUserId(existingUserId)).thenReturn(user.getSubjects());

        List<Subject> subjects = subjectService.getSubjectsById(1);

        assertNotNull(subjects);
        assertEquals(user.getSubjects().size(), subjects.size());
        verify(userRepository).existsById(existingUserId);
        verify(subjectRepository).findByUserId(existingUserId);
    }

    @Test
    void getSubjectsById_ShouldThrowException_WhenUserNotFound(){
        long unusedUserId = 10;

        when(userRepository.existsById(unusedUserId)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> subjectService.getSubjectsById(unusedUserId));
        verify(userRepository).existsById(unusedUserId);
        verify(subjectRepository, never()).findByUserId(unusedUserId);
    }
}
