package com.fitcore.backend.controller;

import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.service.TaiKhoanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tai-khoan")
public class TaiKhoanController {

    private final TaiKhoanService service;

    public TaiKhoanController(TaiKhoanService service) {
        this.service = service;
    }

    @GetMapping
    public List<TaiKhoan> layDanhSachTaiKhoan() {
        return service.layDanhSachTaiKhoan();
    }
}