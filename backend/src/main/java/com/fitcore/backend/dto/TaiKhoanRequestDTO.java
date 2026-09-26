package com.fitcore.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class TaiKhoanRequestDTO {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50,
          message = "Tên đăng nhập phải từ 3 đến 50 ký tự")
    private String tenDangNhap;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String matKhau;

    @NotBlank(message = "Vai trò không được để trống")
    private String vaiTro;

    @NotBlank(message = "Trạng thái không được để trống")
    private String trangThai;

    public TaiKhoanRequestDTO(){}
    public String getTenDangNhap(){
        return tenDangNhap;
    }
    public String getMatKhau(){
        return matKhau;
    }
    public String getVaiTro(){
        return vaiTro;
    }
    public String getTrangThai(){
        return trangThai;
    }

    public void setTenDangNhap(String tenDangNhap){
        this.tenDangNhap=tenDangNhap;
    }
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
     public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
