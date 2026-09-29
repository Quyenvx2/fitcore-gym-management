package com.fitcore.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PHONG_TAP")
public class PhongTap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_phong")
    private Integer maPhong;

    @Column(name = "ten_phong", nullable = false, unique = true)
    private String tenPhong;

    @Column(name = "vi_tri", nullable = false)
    private String viTri;

    @Column(name = "suc_chua", nullable = false)
    private Integer sucChua;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    public PhongTap() {
    }

    public Integer getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(Integer maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public Integer getSucChua() {
        return sucChua;
    }

    public void setSucChua(Integer sucChua) {
        this.sucChua = sucChua;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}