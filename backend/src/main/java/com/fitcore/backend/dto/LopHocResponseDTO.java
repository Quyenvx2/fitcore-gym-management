package com.fitcore.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class LopHocResponseDTO {

    private Integer maLop;
    private Integer maPhong;
    private Integer maPt;
    private Integer soNguoiDaDangKy;
    private String tenLop;
    private BigDecimal donGiaPt;
    private String thuHoc;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String trangThai;

    public LopHocResponseDTO() {
    }

    public LopHocResponseDTO(
            Integer maLop,
            Integer maPhong,
            Integer maPt,
            Integer soNguoiDaDangKy,
            String tenLop,
            BigDecimal donGiaPt,
            String thuHoc,
            LocalTime gioBatDau,
            LocalTime gioKetThuc,
            LocalDate ngayBatDau,
            LocalDate ngayKetThuc,
            String trangThai) {

        this.maLop = maLop;
        this.maPhong = maPhong;
        this.maPt = maPt;
        this.soNguoiDaDangKy = soNguoiDaDangKy;
        this.tenLop = tenLop;
        this.donGiaPt = donGiaPt;
        this.thuHoc = thuHoc;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    public Integer getMaLop() {
    return maLop;
}

public void setMaLop(Integer maLop) {
    this.maLop = maLop;
}

public Integer getMaPhong() {
    return maPhong;
}

public void setMaPhong(Integer maPhong) {
    this.maPhong = maPhong;
}

public Integer getMaPt() {
    return maPt;
}

public void setMaPt(Integer maPt) {
    this.maPt = maPt;
}

public Integer getSoNguoiDaDangKy() {
    return soNguoiDaDangKy;
}

public void setSoNguoiDaDangKy(Integer soNguoiDaDangKy) {
    this.soNguoiDaDangKy = soNguoiDaDangKy;
}

public String getTenLop() {
    return tenLop;
}

public void setTenLop(String tenLop) {
    this.tenLop = tenLop;
}

public BigDecimal getDonGiaPt() {
    return donGiaPt;
}

public void setDonGiaPt(BigDecimal donGiaPt) {
    this.donGiaPt = donGiaPt;
}

public String getThuHoc() {
    return thuHoc;
}

public void setThuHoc(String thuHoc) {
    this.thuHoc = thuHoc;
}

public LocalTime getGioBatDau() {
    return gioBatDau;
}

public void setGioBatDau(LocalTime gioBatDau) {
    this.gioBatDau = gioBatDau;
}

public LocalTime getGioKetThuc() {
    return gioKetThuc;
}

public void setGioKetThuc(LocalTime gioKetThuc) {
    this.gioKetThuc = gioKetThuc;
}

public LocalDate getNgayBatDau() {
    return ngayBatDau;
}

public void setNgayBatDau(LocalDate ngayBatDau) {
    this.ngayBatDau = ngayBatDau;
}

public LocalDate getNgayKetThuc() {
    return ngayKetThuc;
}

public void setNgayKetThuc(LocalDate ngayKetThuc) {
    this.ngayKetThuc = ngayKetThuc;
}

public String getTrangThai() {
    return trangThai;
}

public void setTrangThai(String trangThai) {
    this.trangThai = trangThai;
}
}