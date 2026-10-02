package com.fitcore.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CHECK_IN_OUT")
public class CheckInOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_luot")
    private Integer maLuot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hv", nullable = false)
    private HoiVien hoiVien;

    @Column(name = "thoi_gian_check_in", nullable = false)
    private LocalDateTime thoiGianCheckIn;

    @Column(name = "thoi_gian_check_out")
    private LocalDateTime thoiGianCheckOut;

    public CheckInOut() {
    }

    public Integer getMaLuot() {
        return maLuot;
    }

    public void setMaLuot(Integer maLuot) {
        this.maLuot = maLuot;
    }

    public HoiVien getHoiVien() {
        return hoiVien;
    }

    public void setHoiVien(HoiVien hoiVien) {
        this.hoiVien = hoiVien;
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