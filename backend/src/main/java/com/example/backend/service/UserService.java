package com.example.backend.service;

import com.example.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Integer providerId);
    User save(User user);
    User update(Integer id, User user);
    void deleteById(Integer id);
}