package com.fitcore.backend.repository;

import com.fitcore.backend.entity.Pt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PtRepository extends JpaRepository<Pt, Integer> {

    Optional<Pt> findByCccd(String cccd);

    Optional<Pt> findBySdt(String sdt);

    Optional<Pt> findByTaiKhoan_MaTk(Integer maTk);
}