package com.example.backend.controller;

import com.example.backend.service.GroqProfileService;
import com.example.backend.service.ProfileCacheService;
import com.example.backend.service.ProfileSaveService;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/make_portfolio")
public class AiController {

    private static final Logger log = LoggerFactory.getLogger(AiController.class);

    private final GroqProfileService groqProfileService;
    private final ProfileCacheService profileCacheService;
    private final ProfileSaveService profileSaveService;

    public AiController(GroqProfileService groqProfileService,ProfileCacheService profileCacheService,ProfileSaveService profileSaveService) {
        this.groqProfileService = groqProfileService;
        this.profileCacheService = profileCacheService;
        this.profileSaveService=profileSaveService;
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

    @PostMapping("/profile-structure/cache_key")
    public ProfileStructureResponse getProfileByCacheKey(@RequestBody String cacheKey) throws Exception {
        JsonNode json = profileCacheService.get(cacheKey);
        if (json == null) {
            throw new IllegalArgumentException("キャッシュが見つかりません: " + cacheKey);
        }
        return new ProfileStructureResponse(
                cacheKey,
                json.path("careers"),
                json.path("licenses"),
                json.path("projects"),
                json.path("skills")
        );

    }

    @PostMapping("/profile-structure/save")
    public void saveProfileStructure(@RequestBody SaveProfileRequest request) throws Exception {
        JsonNode json = profileCacheService.get(request.cacheKey());
        if (json == null) {
            throw new IllegalArgumentException("キャッシュが見つかりません: " + request.cacheKey());
        }
        profileSaveService.saveAll(request.userId(), json);
    }

    public record GenerateProfileRequest(
            String profileInfo
    ) { }
    public record SaveProfileRequest(
            String cacheKey,
            Integer userId
    ) {}

    public record ProfileStructureResponse(
            String cacheKey,
            JsonNode careers,
            JsonNode licenses,
            JsonNode projects,
            JsonNode skills
    ) {
    }

}
