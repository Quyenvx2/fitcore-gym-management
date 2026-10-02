package com.fitcore.backend.repository;

import com.fitcore.backend.entity.ChiSoCoThe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiSoCoTheRepository
        extends JpaRepository<ChiSoCoThe, Integer> {

    List<ChiSoCoThe> findByHoiVien_MaHvOrderByNgayDoDesc(
            Integer maHv
    );

    List<ChiSoCoThe> findByHoiVien_MaHvOrderByNgayDoAsc(
            Integer maHv
    );
}