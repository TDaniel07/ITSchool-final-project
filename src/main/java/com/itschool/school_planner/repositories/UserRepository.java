package com.itschool.school_planner.repositories;

import com.itschool.school_planner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);
    public boolean existsByUsername(String username);
}
