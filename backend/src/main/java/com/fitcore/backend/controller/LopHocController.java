package com.fitcore.backend.controller;

import com.fitcore.backend.dto.BuoiHocResponseDTO;
import com.fitcore.backend.dto.LopHocRequestDTO;
import com.fitcore.backend.dto.LopHocResponseDTO;
import com.fitcore.backend.service.LopHocService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.fitcore.backend.dto.BuoiHocResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/lop-hoc")
public class LopHocController {

    private final LopHocService service;

    public LopHocController(LopHocService service) {
        this.service = service;
    }

    @GetMapping
    public List<LopHocResponseDTO> layDanhSachLop() {
        return service.layDanhSachLop();
    }

    @GetMapping("/{id}")
    public LopHocResponseDTO layLopTheoId(
            @PathVariable Integer id) {

        return service.layLopTheoId(id);
    }

    @PostMapping
    public LopHocResponseDTO taoLop(
            @Valid @RequestBody LopHocRequestDTO request) {

        return service.taoLop(request);
    }

    @PutMapping("/{id}")
    public LopHocResponseDTO capNhatLop(
            @PathVariable Integer id,
            @Valid @RequestBody LopHocRequestDTO request) {

        return service.capNhatLop(id, request);
    }

    @DeleteMapping("/{id}")
    public void xoaLop(@PathVariable Integer id) {
        service.xoaLop(id);
    }

    @GetMapping("/{id}/buoi-hoc")
    public List<BuoiHocResponseDTO> layDanhSachBuoiHoc(
        @PathVariable Integer id) {

    return service.layDanhSachBuoiHoc(id);
    }
}