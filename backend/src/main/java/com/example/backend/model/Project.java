package com.example.backend.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //User.use_idを外部キーに設定
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String explanation; // 説明
    private String url; // デプロイURL
    private String githubUrl;
    private Boolean isGithub;

    //skill*projectを結合した
    @ManyToMany
    @JoinTable(
            name = "project_skill", // 中間テーブル（deploy_skill）
            //自分のテーブルのキー
            joinColumns = @JoinColumn(name = "project_id"),
            //相手のテーブルのキー
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;
}