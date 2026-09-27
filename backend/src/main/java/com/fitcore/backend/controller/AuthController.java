package com.fitcore.backend.controller;
import com.fitcore.backend.dto.TaiKhoanLoginResponseDTO;
import com.fitcore.backend.dto.TaiKhoanLoginRequestDTO;
import com.fitcore.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
    this.authService = authService;
    }
    @PostMapping("/login")
    public TaiKhoanLoginResponseDTO dangNhap(
        @Valid @RequestBody TaiKhoanLoginRequestDTO request) {

    return authService.dangNhap(request);
    }
}
