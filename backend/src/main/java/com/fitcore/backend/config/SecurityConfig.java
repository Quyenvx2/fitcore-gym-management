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
                         HttpMethod.GET,
                         "/api/lop-hoc",
                         "/api/lop-hoc/*"
                        )
                        .hasAnyAuthority(
                         "NHAN_VIEN",
                                "PT",
                        "HOI_VIEN"
                        )
                        .requestMatchers(HttpMethod.GET, "/api/goi-tap", "/api/goi-tap/**")
                        .hasAnyAuthority("NHAN_VIEN", "HOI_VIEN")
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
                         "/api/dang-ky-lop/cua-toi/**")
                        .hasAuthority("HOI_VIEN")

                        .requestMatchers(
                                "/api/pt/cua-toi/**"
                        )
                        .hasAuthority("PT")
                        .requestMatchers(
                                 HttpMethod.GET,
                          "/api/pt/danh-sach-cho-hoi-vien"
                                )
                        .hasAuthority("HOI_VIEN")            
                        .requestMatchers(
                                "/api/pt/**"
                        )
                        .hasAuthority("NHAN_VIEN")

                      
                        .requestMatchers(
                                "/api/cau-hinh-pt/**"
                        )
                        .hasAuthority("NHAN_VIEN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/buoi-pt"
                        )
                        .hasAuthority("NHAN_VIEN")

                        .requestMatchers(
                          HttpMethod.PUT,
                        "/api/buoi-pt/*/trang-thai")
                        .hasAnyAuthority("NHAN_VIEN", "PT")

                        
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/buoi-pt/pt-cua-toi"
                        )
                        .hasAuthority("PT")

                        
                        .requestMatchers(
                                "/api/buoi-pt/cua-toi/**"
                        )
                        .hasAuthority("HOI_VIEN")

                        .requestMatchers(
                         "/api/check-in-out/cua-toi/**")
                        .hasAuthority("HOI_VIEN")

                        .requestMatchers(
                          HttpMethod.GET,
                         "/api/check-in-out")
                        .hasAuthority("NHAN_VIEN")




                        
                        .requestMatchers(
                          HttpMethod.POST,
                        "/api/chi-so-co-the/hoi-vien/**"
                        )
                        .hasAuthority("NHAN_VIEN")

                               
                        .requestMatchers(
                          "/api/chi-so-co-the/cua-toi/**"
                        )
                        .hasAuthority("HOI_VIEN")


                        .requestMatchers("/api/thong-ke/tong-quan")
                                .hasAuthority("NHAN_VIEN")

                        .requestMatchers("/api/thong-ke/pt/cua-toi")
                        .hasAuthority("PT")

                        .requestMatchers("/api/thong-ke/hoi-vien/cua-toi")
                        .hasAuthority("HOI_VIEN")
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