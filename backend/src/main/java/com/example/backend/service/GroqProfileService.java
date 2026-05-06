package com.example.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GroqProfileService {

    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String model;

    public GroqProfileService(
            @Value("${groq.api-key:}") String apiKey,
            @Value("${groq.model:llama-3.3-70b-versatile}") String model
    ) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.apiKey = apiKey;
        this.model = model;
    }

    public JsonNode generateStructuredProfile(String profileInfo) {
        if (profileInfo == null || profileInfo.isBlank()) {
            throw new IllegalArgumentException("profileInfo is required");
        }
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GROQ_API_KEY is not configured");
        }

        String userPrompt = """
                あなたはソフトウェアエンジニアのポートフォリオ情報を整理するAIです。
                入力されたテキストから、学歴・職歴・資格・プロジェクト・スキルに関する情報を抽出し、
                次のJSONフォーマット「だけ」を日本語で返してください。

                {
                  "careers": [
                    { "careerName": "会社名や所属・役割など", "time": "2023-04-01T00:00:00" }
                  ],
                  "licenses": [
                    { "licenseName": "資格名", "createdAt": "2021-11-01" }
                  ],
                  "projects": [
                    {
                      "title": "プロジェクト名",
                      "explanation": "プロジェクトの概要",
                      "url": "https://example.com or null",
                      "githubUrl": "https://github.com/... or null",
                      "isGithub": true,
                      "skills": [
                        {
                          "name": "スキル名",
                          "level": 1,
                          "description": "スキルの説明",
                          "isFramework": false,
                          "category": "Backend|Frontend|Language|Database|DevOps|ML|Other など"
                        }
                      ]
                    }
                  ],
                  "skills": [
                    {
                      "name": "スキル名",
                      "level": 1,
                      "description": "スキルの説明",
                      "isFramework": false,
                      "category": "Backend|Frontend|Language|Database|DevOps|ML|Other など"
                    }
                  ]
                }

                必ず有効なJSONとして返してください。
                レスポンスの最初の文字は { でなければなりません。
                バッククォート(```)、```json、コメント、説明文は一切含めないでください。
                入力テキスト:
                %s
                """.formatted(profileInfo);

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", "あなたはJSONのみを返すAPIです。レスポンスは常に { から始まる有効なJSONだけを返してください。説明文やバッククォートは絶対に含めないでください。"),
                        Map.of("role", "user", "content", userPrompt)
                ),
                "temperature", 0.2  // 低めにすると指示に忠実になりやすい
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                GROQ_API_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        return parseJsonContent(response.getBody());
    }

    private JsonNode parseJsonContent(String body) {
        try {
            JsonNode root = objectMapper.readTree(body);
            String contentText = root.path("choices").path(0).path("message").path("content").asText();

            if (contentText == null || contentText.isBlank()) {
                throw new IllegalStateException("Groq response did not include message content");
            }

            // バッククォートのコードブロックを除去
            String cleaned = contentText
                    .replaceAll("```json\\s*", "")
                    .replaceAll("```\\s*", "")
                    .trim();

            return objectMapper.readTree(cleaned);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Groq structured JSON response", e);
        }
    }
}