package com.fitcore.backend.dto;

public class PtDanhSachResponseDTO {

    private Integer maPt;
    private String hoTen;
    private String chuyenMon;
    private Integer soNamKinhNghiem;

    public PtDanhSachResponseDTO() {
    }

    public PtDanhSachResponseDTO(
            Integer maPt,
            String hoTen,
            String chuyenMon,
            Integer soNamKinhNghiem
    ) {
        this.maPt = maPt;
        this.hoTen = hoTen;
        this.chuyenMon = chuyenMon;
        this.soNamKinhNghiem = soNamKinhNghiem;
    }

    public Integer getMaPt() {
        return maPt;
    }

    public void setMaPt(Integer maPt) {
        this.maPt = maPt;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getChuyenMon() {
        return chuyenMon;
    }

    public void setChuyenMon(String chuyenMon) {
        this.chuyenMon = chuyenMon;
    }

    public Integer getSoNamKinhNghiem() {
        return soNamKinhNghiem;
    }

    public void setSoNamKinhNghiem(Integer soNamKinhNghiem) {
        this.soNamKinhNghiem = soNamKinhNghiem;
    }
}