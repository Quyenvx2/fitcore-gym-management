package com.fitcore.backend.service;

import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.repository.TaiKhoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaiKhoanService {

    private final TaiKhoanRepository repository;

    public TaiKhoanService(TaiKhoanRepository repository) {
        this.repository = repository;
    }

    public List<TaiKhoan> layDanhSachTaiKhoan() {
        return repository.findAll();
    }
}