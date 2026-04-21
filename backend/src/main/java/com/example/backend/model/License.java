package com.example.backend.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "licenses")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //User.use_idを外部キーに設定
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String licenseName;
    private LocalDate createdAt; // 取得日
}