package com.fitcore.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BuoiPtResponseDTO {

    private Integer maBuoiPt;
    private Integer maHv;
    private Integer maPt;
    private LocalDateTime thoiGianBatDau;
    private BigDecimal donGiaPt;
    private String trangThai;
    private LocalDateTime thoiGianDat;
    private LocalDateTime thoiGianHuy;

    public BuoiPtResponseDTO() {
    }

    public BuoiPtResponseDTO(
            Integer maBuoiPt,
            Integer maHv,
            Integer maPt,
            LocalDateTime thoiGianBatDau,
            BigDecimal donGiaPt,
            String trangThai,
            LocalDateTime thoiGianDat,
            LocalDateTime thoiGianHuy
    ) {
        this.maBuoiPt = maBuoiPt;
        this.maHv = maHv;
        this.maPt = maPt;
        this.thoiGianBatDau = thoiGianBatDau;
        this.donGiaPt = donGiaPt;
        this.trangThai = trangThai;
        this.thoiGianDat = thoiGianDat;
        this.thoiGianHuy = thoiGianHuy;
    }

    public Integer getMaBuoiPt() {
        return maBuoiPt;
    }

    public void setMaBuoiPt(Integer maBuoiPt) {
        this.maBuoiPt = maBuoiPt;
    }

    public Integer getMaHv() {
        return maHv;
    }

    public void setMaHv(Integer maHv) {
        this.maHv = maHv;
    }

    public Integer getMaPt() {
        return maPt;
    }

    public void setMaPt(Integer maPt) {
        this.maPt = maPt;
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