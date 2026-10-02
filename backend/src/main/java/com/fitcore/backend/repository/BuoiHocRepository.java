package com.fitcore.backend.repository;

import com.fitcore.backend.entity.BuoiHoc;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

public interface BuoiHocRepository extends JpaRepository<BuoiHoc, Integer> {

    List<BuoiHoc> findByLopHoc_MaLop(Integer maLop);


@Query("""
        SELECT COUNT(b) > 0
        FROM BuoiHoc b
        JOIN b.lopHoc l
        WHERE l.pt.maPt = :maPt
          AND b.ngayHoc = :ngayHoc
          AND b.trangThai <> 'Bị hủy'
          AND :gioBatDau >= l.gioBatDau
          AND :gioBatDau < l.gioKetThuc
        """)
boolean existsPtDayLopAtTime(
        @Param("maPt") Integer maPt,
        @Param("ngayHoc") LocalDate ngayHoc,
        @Param("gioBatDau") LocalTime gioBatDau
);
}