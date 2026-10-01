package com.fitcore.backend.dto;

import jakarta.validation.constraints.NotNull;

public class DangKyLopHocRequestDTO {

    @NotNull(message = "Mã lớp không được để trống")
    private Integer maLop;

    public DangKyLopHocRequestDTO() {
    }

    public Integer getMaLop() {
        return maLop;
    }

    public void setMaLop(Integer maLop) {
        this.maLop = maLop;
    }
}