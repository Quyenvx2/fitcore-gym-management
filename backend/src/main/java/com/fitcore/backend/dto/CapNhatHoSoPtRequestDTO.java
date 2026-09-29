package com.fitcore.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public class CapNhatHoSoPtRequestDTO {

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

    public CapNhatHoSoPtRequestDTO() {
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
}