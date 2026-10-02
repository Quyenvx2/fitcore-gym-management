package com.fitcore.backend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class BuoiPtRequestDTO {

    @NotNull(message = "Mã PT không được để trống")
    private Integer maPt;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    @Future(message = "Thời gian bắt đầu phải ở tương lai")
    private LocalDateTime thoiGianBatDau;

    public BuoiPtRequestDTO() {
    }

    public Integer getMaPt() {
        return maPt;
    }

    public void setMaPt(Integer maPt) {
        this.maPt = maPt;
    }

    public LocalDateTime getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(LocalDateTime thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }
}