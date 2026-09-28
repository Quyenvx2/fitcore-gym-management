package com.fitcore.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity 
@Table (name = "DANG_KY_GOI")
public class DangKyGoi {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "ma_dk_goi")
    private Integer maDkGoi;

    @ManyToOne 
    @JoinColumn (name = "ma_hv", nullable = false)
    private HoiVien hoiVien;

    @ManyToOne 
    @JoinColumn (name ="ma_goi", nullable = false)
    private GoiTap goiTap;

     @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "ngay_het_han", nullable = false)
    private LocalDate ngayHetHan;

    @Column(name = "ngay_dang_ky", nullable = false)
    private LocalDate ngayDangKy;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    public DangKyGoi(){
    }

     public Integer getMaDkGoi() {
        return maDkGoi;
    }

    public void setMaDkGoi(Integer maDkGoi) {
        this.maDkGoi = maDkGoi;
    }

    public HoiVien getHoiVien() {
        return hoiVien;
    }

    public void setHoiVien(HoiVien hoiVien) {
        this.hoiVien = hoiVien;
    }

    public GoiTap getGoiTap() {
        return goiTap;
    }

    public void setGoiTap(GoiTap goiTap) {
        this.goiTap = goiTap;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public LocalDate getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(LocalDate ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
