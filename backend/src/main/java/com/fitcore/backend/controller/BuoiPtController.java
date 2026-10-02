package com.fitcore.backend.controller;

import com.fitcore.backend.dto.BuoiPtRequestDTO;
import com.fitcore.backend.dto.BuoiPtResponseDTO;
import com.fitcore.backend.dto.BuoiPtTrangThaiRequestDTO;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.repository.HoiVienRepository;
import com.fitcore.backend.repository.PtRepository;
import com.fitcore.backend.repository.TaiKhoanRepository;
import com.fitcore.backend.service.BuoiPtService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buoi-pt")
public class BuoiPtController {

    private final BuoiPtService buoiPtService;
    private final TaiKhoanRepository taiKhoanRepository;
    private final HoiVienRepository hoiVienRepository;
    private final PtRepository ptRepository;
    public BuoiPtController(
            BuoiPtService buoiPtService,
            TaiKhoanRepository taiKhoanRepository,
            HoiVienRepository hoiVienRepository,
            PtRepository ptRepository
    ) {
        this.buoiPtService = buoiPtService;
        this.taiKhoanRepository = taiKhoanRepository;
        this.hoiVienRepository = hoiVienRepository;
        this.ptRepository=ptRepository ;
    }

   
    @PostMapping("/cua-toi")
    public BuoiPtResponseDTO datBuoiPt(
            @Valid @RequestBody BuoiPtRequestDTO request,
            Authentication authentication
    ) {
        return buoiPtService.datBuoiPt(
                request,
                authentication.getName()
        );
    }

    @GetMapping("/cua-toi")
    public List<BuoiPtResponseDTO> layLichCuaToi(
            Authentication authentication
    ) {
        Integer maHv = layMaHv(authentication);

        return buoiPtService.layDanhSachCuaHoiVien(maHv);
    }

    @DeleteMapping("/cua-toi/{maBuoiPt}")
    public BuoiPtResponseDTO huyBuoiPt(
            @PathVariable Integer maBuoiPt,
            Authentication authentication
    ) {
        return buoiPtService.huyBuoiPt(
                maBuoiPt,
                authentication.getName()
        );
    }

    
    @GetMapping("/pt-cua-toi")
    public List<BuoiPtResponseDTO> layBuoiPtCuaToi(
        Authentication authentication
) {
    Integer maPt = layMaPt(authentication);

    return buoiPtService.layDanhSachCuaPt(maPt);
}

    @GetMapping
    public List<BuoiPtResponseDTO> layTatCaBuoiPt() {

    return buoiPtService.layDanhSachTatCa();
}
    
    @PutMapping("/{maBuoiPt}/trang-thai")
public BuoiPtResponseDTO capNhatTrangThai(
        @PathVariable Integer maBuoiPt,
        @Valid @RequestBody BuoiPtTrangThaiRequestDTO request,
        Authentication authentication
) {
    return buoiPtService.capNhatTrangThai(
            maBuoiPt,
            request.getTrangThai(),
            authentication.getName()
    );
}

    private Integer layMaPt(Authentication authentication) {

    String tenDangNhap = authentication.getName();

    TaiKhoan taiKhoan = taiKhoanRepository
            .findByTenDangNhap(tenDangNhap)
            .orElseThrow(() ->
                    new RuntimeException("Không tìm thấy tài khoản")
            );

    if (!"PT".equals(taiKhoan.getVaiTro())) {
        throw new RuntimeException(
                "Chỉ PT mới được sử dụng API này"
        );
    }

    return ptRepository
            .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
            .orElseThrow(() ->
                    new RuntimeException("Không tìm thấy PT")
            )
            .getMaPt();
}
    private Integer layMaHv(Authentication authentication) {

        String tenDangNhap = authentication.getName();

        TaiKhoan taiKhoan = taiKhoanRepository
                .findByTenDangNhap(tenDangNhap)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy tài khoản")
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