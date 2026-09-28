package com.fitcore.backend.dto;

import java.math.BigDecimal;

public class GoiTapResponseDTO {

    private Integer maGoi;
    private String tenGoi;
    private Integer thoiHanThang;
    private BigDecimal giaTien;
    private Integer soBuoiPt;
    private String trangThai;

    public GoiTapResponseDTO() {
    }

    public GoiTapResponseDTO(
            Integer maGoi,
            String tenGoi,
            Integer thoiHanThang,
            BigDecimal giaTien,
            Integer soBuoiPt,
            String trangThai) {

        this.maGoi = maGoi;
        this.tenGoi = tenGoi;
        this.thoiHanThang = thoiHanThang;
        this.giaTien = giaTien;
        this.soBuoiPt = soBuoiPt;
        this.trangThai = trangThai;
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