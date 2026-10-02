package com.fitcore.backend.controller;

import com.fitcore.backend.dto.HoiVienRequestDTO;
import com.fitcore.backend.dto.HoiVienResponseDTO;
import com.fitcore.backend.service.HoiVienService;

import com.fitcore.backend.dto.HoanThienHoiVienRequestDTO;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoi-vien")
public class HoiVienController {

    private final HoiVienService service;

    public HoiVienController(HoiVienService service) {
        this.service = service;
    }


    @GetMapping
    public List<HoiVienResponseDTO> layDanhSachHoiVien() {

        return service.layDanhSachHoiVien();
    }



    @GetMapping("/{id}")
    public HoiVienResponseDTO layHoiVienTheoId(
            @PathVariable Integer id) {

        return service.layHoiVienTheoId(id);
    }


   

    @PostMapping
    public HoiVienResponseDTO taoHoiVien(
            @Valid @RequestBody HoiVienRequestDTO request) {

        return service.taoHoiVien(request);
    }


    @PostMapping("/cua-toi")
    public HoiVienResponseDTO hoanThienHoSo(
        @Valid @RequestBody HoanThienHoiVienRequestDTO request,
        Authentication authentication) {

    return service.hoanThienHoSo(
            request,
            authentication.getName());
    }

    @PutMapping("/{id}")
    public HoiVienResponseDTO capNhatHoiVien(
            @PathVariable Integer id,
            @Valid @RequestBody HoiVienRequestDTO request) {

        return service.capNhatHoiVien(id, request);
    }


    

    @DeleteMapping("/{id}")
    public void xoaHoiVien(
            @PathVariable Integer id) {

        service.xoaHoiVien(id);
    }


    @GetMapping("/cua-toi")
    public HoiVienResponseDTO layHoSoCuaToi(
        Authentication authentication) {

    return service.layHoiVienCuaToi(
            authentication.getName()
    );
    }


   @PutMapping("/cua-toi")
public HoiVienResponseDTO capNhatHoSoCuaToi(
        @Valid @RequestBody HoanThienHoiVienRequestDTO request,
        Authentication authentication) {

    return service.capNhatHoSoCuaToi(
            request,
            authentication.getName()
    );
}
}
