package com.fitcore.backend.entity;
import jakarta.persistence.*;
import java.math.*;

@Entity 
@Table (name = "GOI_TAP")
public class GoiTap {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name= "ma_goi")
    private  Integer maGoi;

    @Column(name = "ten_goi", nullable = false)
    private String tenGoi;

    @Column(name = "thoi_han_thang", nullable = false)
    private Integer thoiHanThang;

    @Column(name = "gia_tien", nullable = false)
    private BigDecimal giaTien;

    @Column(name = "so_buoi_pt", nullable = false)
    private Integer soBuoiPt;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    public GoiTap() {
    }

    public Integer getMaGoi() {
        return maGoi;
    }

    public void setMaGoi(Integer maGoi) {
        this.maGoi = maGoi;
    }

    public String getTenGoi() {
        return tenGoi;
    }

    public void setTenGoi(String tenGoi) {
        this.tenGoi = tenGoi;
    }

    public Integer getThoiHanThang() {
        return thoiHanThang;
    }

    public void setThoiHanThang(Integer thoiHanThang) {
        this.thoiHanThang = thoiHanThang;
    }

    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }

    public Integer getSoBuoiPt() {
        return soBuoiPt;
    }

    public void setSoBuoiPt(Integer soBuoiPt) {
        this.soBuoiPt = soBuoiPt;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
}
