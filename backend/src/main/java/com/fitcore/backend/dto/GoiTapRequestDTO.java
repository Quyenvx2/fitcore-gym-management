package com.fitcore.backend.dto;
import jakarta.validation.constraints.*;
import java.math.*;
public class GoiTapRequestDTO {
     @NotBlank(message = "Tên gói không được để trống")
    private String tenGoi;

    @NotNull(message = "Thời hạn tháng không được để trống")
    @Positive(message = "Thời hạn tháng phải lớn hơn 0")
    private Integer thoiHanThang;

    @NotNull(message = "Giá tiền không được để trống")
    @Positive(message = "Giá tiền phải lớn hơn 0")
    private BigDecimal giaTien;

    @NotNull(message = "Số buổi PT không được để trống")
    @PositiveOrZero(message = "Số buổi PT không được âm")
    private Integer soBuoiPt;

    @NotBlank(message = "Trạng thái không được để trống")
    private String trangThai;

    public GoiTapRequestDTO() {
    }

    public String getTenGoi() {
        return tenGoi;
    }

    public void setTenGoi(String tenGoi) {
        this.tenGoi = tenGoi;
    }

    public Integer getThoiHanThang() {
        return thoiHanThang;
    }

    public void setThoiHanThang(Integer thoiHanThang) {
        this.thoiHanThang = thoiHanThang;
    }

    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }

    public Integer getSoBuoiPt() {
        return soBuoiPt;
    }

    public void setSoBuoiPt(Integer soBuoiPt) {
        this.soBuoiPt = soBuoiPt;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
