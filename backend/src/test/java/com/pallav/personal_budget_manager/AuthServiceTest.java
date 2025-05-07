package com.pallav.personal_budget_manager;

import com.pallav.personal_budget_manager.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AuthServiceTest {
    @Autowired private AuthService authService;

    @Test
    public void testRegisterAndLogin() {
        String token = authService.register("test@example.com", "password");
        assertNotNull(token);

        String loginToken = authService.login("test@example.com", "password");
        assertNotNull(loginToken);
    }
}
