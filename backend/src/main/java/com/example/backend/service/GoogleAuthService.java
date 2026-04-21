package com.example.backend.service;
import com.example.backend.config.GoogleTokenVerifier;
import com.example.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.example.backend.model.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Service
public class GoogleAuthService {

    private final UserRepository userRepository;

    public GoogleAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String idToken, HttpServletRequest request) throws Exception {

        Payload payload = GoogleTokenVerifier.verify(idToken);

        String name = (String) payload.get("name");
        String providerId = payload.getSubject();

        User user = userRepository.findByProviderId(providerId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setProviderId(providerId);
                    newUser.setName(name);
                    return userRepository.save(newUser);
                });

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        user.getName(),
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                );

        SecurityContextHolder.getContext().setAuthentication(auth);

        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );
        return user;
    }
}
