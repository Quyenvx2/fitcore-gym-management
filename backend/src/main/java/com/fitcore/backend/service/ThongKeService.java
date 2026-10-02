package com.fitcore.backend.service;

import com.fitcore.backend.dto.ThongKeResponseDTO;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.entity.Pt;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.HoiVienRepository;
import com.fitcore.backend.repository.PtRepository;
import com.fitcore.backend.repository.TaiKhoanRepository;
import com.fitcore.backend.repository.ThongKeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ThongKeService {

    private final ThongKeRepository thongKeRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final HoiVienRepository hoiVienRepository;
    private final PtRepository ptRepository;


    public ThongKeService(
            ThongKeRepository thongKeRepository,
            TaiKhoanRepository taiKhoanRepository,
            HoiVienRepository hoiVienRepository,
            PtRepository ptRepository
    ) {
        this.thongKeRepository = thongKeRepository;
        this.taiKhoanRepository = taiKhoanRepository;
        this.hoiVienRepository = hoiVienRepository;
        this.ptRepository = ptRepository;
    }


    // =========================================================
    // NHÂN VIÊN
    // =========================================================

    public ThongKeResponseDTO thongKeTongQuan() {

        return new ThongKeResponseDTO(

                // Hội viên
                thongKeRepository.demTongHoiVien(),
                thongKeRepository.demHoiVienDangHoatDong(),

                // PT
                thongKeRepository.demTongPt(),
                thongKeRepository.demPtDangLamViec(),

                // Hệ thống
                thongKeRepository.demGoiTapDangSuDung(),
                thongKeRepository.demTongLopHoc(),
                thongKeRepository.demTongBuoiPt(),
                thongKeRepository.demTongLuotCheckIn(),

                // PT
                null,
                null,
                null,
                null,

                // Hội viên
                null,
                null,
                null,
                null,
                null
        );
    }


    // =========================================================
    // PT
    // =========================================================

    public ThongKeResponseDTO thongKePt(String tenDangNhap) {

        Pt pt = layPtTheoTenDangNhap(tenDangNhap);

        return new ThongKeResponseDTO(

                // Nhân viên - không sử dụng
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,

                // PT
                thongKeRepository.demSoHocVienCuaPt(pt.getMaPt()),
                thongKeRepository.demSoBuoiPtDaLenLich(pt.getMaPt()),
                thongKeRepository.demSoBuoiPtDaHoanThanh(pt.getMaPt()),
                thongKeRepository.demSoBuoiPtVangMat(pt.getMaPt()),

                // Hội viên - không sử dụng
                null,
                null,
                null,
                null,
                null
        );
    }


    // =========================================================
    // HỘI VIÊN
    // =========================================================

    public ThongKeResponseDTO thongKeHoiVien(String tenDangNhap) {

        HoiVien hoiVien = layHoiVienTheoTenDangNhap(tenDangNhap);

        return new ThongKeResponseDTO(

                // Nhân viên - không sử dụng
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,

                // PT - không sử dụng
                null,
                null,
                null,
                null,

                // Hội viên
                thongKeRepository.demSoGoiTapDaDangKy(
                        hoiVien.getMaHv()
                ),

                thongKeRepository.demSoBuoiPtDaSuDung(
                        hoiVien.getMaHv()
                ),

                thongKeRepository.demSoLopHocDaDangKy(
                        hoiVien.getMaHv()
                ),

                thongKeRepository.demSoLanCheckIn(
                        hoiVien.getMaHv()
                ),

                thongKeRepository.demSoLanDoChiSoCoThe(
                        hoiVien.getMaHv()
                )
        );
    }


    // =========================================================
    // TÌM HỘI VIÊN THEO TÀI KHOẢN
    // =========================================================

    private HoiVien layHoiVienTheoTenDangNhap(
            String tenDangNhap
    ) {

        TaiKhoan taiKhoan = taiKhoanRepository
                .findByTenDangNhap(tenDangNhap)
                .orElseThrow(() ->
                        new BusinessException(
                                "TAI_KHOAN_NOT_FOUND",
                                "Không tìm thấy tài khoản",
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!"HOI_VIEN".equals(taiKhoan.getVaiTro())) {
            throw new BusinessException(
                    "AUTH_INVALID_ROLE",
                    "Tài khoản không phải hội viên",
                    HttpStatus.FORBIDDEN
            );
        }

        return hoiVienRepository
                .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
                .orElseThrow(() ->
                        new BusinessException(
                                "HOI_VIEN_NOT_FOUND",
                                "Tài khoản chưa được liên kết với hội viên",
                                HttpStatus.NOT_FOUND
                        )
                );
    }


    // =========================================================
    // TÌM PT THEO TÀI KHOẢN
    // =========================================================

    private Pt layPtTheoTenDangNhap(
            String tenDangNhap
    ) {

        TaiKhoan taiKhoan = taiKhoanRepository
                .findByTenDangNhap(tenDangNhap)
                .orElseThrow(() ->
                        new BusinessException(
                                "TAI_KHOAN_NOT_FOUND",
                                "Không tìm thấy tài khoản",
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!"PT".equals(taiKhoan.getVaiTro())) {
            throw new BusinessException(
                    "AUTH_INVALID_ROLE",
                    "Tài khoản không phải PT",
                    HttpStatus.FORBIDDEN
            );
        }

        return ptRepository
                .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
                .orElseThrow(() ->
                        new BusinessException(
                                "PT_NOT_FOUND",
                                "Tài khoản chưa được liên kết với PT",
                                HttpStatus.NOT_FOUND
                        )
                );
    }
}