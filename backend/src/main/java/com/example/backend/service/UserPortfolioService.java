package com.example.backend.service;

import com.example.backend.model.UserData;
import com.example.backend.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserPortfolioService {

    @Autowired
    private UserDataRepository repository;

    // ユーザーごとのデータを保存する（AIからの入力を想定）
    public UserData saveUserContent(String userId, String content, String category) {
        UserData data = new UserData();
        data.setUserId(userId);
        data.setContent(content);
        data.setCategory(category);
        return repository.save(data); // これだけでDBに保存される
    }

    // 特定のユーザーの全データを取得する
    public List<UserData> getUserData(String userId) {
        return repository.findByUserId(userId);
    }
}