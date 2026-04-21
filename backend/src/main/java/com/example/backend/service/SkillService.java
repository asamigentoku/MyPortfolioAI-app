package com.example.backend.service;

import com.example.backend.model.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    List<Skill> findAll();
    Optional<Skill> findById(Integer id);
    List<Skill> findByUserId(Integer userId);
    Skill save(Skill skill);
    Skill update(Integer id, Skill skill);
    void deleteById(Integer id);
}

