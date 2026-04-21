package com.example.backend.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //User.use_idを外部キーに設定
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private Integer level; // 1-5など
    private String description;
    private Boolean isFramework;
    private String category; // BACKEND, FRONTEND, OS, CLOUD, AI

    @ManyToMany(mappedBy = "skills")
    private Set<Project> projects;
}