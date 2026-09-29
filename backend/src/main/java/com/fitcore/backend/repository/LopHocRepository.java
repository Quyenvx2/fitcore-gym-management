package com.fitcore.backend.repository;

import com.fitcore.backend.entity.LopHoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LopHocRepository extends JpaRepository<LopHoc, Integer> {

    Optional<LopHoc> findByTenLop(String tenLop);

    List<LopHoc> findByPt_MaPt(Integer maPt);

    List<LopHoc> findByPhongTap_MaPhong(Integer maPhong);
}