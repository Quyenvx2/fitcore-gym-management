package com.fitcore.backend.service;

import com.fitcore.backend.dto.DangKyGoiRequestDTO;
import com.fitcore.backend.dto.DangKyGoiResponseDTO;
import com.fitcore.backend.entity.DangKyGoi;
import com.fitcore.backend.entity.GoiTap;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.DangKyGoiRepository;
import com.fitcore.backend.repository.GoiTapRepository;
import com.fitcore.backend.repository.HoiVienRepository;
import com.fitcore.backend.repository.TaiKhoanRepository;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fitcore.backend.dto.DangKyGoiHoiVienRequestDTO;

import java.time.LocalDate;
import java.util.List;

@Service 
public class DangKyGoiService {
    private final DangKyGoiRepository dangKyGoiRepository;
    private final HoiVienRepository hoiVienRepository;
    private final GoiTapRepository goiTapRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    public DangKyGoiService(
        DangKyGoiRepository dangKyGoiRepository,
        HoiVienRepository hoiVienRepository,
        GoiTapRepository goiTapRepository,
        TaiKhoanRepository taiKhoanRepository) {

    this.dangKyGoiRepository = dangKyGoiRepository;
    this.hoiVienRepository = hoiVienRepository;
    this.goiTapRepository = goiTapRepository;
    this.taiKhoanRepository = taiKhoanRepository;
    }

    private DangKyGoiResponseDTO chuyenSangResponseDTO( DangKyGoi dangKyGoi) {

        Integer maHv = null;
        Integer maGoi = null;

        if (dangKyGoi.getHoiVien() != null) {
            maHv = dangKyGoi.getHoiVien().getMaHv();
        }

        if (dangKyGoi.getGoiTap() != null) {
            maGoi = dangKyGoi.getGoiTap().getMaGoi();
        }

        return new DangKyGoiResponseDTO(
                dangKyGoi.getMaDkGoi(),
                maHv,
                maGoi,
                dangKyGoi.getNgayBatDau(),
                dangKyGoi.getNgayHetHan(),
                dangKyGoi.getNgayDangKy(),
                dangKyGoi.getTrangThai()
        );
    }

    public List<DangKyGoiResponseDTO> layDanhSachDangKyGoi() {

        return dangKyGoiRepository.findAll()
                .stream()
                .map(this::chuyenSangResponseDTO)
                .toList();
    }

     public DangKyGoiResponseDTO layDangKyGoiTheoId(Integer id) {

        DangKyGoi dangKyGoi = dangKyGoiRepository
                .findById(id)
                .orElseThrow(() ->
                        new BusinessException(
                                "DANG_KY_GOI_NOT_FOUND",
                                "Không tìm thấy đăng ký gói với mã: " + id,
                                HttpStatus.NOT_FOUND
                        )
                );

        return chuyenSangResponseDTO(dangKyGoi);
    }

    public DangKyGoiResponseDTO taoDangKyGoi(
            DangKyGoiRequestDTO request) {

        HoiVien hoiVien = hoiVienRepository
                .findById(request.getMaHv())
                .orElseThrow(() ->
                        new BusinessException(
                                "HOI_VIEN_NOT_FOUND",
                                "Không tìm thấy hội viên với mã: "
                                        + request.getMaHv(),
                                HttpStatus.NOT_FOUND
                        )
                );

        List<DangKyGoi> dangKyDangHoatDong =
        dangKyGoiRepository
                .findByHoiVien_MaHvAndTrangThaiIn(
                        hoiVien.getMaHv(),
                        List.of(
                                "Đang chờ kích hoạt",
                                "Đang sử dụng"
                        )
                );

        if (!dangKyDangHoatDong.isEmpty()) {
                throw new BusinessException(
            "HOI_VIEN_ALREADY_HAS_PACKAGE",
            "Hội viên đã có một gói tập đang chờ kích hoạt hoặc đang sử dụng",
            HttpStatus.BAD_REQUEST);
        }        

        GoiTap goiTap = goiTapRepository
                .findById(request.getMaGoi())
                .orElseThrow(() ->
                        new BusinessException(
                                "GOI_TAP_NOT_FOUND",
                                "Không tìm thấy gói tập với mã: "
                                        + request.getMaGoi(),
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!"Active".equals(goiTap.getTrangThai())) {
            throw new BusinessException(
                    "GOI_TAP_INACTIVE",
                    "Gói tập hiện không hoạt động",
                    HttpStatus.BAD_REQUEST
            );
        }

        LocalDate ngayBatDau = request.getNgayBatDau();

        LocalDate ngayHetHan = ngayBatDau
                .plusMonths(goiTap.getThoiHanThang());

        DangKyGoi dangKyGoi = new DangKyGoi();

        dangKyGoi.setHoiVien(hoiVien);
        dangKyGoi.setGoiTap(goiTap);
        dangKyGoi.setNgayBatDau(ngayBatDau);
        dangKyGoi.setNgayHetHan(ngayHetHan);
        dangKyGoi.setNgayDangKy(LocalDate.now());


        dangKyGoi.setTrangThai("Đang chờ kích hoạt");

        DangKyGoi savedDangKyGoi =
                dangKyGoiRepository.save(dangKyGoi);

        return chuyenSangResponseDTO(savedDangKyGoi);
    }

    public DangKyGoiResponseDTO taoDangKyGoiChoHoiVien(
        DangKyGoiHoiVienRequestDTO request,
        String tenDangNhap) {

    TaiKhoan taiKhoan = taiKhoanRepository
            .findByTenDangNhap(tenDangNhap)
            .orElseThrow(() ->
                    new BusinessException(
                            "TAI_KHOAN_NOT_FOUND",
                            "Không tìm thấy tài khoản",
                            HttpStatus.NOT_FOUND
                    )
            );

    HoiVien hoiVien = hoiVienRepository
            .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
            .orElseThrow(() ->
                    new BusinessException(
                            "HOI_VIEN_NOT_FOUND",
                            "Tài khoản chưa được liên kết với hội viên",
                            HttpStatus.NOT_FOUND
                    )
            );
    
        List<DangKyGoi> dangKyDangHoatDong =
        dangKyGoiRepository
                .findByHoiVien_MaHvAndTrangThaiIn(
                        hoiVien.getMaHv(),
                        List.of(
                                "Đang chờ kích hoạt",
                                "Đang sử dụng"
                        )
                );

        if (!dangKyDangHoatDong.isEmpty()) {
            throw new BusinessException(
            "HOI_VIEN_ALREADY_HAS_PACKAGE",
            "Hội viên đã có một gói tập đang chờ kích hoạt hoặc đang sử dụng",
            HttpStatus.BAD_REQUEST
        );
}
    
    GoiTap goiTap = goiTapRepository
            .findById(request.getMaGoi())
            .orElseThrow(() ->
                    new BusinessException(
                            "GOI_TAP_NOT_FOUND",
                            "Không tìm thấy gói tập với mã: "
                                    + request.getMaGoi(),
                            HttpStatus.NOT_FOUND
                    )
            );


    if (!"Active".equals(goiTap.getTrangThai())) {
        throw new BusinessException(
                "GOI_TAP_INACTIVE",
                "Gói tập hiện không hoạt động",
                HttpStatus.BAD_REQUEST
        );
    }

    LocalDate ngayBatDau = request.getNgayBatDau();

    LocalDate ngayHetHan = ngayBatDau
            .plusMonths(goiTap.getThoiHanThang());

    DangKyGoi dangKyGoi = new DangKyGoi();

    dangKyGoi.setHoiVien(hoiVien);
    dangKyGoi.setGoiTap(goiTap);
    dangKyGoi.setNgayBatDau(ngayBatDau);
    dangKyGoi.setNgayHetHan(ngayHetHan);
    dangKyGoi.setNgayDangKy(LocalDate.now());
    dangKyGoi.setTrangThai("Đang chờ kích hoạt");

    DangKyGoi savedDangKyGoi =
            dangKyGoiRepository.save(dangKyGoi);

    
    return chuyenSangResponseDTO(savedDangKyGoi);
    }

    public void huyDangKyGoi(Integer id) {

        DangKyGoi dangKyGoi = dangKyGoiRepository
                .findById(id)
                .orElseThrow(() ->
                        new BusinessException(
                                "DANG_KY_GOI_NOT_FOUND",
                                "Không tìm thấy đăng ký gói với mã: " + id,
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!"Đang chờ kích hoạt"
                .equals(dangKyGoi.getTrangThai())) {

            throw new BusinessException(
                    "DANG_KY_GOI_CANNOT_CANCEL",
                    "Chỉ có thể hủy đăng ký đang chờ kích hoạt",
                    HttpStatus.BAD_REQUEST
            );
        }


        dangKyGoi.setTrangThai("Đã hủy");

        dangKyGoiRepository.save(dangKyGoi);
    }

    public DangKyGoiResponseDTO kichHoatDangKyGoi(
            Integer id) {

        DangKyGoi dangKyGoi = dangKyGoiRepository
                .findById(id)
                .orElseThrow(() ->
                        new BusinessException(
                                "DANG_KY_GOI_NOT_FOUND",
                                "Không tìm thấy đăng ký gói với mã: " + id,
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!"Đang chờ kích hoạt"
                .equals(dangKyGoi.getTrangThai())) {

            throw new BusinessException(
                    "DANG_KY_GOI_CANNOT_ACTIVATE",
                    "Chỉ có thể kích hoạt đăng ký đang chờ kích hoạt",
                    HttpStatus.BAD_REQUEST
            );
        }

        List<DangKyGoi> dangKyDangSuDung =
        dangKyGoiRepository
                .findByHoiVien_MaHvAndTrangThaiIn(
                        dangKyGoi.getHoiVien().getMaHv(),
                        List.of("Đang sử dụng")
                );

        if (!dangKyDangSuDung.isEmpty()) {
            throw new BusinessException(
            "HOI_VIEN_ALREADY_HAS_ACTIVE_PACKAGE",
            "Hội viên đang có một gói tập đang sử dụng",
            HttpStatus.BAD_REQUEST);
        }
        dangKyGoi.setTrangThai("Đang sử dụng");

        DangKyGoi savedDangKyGoi =
                dangKyGoiRepository.save(dangKyGoi);

        return chuyenSangResponseDTO(savedDangKyGoi);
    }

      public List<DangKyGoiResponseDTO> layDangKyGoiTheoHoiVien(
            Integer maHv) {

        if (!hoiVienRepository.existsById(maHv)) {

            throw new BusinessException(
                    "HOI_VIEN_NOT_FOUND",
                    "Không tìm thấy hội viên với mã: " + maHv,
                    HttpStatus.NOT_FOUND
            );
        }

        return dangKyGoiRepository
                .findByHoiVien_MaHv(maHv)
                .stream()
                .map(this::chuyenSangResponseDTO)
                .toList();
    }

    public List<DangKyGoiResponseDTO> layDangKyGoiCuaToi(
        String tenDangNhap) {

    TaiKhoan taiKhoan = taiKhoanRepository
            .findByTenDangNhap(tenDangNhap)
            .orElseThrow(() ->
                    new BusinessException(
                            "TAI_KHOAN_NOT_FOUND",
                            "Không tìm thấy tài khoản",
                            HttpStatus.NOT_FOUND
                    )
            );

    HoiVien hoiVien = hoiVienRepository
            .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
            .orElseThrow(() ->
                    new BusinessException(
                            "HOI_VIEN_NOT_FOUND",
                            "Tài khoản chưa được liên kết với hội viên",
                            HttpStatus.NOT_FOUND
                    )
            );

    return dangKyGoiRepository
            .findByHoiVien_MaHv(hoiVien.getMaHv())
            .stream()
            .map(this::chuyenSangResponseDTO)
            .toList();
    }

    public void huyDangKyGoiCuaToi(
        Integer id,
        String tenDangNhap) {

    TaiKhoan taiKhoan = taiKhoanRepository
            .findByTenDangNhap(tenDangNhap)
            .orElseThrow(() ->
                    new BusinessException(
                            "TAI_KHOAN_NOT_FOUND",
                            "Không tìm thấy tài khoản",
                            HttpStatus.NOT_FOUND
                    )
            );

    HoiVien hoiVien = hoiVienRepository
            .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
            .orElseThrow(() ->
                    new BusinessException(
                            "HOI_VIEN_NOT_FOUND",
                            "Tài khoản chưa được liên kết với hội viên",
                            HttpStatus.NOT_FOUND
                    )
            );

    DangKyGoi dangKyGoi = dangKyGoiRepository
            .findById(id)
            .orElseThrow(() ->
                    new BusinessException(
                            "DANG_KY_GOI_NOT_FOUND",
                            "Không tìm thấy đăng ký gói với mã: " + id,
                            HttpStatus.NOT_FOUND
                    )
            );

    if (!dangKyGoi.getHoiVien().getMaHv()
            .equals(hoiVien.getMaHv())) {

        throw new BusinessException(
                "DANG_KY_GOI_FORBIDDEN",
                "Bạn không có quyền hủy đăng ký này",
                HttpStatus.FORBIDDEN
        );
    }

    if (!"Đang chờ kích hoạt"
            .equals(dangKyGoi.getTrangThai())) {

        throw new BusinessException(
                "DANG_KY_GOI_CANNOT_CANCEL",
                "Chỉ có thể hủy đăng ký đang chờ kích hoạt",
                HttpStatus.BAD_REQUEST
        );
    }

    dangKyGoi.setTrangThai("Đã hủy");

    dangKyGoiRepository.save(dangKyGoi);
    }


}
