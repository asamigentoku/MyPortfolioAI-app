package com.example.backend.controller;

import com.example.backend.service.GroqProfileService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/make_portfolio")
public class AiController {

    private final GroqProfileService groqProfileService;

    public AiController(GroqProfileService groqProfileService) {
        this.groqProfileService = groqProfileService;
    }

    @PostMapping("/profile-structure")
    public ProfileStructureResponse generateProfileStructure(@RequestBody GenerateProfileRequest request) {
        JsonNode json = groqProfileService.generateStructuredProfile(request.profileInfo());
        return new ProfileStructureResponse(
                json.path("careers"),
                json.path("licenses"),
                json.path("projects"),
                json.path("skills")
        );
    }

    public record GenerateProfileRequest(
            String profileInfo
    ) {
    }

    public record ProfileStructureResponse(
            JsonNode careers,
            JsonNode licenses,
            JsonNode projects,
            JsonNode skills
    ) {
    }
}
