package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.mapper.*;
import com.example.backend.model.*;
import com.example.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public-profile")
@RequiredArgsConstructor
public class PublicProfileController {

    private final UserService userService;
    private final SkillService skillService;
    private final LicenseService licenseService;
    private final CareerService careerService;
    private final ProjectService projectService;

    private final PublicUserMapper publicuserMapper;
    private final SkillMapper skillMapper;
    private final LicenseMapper licenseMapper;
    private final CareerMapper careerMapper;
    private final ProjectMapper projectMapper;

    /**
     * シークレットスラグからユーザーの公開プロフィールを取得する。
     * 存在しないユーザー or 非公開の場合は 404 を返す。
     */
    @GetMapping("/slug/{secretSlug}")
    public ResponseEntity<PublicProfileDto> getPublicProfileBySlug(@PathVariable String secretSlug) {
        Optional<User> userOpt = userService.findBySecretSlug(secretSlug);

        // ユーザーが存在しない場合は 404
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();

        // 非公開プロフィールの場合は 404 を返す（セキュリティのため）
        if (!Boolean.TRUE.equals(user.getIsPublic())) {
            return ResponseEntity.notFound().build();
        }

        Integer userId = user.getId();

        // 1. ユーザー情報
        PublicUserDto userDto = publicuserMapper.toDto(user);

// 2. スキル
        List<Skill> skillsEntity = skillService.findByUserId(userId);
        List<SkillDto> skillsDto = skillsEntity.stream()
                .map(skillMapper::toDto)  // 1つずつ Skill → SkillDto に変換
                .toList();

// 3. 資格
        List<License> licensesEntity = licenseService.findByUserId(userId);
        List<LicenseDto> licensesDto = licensesEntity.stream()
                .map(licenseMapper::toDto)  // 1つずつ License → LicenseDto に変換
                .toList();

// 4. 経歴（時間順）
        List<Career> careersEntity = careerService.findByUserIdOrderByTimeDesc(userId);
        List<CareerDto> careersDto = careersEntity.stream()
                .map(careerMapper::toDto)  // 1つずつ Career → CareerDto に変換
                .toList();

// 5. プロジェクト
        List<Project> projectsEntity = projectService.findByUserId(userId);
        List<ProjectDto> projectsDto = projectsEntity.stream()
                .map(projectMapper::toDto)  // 1つずつ Project → ProjectDto に変換
                .toList();

// 6. 最終的に PublicProfileDto に詰める
        PublicProfileDto profile = new PublicProfileDto(userDto, skillsDto, licensesDto, careersDto, projectsDto);
        return ResponseEntity.ok(profile);
    }
}