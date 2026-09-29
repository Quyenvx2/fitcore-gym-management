package com.fitcore.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaiKhoanDangKyRequestDTO {

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50,
            message = "Tên đăng nhập phải từ 3 đến 50 ký tự")
    private String tenDangNhap;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6,
            message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String matKhau;

    public TaiKhoanDangKyRequestDTO() {}

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}