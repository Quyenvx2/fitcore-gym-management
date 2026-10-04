package com.fitcore.backend.dto;

import java.math.BigDecimal;

public class LuongPtResponseDTO {

    private Integer maPt;
    private String hoTen;

    private BigDecimal luongCoBan;
    private BigDecimal luongBuoiPt;
    private BigDecimal luongLopHoc;
    private BigDecimal tongLuong;

    public LuongPtResponseDTO(
            Integer maPt,
            String hoTen,
            BigDecimal luongCoBan,
            BigDecimal luongBuoiPt,
            BigDecimal luongLopHoc) {

        this.maPt = maPt;
        this.hoTen = hoTen;
        this.luongCoBan = luongCoBan;
        this.luongBuoiPt = luongBuoiPt;
        this.luongLopHoc = luongLopHoc;

        this.tongLuong = luongCoBan
                .add(luongBuoiPt)
                .add(luongLopHoc);
    }

    public Integer getMaPt() {
        return maPt;
    }

    public String getHoTen() {
        return hoTen;
    }

    public BigDecimal getLuongCoBan() {
        return luongCoBan;
    }

    public BigDecimal getLuongBuoiPt() {
        return luongBuoiPt;
    }

    public BigDecimal getLuongLopHoc() {
        return luongLopHoc;
    }

    public BigDecimal getTongLuong() {
        return tongLuong;
    }
}