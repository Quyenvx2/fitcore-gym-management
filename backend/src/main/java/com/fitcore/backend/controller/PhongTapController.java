package com.fitcore.backend.controller;

import com.fitcore.backend.dto.PhongTapRequestDTO;
import com.fitcore.backend.dto.PhongTapResponseDTO;
import com.fitcore.backend.service.PhongTapService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phong-tap")
public class PhongTapController {

    private final PhongTapService service;

    public PhongTapController(PhongTapService service) {
        this.service = service;
    }

    @GetMapping
    public List<PhongTapResponseDTO> layDanhSachPhong() {
        return service.layDanhSachPhong();
    }

    @GetMapping("/{id}")
    public PhongTapResponseDTO layPhongTheoId(
            @PathVariable Integer id) {
        return service.layPhongTheoId(id);
    }

    @PostMapping
    public PhongTapResponseDTO taoPhong(
            @Valid @RequestBody PhongTapRequestDTO request) {
        return service.taoPhong(request);
    }

    @PutMapping("/{id}")
    public PhongTapResponseDTO capNhatPhong(
            @PathVariable Integer id,
            @Valid @RequestBody PhongTapRequestDTO request) {
        return service.capNhatPhong(id, request);
    }

    @DeleteMapping("/{id}")
    public void xoaPhong(@PathVariable Integer id) {
        service.xoaPhong(id);
    }
}