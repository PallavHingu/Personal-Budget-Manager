package com.pallav.personal_budget_manager.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    @PostConstruct
    public void init() {
        // In production, use a key from secure config, not hardcoded
        String secret = "a-very-strong-secret-key-should-be-at-least-256-bits-long";
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
