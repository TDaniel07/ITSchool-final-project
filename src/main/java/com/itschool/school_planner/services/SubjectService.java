package com.itschool.school_planner.services;

import com.itschool.school_planner.dtos.CreateSubjectDto;
import com.itschool.school_planner.models.Subject;

public interface SubjectService {
    public Subject addSubjectByUsername(String username, CreateSubjectDto subjectDto);
}
