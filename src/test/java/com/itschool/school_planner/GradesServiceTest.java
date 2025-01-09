package com.itschool.school_planner;

import com.itschool.school_planner.dtos.CreateGradeDto;
import com.itschool.school_planner.models.Grade;
import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.repositories.GradeRepository;
import com.itschool.school_planner.repositories.UserRepository;
import com.itschool.school_planner.services.impl.GradeServiceImpl;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GradesServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeServiceImpl gradeService;

    private User user;
    private Subject subject;

    @BeforeEach
    void setup(){
        user = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("password")
                .registrationDate(LocalDate.now())
                .active(true)
                .build();

        subject = new Subject();
        subject.setId(1);
        subject.setName("Matematica");
        subject.setGrades(new ArrayList<>());

        Grade grade = new Grade();

        grade.setId(1);
        grade.setSubject(subject);
        grade.setValue(10);

        subject.getGrades().add(grade);

        user.getSubjects().add(subject);
    }

    @Test
    void addGradesByUsername_ShouldAddGradeSuccessfully(){
        CreateGradeDto createGradeDto = new CreateGradeDto(10);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Grade addedGrade = gradeService.addGradeByUsername(user.getUsername(), user.getSubjects().getFirst().getName(), createGradeDto);

        assertNotNull(addedGrade);
        assertEquals(createGradeDto.getValue(), addedGrade.getValue());
        assertTrue(subject.getGrades().contains(addedGrade));
        verify(userRepository).findByUsername(user.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void addGradeByUsername_ShouldThrowException_WhenUserNotFound(){
        String unusedUsername = "username";
        String randomSubjectName = "chimie";
        CreateGradeDto createGradeDto = new CreateGradeDto(10);

        when(userRepository.findByUsername(unusedUsername)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> gradeService.addGradeByUsername(unusedUsername, randomSubjectName, createGradeDto));
    }

    @Test
    void addGradeByUsername_ShouldThrowException_WhenSubjectNotFound(){
        String unusedSubjectName = "biologie";
        CreateGradeDto createGradeDto = new CreateGradeDto(10);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(NoSuchElementException.class, () -> gradeService.addGradeByUsername(user.getUsername(), unusedSubjectName, createGradeDto));

        verify(userRepository).findByUsername(user.getUsername());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteGradeShouldDeleteGradeSuccessfully(){
        long existingGradeId = 1;

        when(gradeRepository.existsById(existingGradeId)).thenReturn(true);

        gradeService.deleteGrade(1);

        verify(gradeRepository).existsById(existingGradeId);
        verify(gradeRepository).deleteById(existingGradeId);
    }

    @Test
    void deleteGrade_ShouldThrowException_WhenGradeIdDoesNotExist(){
        long unusedId = 10;

        when(gradeRepository.existsById(unusedId)).thenReturn(false);

        assertThrows(NoSuchElementException.class, ()-> gradeService.deleteGrade(unusedId));

        verify(gradeRepository).existsById(unusedId);
        verify(gradeRepository, never()).deleteById(unusedId);
    }
}
