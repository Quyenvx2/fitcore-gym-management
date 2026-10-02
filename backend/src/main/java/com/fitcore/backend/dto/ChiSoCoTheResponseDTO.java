package com.fitcore.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ChiSoCoTheResponseDTO {

    private Integer maLanDo;
    private Integer maHv;
    private LocalDate ngayDo;
    private BigDecimal canNang;
    private BigDecimal chieuCao;
    private BigDecimal phanTramMo;
    private BigDecimal vongEo;
    private String ghiChu;

    public ChiSoCoTheResponseDTO() {
    }

    public ChiSoCoTheResponseDTO(
            Integer maLanDo,
            Integer maHv,
            LocalDate ngayDo,
            BigDecimal canNang,
            BigDecimal chieuCao,
            BigDecimal phanTramMo,
            BigDecimal vongEo,
            String ghiChu
    ) {
        this.maLanDo = maLanDo;
        this.maHv = maHv;
        this.ngayDo = ngayDo;
        this.canNang = canNang;
        this.chieuCao = chieuCao;
        this.phanTramMo = phanTramMo;
        this.vongEo = vongEo;
        this.ghiChu = ghiChu;
    }

    public Integer getMaLanDo() {
        return maLanDo;
    }

    public void setMaLanDo(Integer maLanDo) {
        this.maLanDo = maLanDo;
    }

    public Integer getMaHv() {
        return maHv;
    }

    public void setMaHv(Integer maHv) {
        this.maHv = maHv;
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