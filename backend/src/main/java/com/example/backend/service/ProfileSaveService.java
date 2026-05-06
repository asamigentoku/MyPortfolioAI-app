package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ProfileSaveService {

    private final CareerRepository careerRepository;
    private final LicenseRepository licenseRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public ProfileSaveService(
            CareerRepository careerRepository,
            LicenseRepository licenseRepository,
            ProjectRepository projectRepository,
            SkillRepository skillRepository,
            UserRepository userRepository
    ) {
        this.careerRepository = careerRepository;
        this.licenseRepository = licenseRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveAll(Integer user_id, JsonNode json) {
        User user=userRepository.findById(user_id).orElseThrow();
        saveCareers(user, json.path("careers"));
        saveLicenses(user, json.path("licenses"));
        saveProjects(user, json.path("projects"));
        saveSkills(user, json.path("skills"));
    }

    private void saveCareers(User user, JsonNode careers) {
        for (JsonNode node : careers) {
            Career career = new Career();
            career.setUser(user);
            career.setCareerName(node.path("careerName").asText());
            career.setTime(LocalDateTime.parse(node.path("time").asText()));
            careerRepository.save(career);
        }
    }

    private void saveLicenses(User user, JsonNode licenses) {
        for (JsonNode node : licenses) {
            License license = new License();
            license.setUser(user);
            license.setLicenseName(node.path("licenseName").asText());
            license.setCreatedAt(LocalDate.parse(node.path("createdAt").asText()));
            licenseRepository.save(license);
        }
    }

    private void saveProjects(User user, JsonNode projects) {
        for (JsonNode node : projects) {
            Project project = new Project();
            project.setUser(user);
            project.setTitle(node.path("title").asText());
            project.setExplanation(node.path("explanation").asText());
            project.setUrl(node.path("url").asText(null));
            project.setGithubUrl(node.path("githubUrl").asText(null));
            project.setIsGithub(node.path("isGithub").asBoolean());
            projectRepository.save(project);
        }
    }

    private void saveSkills(User user, JsonNode skills) {
        for (JsonNode node : skills) {
            Skill skill = new Skill();
            skill.setUser(user);
            skill.setName(node.path("name").asText());
            skill.setLevel(node.path("level").asInt());
            skill.setDescription(node.path("description").asText());
            skill.setIsFramework(node.path("isFramework").asBoolean());
            skill.setCategory(node.path("category").asText());
            skillRepository.save(skill);
        }
    }
}