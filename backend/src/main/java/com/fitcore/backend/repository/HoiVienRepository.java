package com.fitcore.backend.repository;

import com.fitcore.backend.entity.HoiVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HoiVienRepository extends JpaRepository<HoiVien, Integer> {

    Optional<HoiVien> findByCccd(String cccd);

    Optional<HoiVien> findBySdt(String sdt);

    Optional<HoiVien> findByTaiKhoan_MaTk(Integer maTk);
}