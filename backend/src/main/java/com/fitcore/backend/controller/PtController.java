package com.fitcore.backend.controller;


import com.fitcore.backend.dto.CapNhatHoSoPtRequestDTO;
import com.fitcore.backend.dto.PtDanhSachResponseDTO;
import com.fitcore.backend.dto.PtRequestDTO;
import com.fitcore.backend.dto.PtResponseDTO;
import com.fitcore.backend.service.PtService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pt")
public class PtController {

    private final PtService service;

    public PtController(PtService service) {
        this.service = service;
    }

    // api cho pt

    @GetMapping("/cua-toi")
    public PtResponseDTO layHoSoCuaToi(
            Authentication authentication) {

        return service.layPtCuaToi(
                authentication.getName()
        );
    }

    @PutMapping("/cua-toi")
    public PtResponseDTO capNhatHoSoCuaToi(
            @Valid @RequestBody CapNhatHoSoPtRequestDTO request,
            Authentication authentication) {

        return service.capNhatHoSoCuaToi(
                authentication.getName(),
                request
        );
    }
    // api cho hoi vien

    @GetMapping("/danh-sach-cho-hoi-vien")
    public List<PtDanhSachResponseDTO> layDanhSachPtChoHoiVien() {
    return service.layDanhSachPtChoHoiVien();
    }
    //api cho nhan vien

    @GetMapping
    public List<PtResponseDTO> layDanhSachPt() {
        return service.layDanhSachPt();
    }

    @GetMapping("/{id}")
    public PtResponseDTO layPtTheoId(
            @PathVariable Integer id) {

        return service.layPtTheoId(id);
    }

    @PostMapping
    public PtResponseDTO taoPt(
            @Valid @RequestBody PtRequestDTO request) {

        return service.taoPt(request);
    }

    @PutMapping("/{id}")
    public PtResponseDTO capNhatPt(
            @PathVariable Integer id,
            @Valid @RequestBody PtRequestDTO request) {

        return service.capNhatPt(id, request);
    }

    @DeleteMapping("/{id}")
    public void xoaPt(
            @PathVariable Integer id) {

        service.xoaPt(id);
    }
}