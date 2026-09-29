package com.fitcore.backend.dto;

public class PhongTapResponseDTO {

    private Integer maPhong;
    private String tenPhong;
    private String viTri;
    private Integer sucChua;
    private String trangThai;

    public PhongTapResponseDTO() {
    }

    public PhongTapResponseDTO(
            Integer maPhong,
            String tenPhong,
            String viTri,
            Integer sucChua,
            String trangThai) {

        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.viTri = viTri;
        this.sucChua = sucChua;
        this.trangThai = trangThai;
    }

    public Integer getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(Integer maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public Integer getSucChua() {
        return sucChua;
    }

    public void setSucChua(Integer sucChua) {
        this.sucChua = sucChua;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}