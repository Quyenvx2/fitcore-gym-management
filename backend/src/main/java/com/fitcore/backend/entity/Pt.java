package com.fitcore.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PT")
public class Pt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_pt")
    private Integer maPt;

    @OneToOne
    @JoinColumn(
            name = "ma_tk",
            nullable = false,
            unique = true
    )
    private TaiKhoan taiKhoan;

    @Column(name = "cccd", nullable = false, unique = true)
    private String cccd;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "sdt", nullable = false, unique = true)
    private String sdt;

    @Column(name = "chuyen_mon", nullable = false)
    private String chuyenMon;

    @Column(name = "so_nam_kinh_nghiem", nullable = false)
    private Integer soNamKinhNghiem;

    @Column(name = "luong_co_ban", nullable = false)
    private BigDecimal luongCoBan;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    public Pt() {}

    public Integer getMaPt() {
        return maPt;
    }

    public void setMaPt(Integer maPt) {
        this.maPt = maPt;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getChuyenMon() {
        return chuyenMon;
    }

    public void setChuyenMon(String chuyenMon) {
        this.chuyenMon = chuyenMon;
    }

    public Integer getSoNamKinhNghiem() {
        return soNamKinhNghiem;
    }

    public void setSoNamKinhNghiem(Integer soNamKinhNghiem) {
        this.soNamKinhNghiem = soNamKinhNghiem;
    }

    public BigDecimal getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(BigDecimal luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}