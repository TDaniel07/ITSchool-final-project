package com.itschool.school_planner.repositories;

import com.itschool.school_planner.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    public void deleteByNameAndUserUsername(String name, String username);
}
