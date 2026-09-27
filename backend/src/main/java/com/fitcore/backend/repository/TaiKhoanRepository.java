package com.fitcore.backend.repository;

import com.fitcore.backend.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
     Optional<TaiKhoan> findByTenDangNhap(String tenDangNhap);
}