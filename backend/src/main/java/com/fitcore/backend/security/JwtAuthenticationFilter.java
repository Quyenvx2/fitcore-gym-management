package com.fitcore.backend.security;

import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.repository.TaiKhoanRepository;
import com.fitcore.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TaiKhoanRepository repository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            TaiKhoanRepository repository) {

        this.jwtService = jwtService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Lấy Authorization Header
        String authHeader = request.getHeader("Authorization");

        // Không có header hoặc không phải Bearer
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 2. Lấy JWT
        String token = authHeader.substring(7);

        try {

            // 3. Lấy username từ JWT
            String username = jwtService.extractUsername(token);

            // 4. Chỉ xác thực nếu SecurityContext chưa có user
            if (username != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {

                // 5. Tìm tài khoản
                TaiKhoan tk = repository
                        .findByTenDangNhap(username)
                        .orElse(null);

                // 6. Kiểm tra tài khoản và JWT
                if (tk != null &&
                        tk.getTrangThai().equals("Active") &&
                        jwtService.isTokenValid(
                                token,
                                tk.getTenDangNhap())) {

                    // 7. Gán role cho Spring Security
                    SimpleGrantedAuthority authority =
                            new SimpleGrantedAuthority(
                                    tk.getVaiTro()
                            );

                    // 8. Tạo Authentication
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    tk.getTenDangNhap(),
                                    null,
                                    List.of(authority)
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // 9. Đưa Authentication vào SecurityContext
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception e) {

            // JWT lỗi → không xác thực user
            SecurityContextHolder.clearContext();
        }

        // 10. Cho request đi tiếp
        filterChain.doFilter(request, response);
    }
}