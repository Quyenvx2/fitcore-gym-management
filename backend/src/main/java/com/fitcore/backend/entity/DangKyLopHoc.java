package com.fitcore.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "DANG_KY_LOP_HOC")
public class DangKyLopHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_dk_lop")
    private Integer maDkLop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hv", nullable = false)
    private HoiVien hoiVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_lop", nullable = false)
    private LopHoc lopHoc;

    @Column(name = "thoi_gian_dang_ky", nullable = false)
    private LocalDateTime thoiGianDangKy;

    @Column(name = "ngay_het_han", nullable = false)
    private LocalDate ngayHetHan;

    @Column(name = "ngay_huy")
    private LocalDateTime ngayHuy;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    public DangKyLopHoc() {
    }

    public Integer getMaDkLop() {
        return maDkLop;
    }

    public void setMaDkLop(Integer maDkLop) {
        this.maDkLop = maDkLop;
    }

    public HoiVien getHoiVien() {
        return hoiVien;
    }

    public void setHoiVien(HoiVien hoiVien) {
        this.hoiVien = hoiVien;
    }

    public LopHoc getLopHoc() {
        return lopHoc;
    }

    public void setLopHoc(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }

    public LocalDateTime getThoiGianDangKy() {
        return thoiGianDangKy;
    }

    public void setThoiGianDangKy(LocalDateTime thoiGianDangKy) {
        this.thoiGianDangKy = thoiGianDangKy;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public LocalDateTime getNgayHuy() {
        return ngayHuy;
    }

    public void setNgayHuy(LocalDateTime ngayHuy) {
        this.ngayHuy = ngayHuy;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}