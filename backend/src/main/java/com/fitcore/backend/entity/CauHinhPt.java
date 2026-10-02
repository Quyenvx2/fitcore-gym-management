package com.fitcore.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CAU_HINH_PT")
public class CauHinhPt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_cau_hinh")
    private Integer maCauHinh;

    @Column(name = "don_gia_pt", nullable = false)
    private BigDecimal donGiaPt;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    public CauHinhPt() {
    }

    public Integer getMaCauHinh() {
        return maCauHinh;
    }

    public void setMaCauHinh(Integer maCauHinh) {
        this.maCauHinh = maCauHinh;
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
}