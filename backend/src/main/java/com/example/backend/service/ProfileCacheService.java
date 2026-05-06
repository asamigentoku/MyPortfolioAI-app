package com.example.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class ProfileCacheService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public ProfileCacheService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    // 保存（30分間有効）
    public String save(JsonNode profile) throws Exception {
        String key = "profile:" + UUID.randomUUID();
        String json = objectMapper.writeValueAsString(profile);
        redisTemplate.opsForValue().set(key, json, Duration.ofMinutes(30));
        return key;
    }

    // 取得
    public JsonNode get(String key) throws Exception {
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) return null;
        return objectMapper.readTree(json);
    }
}