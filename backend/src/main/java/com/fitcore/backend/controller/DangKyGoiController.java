package com.fitcore.backend.controller;

import com.fitcore.backend.dto.DangKyGoiHoiVienRequestDTO;
import com.fitcore.backend.dto.DangKyGoiRequestDTO;
import com.fitcore.backend.dto.DangKyGoiResponseDTO;
import com.fitcore.backend.service.DangKyGoiService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dang-ky-goi")
public class DangKyGoiController {

    private final DangKyGoiService service;

    public DangKyGoiController(DangKyGoiService service) {
        this.service = service;
    }

    @GetMapping
    public List<DangKyGoiResponseDTO> layDanhSachDangKyGoi() {
        return service.layDanhSachDangKyGoi();
    }

    @GetMapping("/cua-toi")
    public List<DangKyGoiResponseDTO> layDangKyGoiCuaToi(
            Authentication authentication) {

        return service.layDangKyGoiCuaToi(
                authentication.getName()
        );
    }

    @PostMapping("/cua-toi")
    public DangKyGoiResponseDTO taoDangKyGoiChoToi(
            @Valid @RequestBody DangKyGoiHoiVienRequestDTO request,
            Authentication authentication) {

        return service.taoDangKyGoiChoHoiVien(
                request,
                authentication.getName()
        );
    }

    @PatchMapping("/cua-toi/{id}/huy")
    public void huyDangKyGoiCuaToi(
            @PathVariable Integer id,
            Authentication authentication) {

        service.huyDangKyGoiCuaToi(
                id,
                authentication.getName()
        );
    }

    @GetMapping("/hoi-vien/{maHv}")
    public List<DangKyGoiResponseDTO> layDangKyGoiTheoHoiVien(
            @PathVariable Integer maHv) {

        return service.layDangKyGoiTheoHoiVien(maHv);
    }

    @GetMapping("/{id}")
    public DangKyGoiResponseDTO layDangKyGoiTheoId(
            @PathVariable Integer id) {

        return service.layDangKyGoiTheoId(id);
    }

    @PostMapping
    public DangKyGoiResponseDTO taoDangKyGoi(
            @Valid @RequestBody DangKyGoiRequestDTO request) {

        return service.taoDangKyGoi(request);
    }

    @PatchMapping("/{id}/kich-hoat")
    public DangKyGoiResponseDTO kichHoatDangKyGoi(
            @PathVariable Integer id) {

        return service.kichHoatDangKyGoi(id);
    }

    @PatchMapping("/{id}/huy")
    public void huyDangKyGoi(
            @PathVariable Integer id) {

        service.huyDangKyGoi(id);
    }
}