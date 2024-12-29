package com.itschool.school_planner.services.impl;

import com.itschool.school_planner.dtos.CreateGradeDto;
import com.itschool.school_planner.models.Grade;
import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.repositories.GradeRepository;
import com.itschool.school_planner.repositories.UserRepository;
import com.itschool.school_planner.services.GradeService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GradeServiceImpl implements GradeService {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;

    public GradeServiceImpl(UserRepository userRepository, GradeRepository gradeRepository){
        this.userRepository = userRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public Grade addGradeByUsername(String username, String subjectName,CreateGradeDto gradeDto){
        User user = userRepository.findByUsername(username).orElseThrow();
        Subject subject = user.getSubjects().stream().filter((element) -> element.getName().equals(subjectName)).findFirst().orElseThrow(() -> new NoSuchElementException("Subject doesn't exist"));
        Grade grade = new Grade(gradeDto.getValue());
        grade.setSubject(subject);

        subject.getGrades().add(grade);

        userRepository.save(user);

        return grade;
    }
}
