package com.fitcore.backend.dto;

import java.time.LocalDate;

public class BuoiHocResponseDTO {

    private Integer maBuoi;
    private Integer maLop;
    private LocalDate ngayHoc;
    private String trangThai;

    public BuoiHocResponseDTO() {
    }

    public BuoiHocResponseDTO(
            Integer maBuoi,
            Integer maLop,
            LocalDate ngayHoc,
            String trangThai) {

        this.maBuoi = maBuoi;
        this.maLop = maLop;
        this.ngayHoc = ngayHoc;
        this.trangThai = trangThai;
    }

    public Integer getMaBuoi() {
        return maBuoi;
    }

    public void setMaBuoi(Integer maBuoi) {
        this.maBuoi = maBuoi;
    }

    public Integer getMaLop() {
        return maLop;
    }

    public void setMaLop(Integer maLop) {
        this.maLop = maLop;
    }

    public LocalDate getNgayHoc() {
        return ngayHoc;
    }

    public void setNgayHoc(LocalDate ngayHoc) {
        this.ngayHoc = ngayHoc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}