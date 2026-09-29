package com.fitcore.backend.repository;

import com.fitcore.backend.entity.PhongTap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhongTapRepository extends JpaRepository<PhongTap, Integer> {

    Optional<PhongTap> findByTenPhong(String tenPhong);
}