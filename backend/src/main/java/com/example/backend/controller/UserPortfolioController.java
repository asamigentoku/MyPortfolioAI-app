package com.example.backend.controller;

import com.example.backend.model.UserData;
import com.example.backend.service.UserPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "http://localhost:3000") // フロントエンド(Next.js)からのアクセスを許可
public class UserPortfolioController {

    @Autowired
    private UserPortfolioService service;

    // データを保存するエンドポイント (POST)
    @PostMapping("/{userId}")
    public UserData addData(@PathVariable String userId, @RequestBody UserData request) {
        return service.saveUserContent(userId, request.getContent(), request.getCategory());
    }

    // データを取得するエンドポイント (GET)
    @GetMapping("/{userId}")
    public List<UserData> getData(@PathVariable String userId) {
        return service.getUserData(userId);
    }
}