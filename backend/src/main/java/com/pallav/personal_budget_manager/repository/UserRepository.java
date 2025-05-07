package com.pallav.personal_budget_manager.repository;

import com.pallav.personal_budget_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
