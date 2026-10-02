package com.fitcore.backend.controller;

import com.fitcore.backend.dto.CauHinhPtRequestDTO;
import com.fitcore.backend.dto.CauHinhPtResponseDTO;
import com.fitcore.backend.service.CauHinhPtService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cau-hinh-pt")
public class CauHinhPtController {

    private final CauHinhPtService cauHinhPtService;

    public CauHinhPtController(CauHinhPtService cauHinhPtService) {
        this.cauHinhPtService = cauHinhPtService;
    }


    @GetMapping
    public CauHinhPtResponseDTO layGiaPtHienTai() {
        return cauHinhPtService.layGiaPtHienTai();
    }

    @PutMapping
    public CauHinhPtResponseDTO capNhatGiaPt(
            @Valid @RequestBody CauHinhPtRequestDTO request
    ) {
        return cauHinhPtService.capNhatGiaPt(request);
    }
}