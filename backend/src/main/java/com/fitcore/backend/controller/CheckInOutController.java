package com.fitcore.backend.controller;

import com.fitcore.backend.dto.CheckInOutResponseDTO;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.repository.HoiVienRepository;
import com.fitcore.backend.repository.TaiKhoanRepository;
import com.fitcore.backend.service.CheckInOutService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check-in-out")
public class CheckInOutController {

    private final CheckInOutService checkInOutService;
    private final TaiKhoanRepository taiKhoanRepository;
    private final HoiVienRepository hoiVienRepository;

    public CheckInOutController(
            CheckInOutService checkInOutService,
            TaiKhoanRepository taiKhoanRepository,
            HoiVienRepository hoiVienRepository
    ) {
        this.checkInOutService = checkInOutService;
        this.taiKhoanRepository = taiKhoanRepository;
        this.hoiVienRepository = hoiVienRepository;
    }

    @PostMapping("/cua-toi/check-in")
    public CheckInOutResponseDTO checkIn(
            Authentication authentication
    ) {
        Integer maHv = layMaHv(authentication);

        return checkInOutService.checkIn(maHv);
    }

    @PutMapping("/cua-toi/check-out")
    public CheckInOutResponseDTO checkOut(
            Authentication authentication
    ) {
        Integer maHv = layMaHv(authentication);

        return checkInOutService.checkOut(maHv);
    }

    @GetMapping("/cua-toi")
    public List<CheckInOutResponseDTO> layLichSuCuaToi(
            Authentication authentication
    ) {
        Integer maHv = layMaHv(authentication);

        return checkInOutService.layLichSuCuaHoiVien(maHv);
    }

    private Integer layMaHv(Authentication authentication) {

        String tenDangNhap = authentication.getName();

        TaiKhoan taiKhoan = taiKhoanRepository
                .findByTenDangNhap(tenDangNhap)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy tài khoản"
                        )
                );

        if (!"HOI_VIEN".equals(taiKhoan.getVaiTro())) {
            throw new RuntimeException(
                    "Chỉ hội viên mới được sử dụng API này"
            );
        }

        HoiVien hoiVien = hoiVienRepository
                .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy hội viên"
                        )
                );

        return hoiVien.getMaHv();
    }
    @GetMapping
    public List<CheckInOutResponseDTO> layTatCa() {

    return checkInOutService.layTatCa();}
}