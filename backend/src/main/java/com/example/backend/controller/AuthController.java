package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.GoogleAuthService;
import com.example.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final JwtUtil jwtUtil;

    public AuthController(GoogleAuthService googleAuthService,JwtUtil jwtUtil) {
        this.googleAuthService = googleAuthService;
        this.jwtUtil=jwtUtil;
    }

    @PostMapping("/google")
    public Map<String, String> loginWithGoogle(@RequestBody Map<String, String> body,
                                               HttpServletRequest request) {

        String idToken = body.get("token");

        try {
            User user = googleAuthService.authenticate(idToken, request);
            String jwt = jwtUtil.generateToken(user.getId(), user.getName());
            return Map.of("token", jwt,"userName",user.getName(),"userId",user.getId().toString());

        } catch (Exception e) {
            throw new RuntimeException("Google認証に失敗しました: " + e.getMessage());
        }
    }
}