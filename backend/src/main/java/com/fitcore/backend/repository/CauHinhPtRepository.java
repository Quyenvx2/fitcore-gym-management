package com.fitcore.backend.repository;

import com.fitcore.backend.entity.CauHinhPt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CauHinhPtRepository
        extends JpaRepository<CauHinhPt, Integer> {

    Optional<CauHinhPt> findByTrangThai(String trangThai);
}