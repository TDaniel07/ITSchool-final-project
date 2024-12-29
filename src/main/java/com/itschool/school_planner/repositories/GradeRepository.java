package com.itschool.school_planner.repositories;

import com.itschool.school_planner.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
