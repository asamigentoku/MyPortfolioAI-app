package com.example.backend.config;

import com.example.backend.filter.JwtFilter;
import com.example.backend.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(f -> f.disable())) // H2コンソール用
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, e) -> {
                            log.warn("Unauthorized: uri={} error={}", request.getRequestURI(), e.getMessage());
                            response.sendError(401, e.getMessage());
                        })
                        .accessDeniedHandler((request, response, e) -> {
                            log.warn("Access denied: uri={} error={}", request.getRequestURI(), e.getMessage());
                            response.sendError(403, e.getMessage());
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        // 認証用
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Spring Boot エラーハンドリング用（/error が認証ループになるのを防ぐ）
                        .requestMatchers("/error").permitAll()
                        // ALBヘルスチェック用（/ へのアクセスを許可）
                        .requestMatchers("/").permitAll()
                        //開発ように解放
                        //公開用
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                        .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
                        .requestMatchers("/api/v1/public-profile/slug/**").permitAll()
                        //openapi用
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // それ以外は認証必要
                        .anyRequest().authenticated()
                )
                
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}