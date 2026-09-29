package com.fitcore.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PtRequestDTO {

    @NotNull(message = "Mã tài khoản không được để trống")
    private Integer maTk;

    @NotBlank(message = "CCCD không được để trống")
    private String cccd;

    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate ngaySinh;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String sdt;

    @NotBlank(message = "Chuyên môn không được để trống")
    private String chuyenMon;

    @NotNull(message = "Số năm kinh nghiệm không được để trống")
    @PositiveOrZero(message = "Số năm kinh nghiệm không được âm")
    private Integer soNamKinhNghiem;

    @NotNull(message = "Lương cơ bản không được để trống")
    @PositiveOrZero(message = "Lương cơ bản không được âm")
    private BigDecimal luongCoBan;

    @NotBlank(message = "Trạng thái không được để trống")
    private String trangThai;

    public PtRequestDTO() {}

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