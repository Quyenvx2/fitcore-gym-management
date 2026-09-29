package com.fitcore.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PtResponseDTO {

    private Integer maPt;
    private Integer maTk;
    private String cccd;
    private String hoTen;
    private LocalDate ngaySinh;
    private String sdt;
    private String chuyenMon;
    private Integer soNamKinhNghiem;
    private BigDecimal luongCoBan;
    private String trangThai;

    public PtResponseDTO() {}

    public PtResponseDTO(
            Integer maPt,
            Integer maTk,
            String cccd,
            String hoTen,
            LocalDate ngaySinh,
            String sdt,
            String chuyenMon,
            Integer soNamKinhNghiem,
            BigDecimal luongCoBan,
            String trangThai) {

        this.maPt = maPt;
        this.maTk = maTk;
        this.cccd = cccd;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.chuyenMon = chuyenMon;
        this.soNamKinhNghiem = soNamKinhNghiem;
        this.luongCoBan = luongCoBan;
        this.trangThai = trangThai;
    }

    public Integer getMaPt() {
        return maPt;
    }

    public void setMaPt(Integer maPt) {
        this.maPt = maPt;
    }

    public Integer getMaTk() {
        return maTk;
    }

    public void setMaTk(Integer maTk) {
        this.maTk = maTk;
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