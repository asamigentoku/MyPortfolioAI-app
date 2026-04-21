package com.example.backend.repository;

import com.example.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByUserId(Integer userId);
    List<Project> findByUserIdAndIsGithub(Integer userId, Boolean isGithub);
}