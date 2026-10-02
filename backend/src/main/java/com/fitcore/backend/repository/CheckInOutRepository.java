package com.fitcore.backend.repository;

import com.fitcore.backend.entity.CheckInOut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckInOutRepository
        extends JpaRepository<CheckInOut, Integer> {

    List<CheckInOut> findByHoiVien_MaHvOrderByThoiGianCheckInDesc(
            Integer maHv
    );

    Optional<CheckInOut> findFirstByHoiVien_MaHvAndThoiGianCheckOutIsNullOrderByThoiGianCheckInDesc(
            Integer maHv
    );
}