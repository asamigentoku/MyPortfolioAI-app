package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // 追加
import org.springframework.boot.autoconfigure.domain.EntityScan; // 追加

@SpringBootApplication
@EnableJpaRepositories("com.example.backend.repository") // Repositoryの場所を指定
@EntityScan("com.example.backend.model")
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}