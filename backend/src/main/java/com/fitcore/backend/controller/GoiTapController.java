package com.fitcore.backend.controller;

import com.fitcore.backend.dto.GoiTapRequestDTO;
import com.fitcore.backend.dto.GoiTapResponseDTO;
import com.fitcore.backend.service.GoiTapService;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController 
@RequestMapping ("/api/goi-tap")
public class GoiTapController {
    private  final GoiTapService service;

    public GoiTapController (GoiTapService service){
        this.service=service;
    }

    @GetMapping 
    public List<GoiTapResponseDTO> layDanhSachGoiTap(){
        return service.layDanhSachGoiTap();
    }

    @GetMapping("/{id}")
    public GoiTapResponseDTO layGoiTapTheoId( @PathVariable Integer id) {
        return service.layGoiTapTheoId(id);
    }

    @PostMapping 
    public GoiTapResponseDTO taoGoiTap(@Valid @RequestBody GoiTapRequestDTO request){
        return service.taoGoiTap(request);
    }

    @PutMapping("/{id}")
    public GoiTapResponseDTO capNhatGoiTap(
            @PathVariable Integer id,
            @Valid @RequestBody GoiTapRequestDTO request) {
        return service.capNhatGoiTap(id, request);
    }

    @DeleteMapping("/{id}")
    public void xoaGoiTap(@PathVariable Integer id) {
        service.xoaGoiTap(id);
    }

}
