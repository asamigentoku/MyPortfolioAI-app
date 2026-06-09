package com.example.backend.service;

import com.example.backend.config.GoogleTokenVerifier;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GoogleAuthService {

    private final UserRepository userRepository;
    private final GoogleTokenVerifier googleTokenVerifier;

    public GoogleAuthService(UserRepository userRepository, GoogleTokenVerifier googleTokenVerifier) {
        this.userRepository = userRepository;
        this.googleTokenVerifier = googleTokenVerifier;
    }

    @Transactional
    public User authenticate(String idToken, HttpServletRequest request) throws Exception {

        Payload payload = googleTokenVerifier.verify(idToken);

        String name = "未設定";
        String providerId = payload.getSubject();
        User user = userRepository.findByProviderId(providerId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setProviderId(providerId);
                    newUser.setName(name);
                    newUser.setSecretSlug(UUID.randomUUID().toString().substring(0, 8));
                    return userRepository.save(newUser);
                });
        return user;
    }
}
