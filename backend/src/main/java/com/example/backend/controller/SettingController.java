package com.example.backend.controller;

import com.example.backend.dto.SettingDto;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class SettingController {

    private final UserService userService;

    // 公開/非公開をトグル → 現在の isPublic を返す
    @PutMapping("/public_setting/{userId}")
    public ResponseEntity<Boolean> togglePublic(@PathVariable Integer userId) {
        String tokenUserId = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        // パスの userId と一致しなければ 403
        if (!userId.toString().equals(tokenUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User updated = userService.updatePublic(userId);
        return ResponseEntity.ok(updated.getIsPublic());
    }

    // secretSlug を再生成して保存 → 新しい slug を返す
    @PutMapping("/change_secretid/{userId}")
    public ResponseEntity<Map<String, String>> changeSecretId(@PathVariable Integer userId) {
        String tokenUserId = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        // パスの userId と一致しなければ 403
        if (!userId.toString().equals(tokenUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String newSlug = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        userService.updateSecretSlug(userId, newSlug);
        return ResponseEntity.ok(Map.of("secretSlug", newSlug));
    }



    // 名前を変更 → 新しい名前を返す
    @PutMapping("/change_userName/{userId}")
    public ResponseEntity<String> changeName(
            @PathVariable Integer userId,
            @RequestBody String newName) {
        String tokenUserId = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        // パスの userId と一致しなければ 403

        if (!userId.toString().equals(tokenUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User updated = userService.updateName(userId, newName);
        return ResponseEntity.ok(updated.getName());
    }

    @GetMapping("/read_setting/{userId}")
    public ResponseEntity<SettingDto>  readSetting(@PathVariable Integer userId) {
        String tokenUserId = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        // パスの userId と一致しなければ 403
        if (!userId.toString().equals(tokenUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User user = userService.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(new SettingDto(user.getIsPublic(), user.getSecretSlug()));
    }
}
