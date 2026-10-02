package com.fitcore.backend.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ChiSoCoTheRequestDTO {

    @NotNull(message = "Ngày đo không được để trống")
    @PastOrPresent(message = "Ngày đo không được ở tương lai")
    private LocalDate ngayDo;

    @NotNull(message = "Cân nặng không được để trống")
    @Positive(message = "Cân nặng phải lớn hơn 0")
    private BigDecimal canNang;

    @NotNull(message = "Chiều cao không được để trống")
    @Positive(message = "Chiều cao phải lớn hơn 0")
    private BigDecimal chieuCao;

    @NotNull(message = "Phần trăm mỡ không được để trống")
    @DecimalMin(
            value = "0.0",
            message = "Phần trăm mỡ không được nhỏ hơn 0"
    )
    @DecimalMax(
            value = "100.0",
            message = "Phần trăm mỡ không được lớn hơn 100"
    )
    private BigDecimal phanTramMo;

    @NotNull(message = "Vòng eo không được để trống")
    @Positive(message = "Vòng eo phải lớn hơn 0")
    private BigDecimal vongEo;

    private String ghiChu;

    public ChiSoCoTheRequestDTO() {
    }

    public LocalDate getNgayDo() {
        return ngayDo;
    }

    public void setNgayDo(LocalDate ngayDo) {
        this.ngayDo = ngayDo;
    }

    public BigDecimal getCanNang() {
        return canNang;
    }

    public void setCanNang(BigDecimal canNang) {
        this.canNang = canNang;
    }

    public BigDecimal getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(BigDecimal chieuCao) {
        this.chieuCao = chieuCao;
    }

    public BigDecimal getPhanTramMo() {
        return phanTramMo;
    }

    public void setPhanTramMo(BigDecimal phanTramMo) {
        this.phanTramMo = phanTramMo;
    }

    public BigDecimal getVongEo() {
        return vongEo;
    }

    public void setVongEo(BigDecimal vongEo) {
        this.vongEo = vongEo;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}