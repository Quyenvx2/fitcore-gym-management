package com.fitcore.backend.dto;

import java.math.BigDecimal;

public class CauHinhPtResponseDTO {

    private Integer maCauHinh;
    private BigDecimal donGiaPt;
    private String trangThai;

    public CauHinhPtResponseDTO() {
    }

    public CauHinhPtResponseDTO(
            Integer maCauHinh,
            BigDecimal donGiaPt,
            String trangThai
    ) {
        this.maCauHinh = maCauHinh;
        this.donGiaPt = donGiaPt;
        this.trangThai = trangThai;
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