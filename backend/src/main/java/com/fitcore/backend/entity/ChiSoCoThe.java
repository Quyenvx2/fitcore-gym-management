package com.fitcore.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CHI_SO_CO_THE")
public class ChiSoCoThe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_lan_do")
    private Integer maLanDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hv", nullable = false)
    private HoiVien hoiVien;

    @Column(name = "ngay_do", nullable = false)
    private LocalDate ngayDo;

    @Column(name = "can_nang", nullable = false)
    private BigDecimal canNang;

    @Column(name = "chieu_cao", nullable = false)
    private BigDecimal chieuCao;

    @Column(name = "phan_tram_mo", nullable = false)
    private BigDecimal phanTramMo;

    @Column(name = "vong_eo", nullable = false)
    private BigDecimal vongEo;

    @Column(name = "ghi_chu")
    private String ghiChu;

    public ChiSoCoThe() {
    }

    public Integer getMaLanDo() {
        return maLanDo;
    }

    public void setMaLanDo(Integer maLanDo) {
        this.maLanDo = maLanDo;
    }

    public HoiVien getHoiVien() {
        return hoiVien;
    }

    public void setHoiVien(HoiVien hoiVien) {
        this.hoiVien = hoiVien;
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