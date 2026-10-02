package com.fitcore.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "BUOI_PT")
public class BuoiPt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_buoi_pt")
    private Integer maBuoiPt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hv", nullable = false)
    private HoiVien hoiVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_pt", nullable = false)
    private Pt pt;

    @Column(name = "thoi_gian_bat_dau", nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(name = "don_gia_pt", nullable = false)
    private BigDecimal donGiaPt;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @Column(name = "thoi_gian_dat", nullable = false)
    private LocalDateTime thoiGianDat;

    @Column(name = "thoi_gian_huy")
    private LocalDateTime thoiGianHuy;

    public BuoiPt() {
    }

    public Integer getMaBuoiPt() {
        return maBuoiPt;
    }

    public void setMaBuoiPt(Integer maBuoiPt) {
        this.maBuoiPt = maBuoiPt;
    }

    public HoiVien getHoiVien() {
        return hoiVien;
    }

    public void setHoiVien(HoiVien hoiVien) {
        this.hoiVien = hoiVien;
    }

    public Pt getPt() {
        return pt;
    }

    public void setPt(Pt pt) {
        this.pt = pt;
    }

    public LocalDateTime getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(LocalDateTime thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public BigDecimal getDonGiaPt() {
        return donGiaPt;
    }

    public void setDonGiaPt(BigDecimal donGiaPt) {
        this.donGiaPt = donGiaPt;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDateTime getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(LocalDateTime thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public LocalDateTime getThoiGianHuy() {
        return thoiGianHuy;
    }

    public void setThoiGianHuy(LocalDateTime thoiGianHuy) {
        this.thoiGianHuy = thoiGianHuy;
    }
}