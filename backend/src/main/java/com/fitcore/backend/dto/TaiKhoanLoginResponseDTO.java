package com.fitcore.backend.dto;

public class TaiKhoanLoginResponseDTO {

    private String token;
    private Integer maTk;
    private String tenDangNhap;
    private String vaiTro;
    private String trangThai;

    public TaiKhoanLoginResponseDTO() {
    }

    public TaiKhoanLoginResponseDTO(
            String token,
            Integer maTk,
            String tenDangNhap,
            String vaiTro,
            String trangThai) {

        this.token = token;
        this.maTk = maTk;
        this.tenDangNhap = tenDangNhap;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMaTk() {
        return maTk;
    }

    public void setMaTk(Integer maTk) {
        this.maTk = maTk;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}