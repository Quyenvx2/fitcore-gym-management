package com.fitcore.backend.repository;

import com.fitcore.backend.entity.BuoiPt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BuoiPtRepository extends JpaRepository<BuoiPt, Integer> {

  
    List<BuoiPt> findByHoiVien_MaHv(Integer maHv);

    List<BuoiPt> findByPt_MaPt(Integer maPt);

   
    List<BuoiPt> findByPt_MaPtAndThoiGianBatDauBetween(
            Integer maPt,
            LocalDateTime tuThoiGian,
            LocalDateTime denThoiGian
    );

    // Lấy các buổi theo trạng thái
    List<BuoiPt> findByTrangThai(String trangThai);


    @Query(value = """
            SELECT COUNT(*)
            FROM BUOI_PT
            WHERE ma_hv = :maHv
              AND thoi_gian_bat_dau >= :tuThoiGian
              AND thoi_gian_bat_dau <= :denThoiGian
              AND (
                    trang_thai <> 'Bị hủy'
                    OR (
                        trang_thai = 'Bị hủy'
                        AND thoi_gian_huy < thoi_gian_bat_dau
                        AND TIMESTAMPDIFF(
                            MINUTE,
                            thoi_gian_huy,
                            thoi_gian_bat_dau
                        ) < 360
                    )
              )
            """, nativeQuery = true)
    long demSoBuoiDaSuDung(
            @Param("maHv") Integer maHv,
            @Param("tuThoiGian") LocalDateTime tuThoiGian,
            @Param("denThoiGian") LocalDateTime denThoiGian
    );
}