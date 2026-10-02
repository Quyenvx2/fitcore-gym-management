package com.fitcore.backend.controller;

import com.fitcore.backend.dto.ThongKeResponseDTO;
import com.fitcore.backend.service.ThongKeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thong-ke")
public class ThongKeController {

    private final ThongKeService thongKeService;

    public ThongKeController(
            ThongKeService thongKeService
    ) {
        this.thongKeService = thongKeService;
    }


    // =========================================================
    // NHÂN VIÊN
    // =========================================================

    @GetMapping("/tong-quan")
    public ThongKeResponseDTO thongKeTongQuan() {

        return thongKeService.thongKeTongQuan();
    }


    // =========================================================
    // PT
    // =========================================================

    @GetMapping("/pt/cua-toi")
    public ThongKeResponseDTO thongKePt(
            Authentication authentication
    ) {

        String tenDangNhap =
                authentication.getName();

        return thongKeService.thongKePt(
                tenDangNhap
        );
    }


    // =========================================================
    // HỘI VIÊN
    // =========================================================

    @GetMapping("/hoi-vien/cua-toi")
    public ThongKeResponseDTO thongKeHoiVien(
            Authentication authentication
    ) {

        String tenDangNhap =
                authentication.getName();

        return thongKeService.thongKeHoiVien(
                tenDangNhap
        );
    }
}