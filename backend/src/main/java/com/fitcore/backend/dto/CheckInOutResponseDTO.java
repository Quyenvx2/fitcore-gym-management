package com.fitcore.backend.dto;

import java.time.LocalDateTime;

public class CheckInOutResponseDTO {

    private Integer maLuot;
    private Integer maHv;
    private LocalDateTime thoiGianCheckIn;
    private LocalDateTime thoiGianCheckOut;

    public CheckInOutResponseDTO() {
    }

    public CheckInOutResponseDTO(
            Integer maLuot,
            Integer maHv,
            LocalDateTime thoiGianCheckIn,
            LocalDateTime thoiGianCheckOut
    ) {
        this.maLuot = maLuot;
        this.maHv = maHv;
        this.thoiGianCheckIn = thoiGianCheckIn;
        this.thoiGianCheckOut = thoiGianCheckOut;
    }

    public Integer getMaLuot() {
        return maLuot;
    }

    public void setMaLuot(Integer maLuot) {
        this.maLuot = maLuot;
    }

    public Integer getMaHv() {
        return maHv;
    }

    public void setMaHv(Integer maHv) {
        this.maHv = maHv;
    }

    public LocalDateTime getThoiGianCheckIn() {
        return thoiGianCheckIn;
    }

    public void setThoiGianCheckIn(LocalDateTime thoiGianCheckIn) {
        this.thoiGianCheckIn = thoiGianCheckIn;
    }

    public LocalDateTime getThoiGianCheckOut() {
        return thoiGianCheckOut;
    }

    public void setThoiGianCheckOut(LocalDateTime thoiGianCheckOut) {
        this.thoiGianCheckOut = thoiGianCheckOut;
    }
}