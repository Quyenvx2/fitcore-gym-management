package com.fitcore.backend.controller;

import com.fitcore.backend.dto.DangKyLopHocRequestDTO;
import com.fitcore.backend.dto.DangKyLopHocResponseDTO;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.HoiVienRepository;
import com.fitcore.backend.repository.TaiKhoanRepository;
import com.fitcore.backend.service.DangKyLopHocService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dang-ky-lop")
public class DangKyLopHocController {

    private final DangKyLopHocService service;
    private final TaiKhoanRepository taiKhoanRepository;
    private final HoiVienRepository hoiVienRepository;

    public DangKyLopHocController(
            DangKyLopHocService service,
            TaiKhoanRepository taiKhoanRepository,
            HoiVienRepository hoiVienRepository
    ) {
        this.service = service;
        this.taiKhoanRepository = taiKhoanRepository;
        this.hoiVienRepository = hoiVienRepository;
    }

    @PostMapping("/cua-toi")
    public DangKyLopHocResponseDTO dangKyLopHoc(
            @Valid @RequestBody DangKyLopHocRequestDTO request,
            Authentication authentication
    ) {
        Integer maHv = layMaHv(authentication);

        return service.dangKyLopHoc(
                maHv,
                request.getMaLop()
        );
    }

    @DeleteMapping("/cua-toi")
    public DangKyLopHocResponseDTO huyDangKyLopHoc(
            @RequestParam Integer maLop,
            Authentication authentication
    ) {
        Integer maHv = layMaHv(authentication);

        return service.huyDangKyLopHoc(
                maHv,
                maLop
        );
    }

    private Integer layMaHv(Authentication authentication) {

        String tenDangNhap = authentication.getName();

        TaiKhoan taiKhoan = taiKhoanRepository
                .findByTenDangNhap(tenDangNhap)
                .orElseThrow(() -> new BusinessException(
                        "TAI_KHOAN_NOT_FOUND",
                        "Không tìm thấy tài khoản",
                        HttpStatus.NOT_FOUND
                ));

        if (!"HOI_VIEN".equals(taiKhoan.getVaiTro())) {
            throw new BusinessException(
                    "FORBIDDEN",
                    "Chỉ hội viên mới được thực hiện thao tác này",
                    HttpStatus.FORBIDDEN
            );
        }

        HoiVien hoiVien = hoiVienRepository
                .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
                .orElseThrow(() -> new BusinessException(
                        "HOI_VIEN_NOT_FOUND",
                        "Không tìm thấy hồ sơ hội viên",
                        HttpStatus.NOT_FOUND
                ));

        return hoiVien.getMaHv();
    }
}