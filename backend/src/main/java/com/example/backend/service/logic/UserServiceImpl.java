package com.example.backend.service.logic;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override @Transactional(readOnly = true)
    public List<User> findAll() { return userRepository.findAll(); }

    @Override @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) { return userRepository.findById(id); }

    @Override
    public User save(User user) { return userRepository.save(user); }

    @Override
    public User update(Integer id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        existing.setName(user.getName());
        return userRepository.save(existing);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByProviderId(String providerId) {
        return userRepository.findByProviderId(providerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findBySecretSlug(String secretSlug) {
        return userRepository.findBySecretSlug(secretSlug);
    }

    @Override
    public User updatePublic(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        user.setIsPublic(!Boolean.TRUE.equals(user.getIsPublic()));
        return userRepository.save(user);
    }

    @Override
    public User updateSecretSlug(Integer userId, String newSlug) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        user.setSecretSlug(newSlug);
        return userRepository.save(user);
    }

    @Override
    public User updateName(Integer userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        user.setName(name);
        return userRepository.save(user);
    }
}
