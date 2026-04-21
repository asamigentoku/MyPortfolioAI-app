package com.example.backend.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "careers")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //User.use_idを外部キーに設定
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String careerName;
    private LocalDateTime time;// "2020/04 - 2023/03" など
}