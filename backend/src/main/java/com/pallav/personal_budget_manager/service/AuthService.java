package com.pallav.personal_budget_manager.service;

import com.pallav.personal_budget_manager.entity.User;
import com.pallav.personal_budget_manager.repository.UserRepository;
import com.pallav.personal_budget_manager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;

    public String register(String email, String password) {
        if (userRepo.findByEmail(email).isPresent()) throw new RuntimeException("User exists");
        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
        return jwtUtil.generateToken(email);
    }

    public String login(String email, String password) {
        User user = userRepo.findByEmail(email).orElseThrow();
        if (!encoder.matches(password, user.getPassword())) throw new RuntimeException("Invalid");
        return jwtUtil.generateToken(email);
    }
}

