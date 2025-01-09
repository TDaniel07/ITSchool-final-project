package com.itschool.school_planner.services;

import com.itschool.school_planner.dtos.CreateSubjectDto;
import com.itschool.school_planner.models.Subject;

import java.util.List;

public interface SubjectService {
    public Subject addSubjectByUsername(String username, CreateSubjectDto subjectDto);
    public void deleteSubjectByUsername(String username, String subjectName);
    public List<Subject> getSubjectsByUsername(String username);
    public List<Subject> getSubjectsById(long id);
}
