package com.fitcore.backend.config;

import com.fitcore.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                // REST API không sử dụng CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // Không sử dụng session để lưu trạng thái đăng nhập
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Login không cần JWT
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        // Quản lý tài khoản chỉ dành cho nhân viên
                        .requestMatchers("/api/tai-khoan/**")
                        .hasAuthority("NHAN_VIEN")

                        // Các API khác cần đăng nhập
                        .anyRequest()
                        .authenticated()
                )

                // Không dùng form login
                .formLogin(AbstractHttpConfigurer::disable)

                // Không dùng Basic Auth
                .httpBasic(AbstractHttpConfigurer::disable)

                // JWT Filter chạy trước filter xác thực mặc định
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}