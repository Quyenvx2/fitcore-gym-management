package com.fitcore.backend.dto;

public class ThongKeResponseDTO {

    // =========================
    // NHÂN VIÊN
    // =========================

    private Long tongHoiVien;
    private Long hoiVienDangHoatDong;

    private Long tongPt;
    private Long ptDangLamViec;

    private Long tongGoiTapDangSuDung;
    private Long tongLopHoc;
    private Long tongBuoiPt;
    private Long tongLuotCheckIn;


    // =========================
    // PT
    // =========================

    private Long soHocVien;
    private Long soBuoiPtDaLenLich;
    private Long soBuoiPtDaHoanThanh;
    private Long soBuoiPtVangMat;


    // =========================
    // HỘI VIÊN
    // =========================

    private Long soGoiTapDaDangKy;
    private Long soBuoiPtDaSuDung;
    private Long soLopHocDaDangKy;
    private Long soLanCheckIn;
    private Long soLanDoChiSoCoThe;


    public ThongKeResponseDTO(
            Long tongHoiVien,
            Long hoiVienDangHoatDong,
            Long tongPt,
            Long ptDangLamViec,
            Long tongGoiTapDangSuDung,
            Long tongLopHoc,
            Long tongBuoiPt,
            Long tongLuotCheckIn,
            Long soHocVien,
            Long soBuoiPtDaLenLich,
            Long soBuoiPtDaHoanThanh,
            Long soBuoiPtVangMat,
            Long soGoiTapDaDangKy,
            Long soBuoiPtDaSuDung,
            Long soLopHocDaDangKy,
            Long soLanCheckIn,
            Long soLanDoChiSoCoThe
    ) {
        this.tongHoiVien = tongHoiVien;
        this.hoiVienDangHoatDong = hoiVienDangHoatDong;
        this.tongPt = tongPt;
        this.ptDangLamViec = ptDangLamViec;
        this.tongGoiTapDangSuDung = tongGoiTapDangSuDung;
        this.tongLopHoc = tongLopHoc;
        this.tongBuoiPt = tongBuoiPt;
        this.tongLuotCheckIn = tongLuotCheckIn;

        this.soHocVien = soHocVien;
        this.soBuoiPtDaLenLich = soBuoiPtDaLenLich;
        this.soBuoiPtDaHoanThanh = soBuoiPtDaHoanThanh;
        this.soBuoiPtVangMat = soBuoiPtVangMat;

        this.soGoiTapDaDangKy = soGoiTapDaDangKy;
        this.soBuoiPtDaSuDung = soBuoiPtDaSuDung;
        this.soLopHocDaDangKy = soLopHocDaDangKy;
        this.soLanCheckIn = soLanCheckIn;
        this.soLanDoChiSoCoThe = soLanDoChiSoCoThe;
    }


    public Long getTongHoiVien() {
        return tongHoiVien;
    }

    public Long getHoiVienDangHoatDong() {
        return hoiVienDangHoatDong;
    }

    public Long getTongPt() {
        return tongPt;
    }

    public Long getPtDangLamViec() {
        return ptDangLamViec;
    }

    public Long getTongGoiTapDangSuDung() {
        return tongGoiTapDangSuDung;
    }

    public Long getTongLopHoc() {
        return tongLopHoc;
    }

    public Long getTongBuoiPt() {
        return tongBuoiPt;
    }

    public Long getTongLuotCheckIn() {
        return tongLuotCheckIn;
    }

    public Long getSoHocVien() {
        return soHocVien;
    }

    public Long getSoBuoiPtDaLenLich() {
        return soBuoiPtDaLenLich;
    }

    public Long getSoBuoiPtDaHoanThanh() {
        return soBuoiPtDaHoanThanh;
    }

    public Long getSoBuoiPtVangMat() {
        return soBuoiPtVangMat;
    }

    public Long getSoGoiTapDaDangKy() {
        return soGoiTapDaDangKy;
    }

    public Long getSoBuoiPtDaSuDung() {
        return soBuoiPtDaSuDung;
    }

    public Long getSoLopHocDaDangKy() {
        return soLopHocDaDangKy;
    }

    public Long getSoLanCheckIn() {
        return soLanCheckIn;
    }

    public Long getSoLanDoChiSoCoThe() {
        return soLanDoChiSoCoThe;
    }
}