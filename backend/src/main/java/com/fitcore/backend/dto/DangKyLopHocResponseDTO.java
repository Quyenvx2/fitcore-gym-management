package com.fitcore.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DangKyLopHocResponseDTO {

    private Integer maDkLop;
    private Integer maHv;
    private Integer maLop;
    private LocalDateTime thoiGianDangKy;
    private LocalDate ngayHetHan;
    private LocalDateTime ngayHuy;
    private String trangThai;

    public DangKyLopHocResponseDTO() {
    }

    public DangKyLopHocResponseDTO(
            Integer maDkLop,
            Integer maHv,
            Integer maLop,
            LocalDateTime thoiGianDangKy,
            LocalDate ngayHetHan,
            LocalDateTime ngayHuy,
            String trangThai
    ) {
        this.maDkLop = maDkLop;
        this.maHv = maHv;
        this.maLop = maLop;
        this.thoiGianDangKy = thoiGianDangKy;
        this.ngayHetHan = ngayHetHan;
        this.ngayHuy = ngayHuy;
        this.trangThai = trangThai;
    }

    public Integer getMaDkLop() {
        return maDkLop;
    }

    public void setMaDkLop(Integer maDkLop) {
        this.maDkLop = maDkLop;
    }

    public Integer getMaHv() {
        return maHv;
    }

    public void setMaHv(Integer maHv) {
        this.maHv = maHv;
    }

    public Integer getMaLop() {
        return maLop;
    }

    public void setMaLop(Integer maLop) {
        this.maLop = maLop;
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