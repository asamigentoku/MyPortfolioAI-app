package com.example.backend.service;

import com.example.backend.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAll();
    Optional<Project> findById(Integer id);
    List<Project> findByUserId(Integer userId);
    Project save(Project project);
    Project update(Integer id, Project project);
    void deleteById(Integer id);
}
