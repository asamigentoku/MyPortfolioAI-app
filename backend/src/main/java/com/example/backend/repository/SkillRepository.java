package com.example.backend.repository;

import com.example.backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//extendsは<テーブル,主キーの型>
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    List<Skill> findByUserId(Integer userId);
    List<Skill> findByUserIdAndIsFramework(Integer userId, Boolean isFramework);
}