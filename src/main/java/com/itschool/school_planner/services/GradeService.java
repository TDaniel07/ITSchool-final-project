package com.itschool.school_planner.services;

import com.itschool.school_planner.dtos.CreateGradeDto;
import com.itschool.school_planner.models.Grade;

public interface GradeService{
    public Grade addGradeByUsername(String username, String subjectName, CreateGradeDto gradeDto);
}
