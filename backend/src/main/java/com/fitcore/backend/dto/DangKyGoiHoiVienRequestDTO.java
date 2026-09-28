package com.fitcore.backend.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DangKyGoiHoiVienRequestDTO {

    @NotNull(message = "Mã gói không được để trống")
    private Integer maGoi;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate ngayBatDau;

    public DangKyGoiHoiVienRequestDTO() {
    }

    public Integer getMaGoi() {
        return maGoi;
    }

    public void setMaGoi(Integer maGoi) {
        this.maGoi = maGoi;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
}