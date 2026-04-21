package com.example.backend.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ProviderId", nullable = true) // nullable = true でNULLを許可
    private String providerId;

    @Column(name = "github_id", nullable = true)
    private Integer githubId;
    private String name;
    private String email;

    //skills.userを親としている
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Skill> skills;

    //licenses.userを親としている
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<License> licenses;

    //carees.userを親としている
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Career> careers;

    //project.userを親としている
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Project> projects;
}