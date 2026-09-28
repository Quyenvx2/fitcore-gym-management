package com.fitcore.backend.dto;

import java.time.LocalDate;

public class DangKyGoiResponseDTO {

    private Integer maDkGoi;
    private Integer maHv;
    private Integer maGoi;
    private LocalDate ngayBatDau;
    private LocalDate ngayHetHan;
    private LocalDate ngayDangKy;
    private String trangThai;

    public DangKyGoiResponseDTO() {
    }

    public DangKyGoiResponseDTO(
            Integer maDkGoi,
            Integer maHv,
            Integer maGoi,
            LocalDate ngayBatDau,
            LocalDate ngayHetHan,
            LocalDate ngayDangKy,
            String trangThai) {

        this.maDkGoi = maDkGoi;
        this.maHv = maHv;
        this.maGoi = maGoi;
        this.ngayBatDau = ngayBatDau;
        this.ngayHetHan = ngayHetHan;
        this.ngayDangKy = ngayDangKy;
        this.trangThai = trangThai;
    }

    public Integer getMaDkGoi() {
        return maDkGoi;
    }

    public void setMaDkGoi(Integer maDkGoi) {
        this.maDkGoi = maDkGoi;
    }

    public Integer getMaHv() {
        return maHv;
    }

    public void setMaHv(Integer maHv) {
        this.maHv = maHv;
    }

    public Integer getMaGoi() {
        return maGoi;
    }

    public void setMaGoi(Integer maGoi) {
        this.maGoi = maGoi;
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