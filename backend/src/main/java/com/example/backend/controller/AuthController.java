package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.GoogleAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final GoogleAuthService googleAuthService;

    public AuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @PostMapping("/google")
    public Map<String, String> loginWithGoogle(@RequestBody Map<String, String> body,
                                               HttpServletRequest request) {

        String idToken = body.get("token");

        try {
            User user = googleAuthService.authenticate(idToken, request);
            return Map.of("status", "success", "username", user.getName());

        } catch (Exception e) {
            throw new RuntimeException("Google認証に失敗しました: " + e.getMessage());
        }
    }
}