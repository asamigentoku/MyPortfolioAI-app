package com.example.backend.service;

import com.example.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Integer userId);
    User save(User user);
    User update(Integer id, User user);
    void deleteById(Integer id);
    Optional<User> findByProviderId(String providerId);
    Optional<User> findBySecretSlug(String secretSlug);
    User updatePublic(Integer userId);
    User updateSecretSlug(Integer userId, String secretSlug);
    User updateName(Integer userId, String name);
}