package com.fitcore.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "HOI_VIEN")
public class HoiVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hv")
    private Integer maHv;

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

    @Column(name = "gioi_tinh", nullable = false)
    private String gioiTinh;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @Column(name = "sdt", nullable = false, unique = true)
    private String sdt;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;


   
    public HoiVien() {
    }


    public Integer getMaHv() {
        return maHv;
    }

    public void setMaHv(Integer maHv) {
        this.maHv = maHv;
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

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}