package com.fitcore.backend.repository;

import com.fitcore.backend.entity.HoiVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongKeRepository extends JpaRepository<HoiVien, Integer> {

    // =========================================================
    // THỐNG KÊ DÀNH CHO NHÂN VIÊN
    // =========================================================

    // Tổng số hội viên
    @Query("""
        SELECT COUNT(h)
        FROM HoiVien h
    """)
    Long demTongHoiVien();

    // Số hội viên đang hoạt động
    @Query("""
        SELECT COUNT(h)
        FROM HoiVien h
        WHERE h.trangThai = 'Đang hoạt động'
    """)
    Long demHoiVienDangHoatDong();

    // Tổng số PT
    @Query("""
        SELECT COUNT(p)
        FROM Pt p
    """)
    Long demTongPt();

    // Số PT đang làm việc
    @Query("""
        SELECT COUNT(p)
        FROM Pt p
        WHERE p.trangThai = 'Đang làm việc'
    """)
    Long demPtDangLamViec();

    // Số gói tập đang được sử dụng
    @Query("""
        SELECT COUNT(d)
        FROM DangKyGoi d
        WHERE d.trangThai = 'Đang sử dụng'
    """)
    Long demGoiTapDangSuDung();

    // Tổng số lớp học
    @Query("""
        SELECT COUNT(l)
        FROM LopHoc l
    """)
    Long demTongLopHoc();

    // Tổng số buổi PT
    @Query("""
        SELECT COUNT(b)
        FROM BuoiPt b
    """)
    Long demTongBuoiPt();

    // Tổng số lượt check-in
    @Query("""
        SELECT COUNT(c)
        FROM CheckInOut c
    """)
    Long demTongLuotCheckIn();


    // =========================================================
    // THỐNG KÊ DÀNH CHO PT
    // =========================================================

    // Số hội viên mà PT đã/đang có buổi PT
    // DISTINCT để một hội viên có nhiều buổi PT
    // vẫn chỉ được tính một lần.
    @Query("""
        SELECT COUNT(DISTINCT b.hoiVien.maHv)
        FROM BuoiPt b
        WHERE b.pt.maPt = :maPt
    """)
    Long demSoHocVienCuaPt(
            @Param("maPt") Integer maPt
    );

    // Số buổi PT đã lên lịch
    @Query("""
        SELECT COUNT(b)
        FROM BuoiPt b
        WHERE b.pt.maPt = :maPt
          AND b.trangThai = 'Đã lên lịch'
    """)
    Long demSoBuoiPtDaLenLich(
            @Param("maPt") Integer maPt
    );

    // Số buổi PT đã hoàn thành
    @Query("""
        SELECT COUNT(b)
        FROM BuoiPt b
        WHERE b.pt.maPt = :maPt
          AND b.trangThai = 'Đã hoàn thành'
    """)
    Long demSoBuoiPtDaHoanThanh(
            @Param("maPt") Integer maPt
    );

    // Số buổi PT mà hội viên vắng mặt
    @Query("""
        SELECT COUNT(b)
        FROM BuoiPt b
        WHERE b.pt.maPt = :maPt
          AND b.trangThai = 'Vắng mặt'
    """)
    Long demSoBuoiPtVangMat(
            @Param("maPt") Integer maPt
    );


    // =========================================================
    // THỐNG KÊ DÀNH CHO HỘI VIÊN
    // =========================================================

    // Tổng số gói tập mà hội viên đã đăng ký
    @Query("""
        SELECT COUNT(d)
        FROM DangKyGoi d
        WHERE d.hoiVien.maHv = :maHv
    """)
    Long demSoGoiTapDaDangKy(
            @Param("maHv") Integer maHv
    );

    // Tổng số buổi PT của hội viên đã hoàn thành
    @Query("""
        SELECT COUNT(b)
        FROM BuoiPt b
        WHERE b.hoiVien.maHv = :maHv
          AND b.trangThai = 'Đã hoàn thành'
    """)
    Long demSoBuoiPtDaSuDung(
            @Param("maHv") Integer maHv
    );

    // Số lớp học mà hội viên đã đăng ký
    // Không tính đăng ký đã bị hủy.
    @Query("""
        SELECT COUNT(d)
        FROM DangKyLopHoc d
        WHERE d.hoiVien.maHv = :maHv
          AND d.trangThai <> 'Đã bị hủy'
    """)
    Long demSoLopHocDaDangKy(
            @Param("maHv") Integer maHv
    );

    // Số lượt check-in của hội viên
    @Query("""
        SELECT COUNT(c)
        FROM CheckInOut c
        WHERE c.hoiVien.maHv = :maHv
    """)
    Long demSoLanCheckIn(
            @Param("maHv") Integer maHv
    );

    // Số lần đo chỉ số cơ thể
    @Query("""
        SELECT COUNT(c)
        FROM ChiSoCoThe c
        WHERE c.hoiVien.maHv = :maHv
    """)
    Long demSoLanDoChiSoCoThe(
            @Param("maHv") Integer maHv
    );
}