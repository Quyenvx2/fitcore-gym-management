package com.fitcore.backend.repository;

import com.fitcore.backend.entity.BuoiHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BuoiHocRepository
        extends JpaRepository<BuoiHoc, Integer> {

    List<BuoiHoc> findByLopHoc_MaLop(Integer maLop);

    @Query("""
            SELECT COUNT(b) > 0
            FROM BuoiHoc b
            JOIN b.lopHoc l
            WHERE l.pt.maPt = :maPt
              AND b.ngayHoc = :ngayHoc
              AND b.trangThai NOT IN ('Bị hủy', 'Đã hủy')
              AND b.ngayHoc BETWEEN l.ngayBatDau AND l.ngayKetThuc
              AND :gioBatDau < l.gioKetThuc
              AND :gioKetThuc > l.gioBatDau
            """)
    boolean existsPtDayLopDuring(
            @Param("maPt") Integer maPt,
            @Param("ngayHoc") LocalDate ngayHoc,
            @Param("gioBatDau") LocalTime gioBatDau,
            @Param("gioKetThuc") LocalTime gioKetThuc
    );

    @Query("""
            SELECT COUNT(b) > 0
            FROM BuoiHoc b
            JOIN b.lopHoc l
            WHERE b.ngayHoc = :ngayHoc
              AND b.trangThai NOT IN ('Bị hủy', 'Đã hủy')
              AND b.ngayHoc BETWEEN l.ngayBatDau AND l.ngayKetThuc
              AND :gioBatDau < l.gioKetThuc
              AND :gioKetThuc > l.gioBatDau
              AND EXISTS (
                  SELECT d.maDkLop
                  FROM DangKyLopHoc d
                  WHERE d.lopHoc = l
                    AND d.hoiVien.maHv = :maHv
                    AND d.trangThai = 'Đăng kí thành công'
                    AND d.ngayHetHan >= :ngayHoc
              )
            """)
    boolean existsHoiVienDangKyLopDuring(
            @Param("maHv") Integer maHv,
            @Param("ngayHoc") LocalDate ngayHoc,
            @Param("gioBatDau") LocalTime gioBatDau,
            @Param("gioKetThuc") LocalTime gioKetThuc
    );
}