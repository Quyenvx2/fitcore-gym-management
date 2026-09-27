package com.fitcore.backend.service;

import com.fitcore.backend.dto.TaiKhoanLoginRequestDTO;
import com.fitcore.backend.dto.TaiKhoanLoginResponseDTO;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.TaiKhoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TaiKhoanRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            TaiKhoanRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TaiKhoanLoginResponseDTO dangNhap(
            TaiKhoanLoginRequestDTO request) {

        TaiKhoan tk = repository
                .findByTenDangNhap(request.getTenDangNhap())
                .orElse(null);

     
        if (tk == null) {
            throw new BusinessException(
                    "AUTH_INVALID_CREDENTIALS",
                    "Tên đăng nhập hoặc mật khẩu không đúng",
                    HttpStatus.UNAUTHORIZED
            );
        }

        if (!tk.getTrangThai().equals("Active")) {
            throw new BusinessException(
                    "AUTH_ACCOUNT_BLOCKED",
                    "Tài khoản không thể đăng nhập",
                    HttpStatus.UNAUTHORIZED
            );
        }

  
        boolean matKhauDung = passwordEncoder.matches(
                request.getMatKhau(),
                tk.getMatKhau()
        );

        if (!matKhauDung) {
            throw new BusinessException(
                    "AUTH_INVALID_CREDENTIALS",
                    "Tên đăng nhập hoặc mật khẩu không đúng",
                    HttpStatus.UNAUTHORIZED
            );
        }

       
        String token = jwtService.generateToken(
                tk.getMaTk(),
                tk.getTenDangNhap(),
                tk.getVaiTro()
        );


        return new TaiKhoanLoginResponseDTO(
                token,
                tk.getMaTk(),
                tk.getTenDangNhap(),
                tk.getVaiTro(),
                tk.getTrangThai()
        );
    }
}