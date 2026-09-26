package com.fitcore.backend.controller;

import com.fitcore.backend.dto.TaiKhoanRequestDTO;
import com.fitcore.backend.dto.TaiKhoanDTO;
import com.fitcore.backend.service.TaiKhoanService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tai-khoan")
public class TaiKhoanController {

    private final TaiKhoanService service;

    public TaiKhoanController(TaiKhoanService service) {
        this.service = service;
    }

    @GetMapping
    public List<TaiKhoanDTO> layDanhSachTaiKhoan() {
        return service.layDanhSachTaiKhoan();
    }
    @GetMapping("/{id}")
    public TaiKhoanDTO layTaiKhoanTheoId (@PathVariable Integer id){
        return service.layTaiKhoanTheoId(id);
    }
    @PostMapping 
    public TaiKhoanDTO  taoTaiKhoan(@Valid @RequestBody TaiKhoanRequestDTO request){
        return service.taoTaiKhoan(request);
    }
    @PutMapping ("/{id}")
    public TaiKhoanDTO capNhatTaiKhoan(@PathVariable  Integer id, @Valid @RequestBody  TaiKhoanRequestDTO request){
        return service.capNhatTaiKhoan(id, request);
    }
    @DeleteMapping("/{id}")
    public void xoaTaiKhoan(@PathVariable Integer id){
        service.xoaTaiKhoan(id);
    }
}