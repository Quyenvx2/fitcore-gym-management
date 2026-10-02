package com.fitcore.backend.controller;

import com.fitcore.backend.dto.ChiSoCoTheRequestDTO;
import com.fitcore.backend.dto.ChiSoCoTheResponseDTO;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.repository.HoiVienRepository;
import com.fitcore.backend.repository.TaiKhoanRepository;
import com.fitcore.backend.service.ChiSoCoTheService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chi-so-co-the")
public class ChiSoCoTheController {

    private final ChiSoCoTheService chiSoCoTheService;
    private final TaiKhoanRepository taiKhoanRepository;
    private final HoiVienRepository hoiVienRepository;

    public ChiSoCoTheController(
            ChiSoCoTheService chiSoCoTheService,
            TaiKhoanRepository taiKhoanRepository,
            HoiVienRepository hoiVienRepository
    ) {
        this.chiSoCoTheService = chiSoCoTheService;
        this.taiKhoanRepository = taiKhoanRepository;
        this.hoiVienRepository = hoiVienRepository;
    }

    // NHÂN VIÊN thêm chỉ số cho hội viên
    @PostMapping("/hoi-vien/{maHv}")
    public ChiSoCoTheResponseDTO themChiSo(
            @PathVariable Integer maHv,
            @Valid @RequestBody ChiSoCoTheRequestDTO request
    ) {
        return chiSoCoTheService.themChiSo(maHv, request);
    }

    // HỘI VIÊN xem lịch sử chỉ số của mình
    @GetMapping("/cua-toi")
    public List<ChiSoCoTheResponseDTO> layLichSuCuaToi(
            Authentication authentication
    ) {
        Integer maHv = layMaHv(authentication);

        return chiSoCoTheService.layLichSu(maHv);
    }

    // HỘI VIÊN xem chỉ số mới nhất
    @GetMapping("/cua-toi/moi-nhat")
    public ChiSoCoTheResponseDTO layMoiNhat(
            Authentication authentication
    ) {
        Integer maHv = layMaHv(authentication);

        return chiSoCoTheService.layMoiNhat(maHv);
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
}