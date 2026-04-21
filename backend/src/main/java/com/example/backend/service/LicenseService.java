package com.example.backend.service;

import com.example.backend.model.License;

import java.util.List;
import java.util.Optional;

public interface LicenseService {
    List<License> findAll();
    Optional<License> findById(Integer id);
    List<License> findByUserId(Integer userId);
    License save(License license);
    License update(Integer id, License license);
    void deleteById(Integer id);
}
