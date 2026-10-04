package com.fitcore.backend.repository;

import com.fitcore.backend.entity.LopHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LopHocRepository extends JpaRepository<LopHoc, Integer> {

    Optional<LopHoc> findByTenLop(String tenLop);

    List<LopHoc> findByPt_MaPt(Integer maPt);

    List<LopHoc> findByPhongTap_MaPhong(Integer maPhong);

    @Query("""
            SELECT COALESCE(SUM(l.donGiaPt), 0)
            FROM LopHoc l
            WHERE l.pt.maPt = :maPt
              AND l.ngayBatDau >= :tuNgay
              AND l.ngayKetThuc <= :denNgay
            """)
    BigDecimal tinhLuongLopHocTheoThang(
            @Param("maPt") Integer maPt,
            @Param("tuNgay") LocalDate tuNgay,
            @Param("denNgay") LocalDate denNgay
    );
}