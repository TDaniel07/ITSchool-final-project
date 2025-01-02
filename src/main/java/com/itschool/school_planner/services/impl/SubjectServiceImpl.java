package com.itschool.school_planner.services.impl;

import com.itschool.school_planner.dtos.CreateSubjectDto;
import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;
import com.itschool.school_planner.repositories.SubjectRepository;
import com.itschool.school_planner.repositories.UserRepository;
import com.itschool.school_planner.services.SubjectService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(UserRepository userRepository, SubjectRepository subjectRepository){
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject addSubjectByUsername(String username, CreateSubjectDto subjectDto){
        User user = userRepository.findByUsername(username).orElseThrow();
        if(userRepository.existsByUsernameAndSubjectsName(username, subjectDto.getName()))
            throw new IllegalArgumentException("Subject name already exists");
        Subject subject = new Subject(subjectDto.getName(), LocalDate.now());

        subject.setUser(user);
        user.getSubjects().add(subject);

        userRepository.save(user);

        return subject;
    }

    @Override
    @Transactional
    public void deleteSubjectByUsername(String username, String subjectName){
        if(!userRepository.existsByUsername(username))
            throw new NoSuchElementException("User doesn't exist");
        if(!userRepository.existsByUsernameAndSubjectsName(username, subjectName))
            throw new NoSuchElementException("Subject doesn't exist");

        subjectRepository.deleteByNameAndUserUsername(subjectName, username);
    }
}
