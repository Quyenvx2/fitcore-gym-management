package com.fitcore.backend.repository;

import com.fitcore.backend.entity.BuoiHoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuoiHocRepository extends JpaRepository<BuoiHoc, Integer> {

    List<BuoiHoc> findByLopHoc_MaLop(Integer maLop);
}