package com.example.backend.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.backend.model.Skill}
 */
@Value
public class SkillDto implements Serializable {
    Integer id;
    String name;
    Integer level;
    String description;
    Boolean isFramework;
    String category;
}