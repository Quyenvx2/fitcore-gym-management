package com.fitcore.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "LOP_HOC")
public class LopHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_lop")
    private Integer maLop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_phong", nullable = false)
    private PhongTap phongTap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_pt", nullable = false)
    private Pt pt;

    @Column(name = "so_nguoi_da_dang_ky", nullable = false)
    private Integer soNguoiDaDangKy = 0;

    @Column(name = "ten_lop", nullable = false)
    private String tenLop;

    @Column(name = "don_gia_pt", nullable = false)
    private BigDecimal donGiaPt;

    @Column(name = "thu_hoc", nullable = false)
    private String thuHoc;

    @Column(name = "gio_bat_dau", nullable = false)
    private LocalTime gioBatDau;

    @Column(name = "gio_ket_thuc", nullable = false)
    private LocalTime gioKetThuc;

    @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "ngay_ket_thuc", nullable = false)
    private LocalDate ngayKetThuc;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    public LopHoc() {
    }

    public Integer getMaLop() {
    return maLop;
}

public void setMaLop(Integer maLop) {
    this.maLop = maLop;
}

public PhongTap getPhongTap() {
    return phongTap;
}

public void setPhongTap(PhongTap phongTap) {
    this.phongTap = phongTap;
}

public Pt getPt() {
    return pt;
}

public void setPt(Pt pt) {
    this.pt = pt;
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