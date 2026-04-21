package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_data") // 全ユーザーのデータがここに入る
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;   // ここでユーザーを特定（AIが識別するID）
    private String content;  // AIが生成したテキストなど
    private String category; // データの種類
}