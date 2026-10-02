package com.fitcore.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CauHinhPtRequestDTO {

    @NotNull(message = "Đơn giá PT không được để trống")
    @Positive(message = "Đơn giá PT phải lớn hơn 0")
    private BigDecimal donGiaPt;

    public CauHinhPtRequestDTO() {
    }

    public BigDecimal getDonGiaPt() {
        return donGiaPt;
    }

    public void setDonGiaPt(BigDecimal donGiaPt) {
        this.donGiaPt = donGiaPt;
    }
}