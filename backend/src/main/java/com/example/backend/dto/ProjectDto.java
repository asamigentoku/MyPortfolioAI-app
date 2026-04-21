package com.example.backend.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.backend.model.Project}
 */
@Value
public class ProjectDto implements Serializable {
    Integer id;
    String title;
    String explanation;
    String url;
    String githubUrl;
    Boolean isGithub;
    Set<SkillDto> skills;
}