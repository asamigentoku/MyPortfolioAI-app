package com.example.backend.dto;

import lombok.Value;

import java.util.List;

@Value
public class PublicProfileDto {
    PublicUserDto user;
    List<SkillDto> skills;
    List<LicenseDto> licenses;
    List<CareerDto> careers;
    List<ProjectDto> projects;
}
