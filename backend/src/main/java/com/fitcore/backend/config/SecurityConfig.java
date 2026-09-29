package com.fitcore.backend.config;

import com.fitcore.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                       
                        .requestMatchers("/api/auth/**")
                        .permitAll()


                       
                        .requestMatchers(
                                "/api/hoi-vien/cua-toi/**"
                        )
                        .hasAuthority("HOI_VIEN")


                      
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/lop-hoc/*/buoi-hoc"
                        )
                        .hasAnyAuthority(
                                "NHAN_VIEN",
                                "PT",
                                "HOI_VIEN"
                        )


                        .requestMatchers(
                                "/api/tai-khoan/**",
                                "/api/hoi-vien/**",
                                "/api/goi-tap/**",
                                "/api/phong-tap/**",
                                "/api/lop-hoc/**"
                        )
                        .hasAuthority("NHAN_VIEN")


                      
                        .requestMatchers(
                                "/api/dang-ky-goi/cua-toi/**"
                        )
                        .hasAuthority("HOI_VIEN")


                       
                        .requestMatchers(
                                "/api/dang-ky-goi/**"
                        )
                        .hasAuthority("NHAN_VIEN")


                        
                        .requestMatchers(
                                "/api/pt/cua-toi/**"
                        )
                        .hasAuthority("PT")


                        .requestMatchers(
                                "/api/pt/**"
                        )
                        .hasAuthority("NHAN_VIEN")


                      
                        .anyRequest()
                        .authenticated()
                )

                .formLogin(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}