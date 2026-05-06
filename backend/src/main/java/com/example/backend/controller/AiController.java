package com.example.backend.controller;

import com.example.backend.service.GroqProfileService;
import com.example.backend.service.ProfileCacheService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/make_portfolio")
public class AiController {

    private final GroqProfileService groqProfileService;
    private final ProfileCacheService profileCacheService;

    public AiController(GroqProfileService groqProfileService,ProfileCacheService profileCacheService) {
        this.groqProfileService = groqProfileService;
        this.profileCacheService = profileCacheService;
    }

    @PostMapping("/profile-structure")
    public ProfileStructureResponse generateProfileStructure(@RequestBody GenerateProfileRequest request) throws Exception {
        JsonNode json = groqProfileService.generateStructuredProfile(request.profileInfo());

        String cacheKey = profileCacheService.save(json);
        return new ProfileStructureResponse(
                cacheKey,
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
            String cacheKey,
            JsonNode careers,
            JsonNode licenses,
            JsonNode projects,
            JsonNode skills
    ) {
    }
}
