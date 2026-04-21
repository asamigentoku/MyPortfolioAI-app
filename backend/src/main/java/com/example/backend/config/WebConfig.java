package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // ✅ 全エンドポイントの先頭に "/api" を自動付与する設定
    // これにより、application.propertiesに書くのと同じ効果が得られます
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", c -> true);
    }

    // ✅ CORS (Allow Origin) の一括設定
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 全パスに対して
                .allowedOrigins("http://localhost:3000") // Next.jsなどのオリジンを許可
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}