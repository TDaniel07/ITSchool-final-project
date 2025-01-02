package com.itschool.school_planner.repositories;

import com.itschool.school_planner.models.Subject;
import com.itschool.school_planner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);
    public boolean existsByUsername(String username);
    public Optional<User> findByUsername(String username);
    public boolean existsByUsernameAndSubjectsName(String username, String subjectName);
    public void deleteByUsername(String username);
    @Query("SELECT u.subjects FROM User u WHERE u.username = :username")
    public List<Subject> findSubjectsByUsername(String username);
}
