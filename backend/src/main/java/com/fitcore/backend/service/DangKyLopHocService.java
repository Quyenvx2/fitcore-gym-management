package com.fitcore.backend.service;

import com.fitcore.backend.entity.DangKyGoi;
import com.fitcore.backend.entity.DangKyLopHoc;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.entity.LopHoc;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.DangKyGoiRepository;
import com.fitcore.backend.repository.DangKyLopHocRepository;
import com.fitcore.backend.repository.HoiVienRepository;
import com.fitcore.backend.repository.LopHocRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fitcore.backend.dto.DangKyLopHocResponseDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class DangKyLopHocService {

    private final DangKyLopHocRepository dangKyLopHocRepository;
    private final DangKyGoiRepository dangKyGoiRepository;
    private final HoiVienRepository hoiVienRepository;
    private final LopHocRepository lopHocRepository;

    public DangKyLopHocService(
            DangKyLopHocRepository dangKyLopHocRepository,
            DangKyGoiRepository dangKyGoiRepository,
            HoiVienRepository hoiVienRepository,
            LopHocRepository lopHocRepository
    ) {
        this.dangKyLopHocRepository = dangKyLopHocRepository;
        this.dangKyGoiRepository = dangKyGoiRepository;
        this.hoiVienRepository = hoiVienRepository;
        this.lopHocRepository = lopHocRepository;
    }

   
    @Transactional
    public DangKyLopHocResponseDTO dangKyLopHoc(
            Integer maHv,
            Integer maLop
    ) {


        HoiVien hoiVien = hoiVienRepository.findById(maHv)
                .orElseThrow(() -> new BusinessException(
                        "HOI_VIEN_NOT_FOUND",
                        "Không tìm thấy hội viên",
                        HttpStatus.NOT_FOUND
                ));

        if (!"Đang hoạt động".equals(hoiVien.getTrangThai())) {
            throw new BusinessException(
                    "HOI_VIEN_INACTIVE",
                    "Hội viên không còn hoạt động",
                    HttpStatus.BAD_REQUEST
            );
        }

        

        LopHoc lopHoc = lopHocRepository.findById(maLop)
                .orElseThrow(() -> new BusinessException(
                        "LOP_NOT_FOUND",
                        "Không tìm thấy lớp học",
                        HttpStatus.NOT_FOUND
                ));

       
        capNhatTrangThaiLop(lopHoc);

        if ("Đã đóng".equals(lopHoc.getTrangThai())) {
            throw new BusinessException(
                    "LOP_CLOSED",
                    "Lớp học đã đóng",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (lopHoc.getSoNguoiDaDangKy()
                >= lopHoc.getPhongTap().getSucChua()) {

            throw new BusinessException(
                    "LOP_FULL",
                    "Lớp học đã đủ số người",
                    HttpStatus.BAD_REQUEST
            );
        }

       

        boolean daDangKy =
                dangKyLopHocRepository
                        .existsByHoiVien_MaHvAndLopHoc_MaLopAndTrangThaiNot(
                                maHv,
                                maLop,
                                "Đã bị hủy"
                        );

        if (daDangKy) {
            throw new BusinessException(
                    "ALREADY_REGISTERED",
                    "Hội viên đã đăng ký lớp học này",
                    HttpStatus.BAD_REQUEST
            );
        }

        

        LocalDate homNay = LocalDate.now();

        List<DangKyGoi> danhSachGoi =
                dangKyGoiRepository.findByHoiVien_MaHvAndTrangThaiIn(
                        maHv,
                        List.of("Đang sử dụng")
                );

        DangKyGoi goiDangSuDung = danhSachGoi.stream()
                .filter(goi ->
                        !goi.getNgayBatDau().isAfter(homNay)
                                && !goi.getNgayHetHan().isBefore(homNay)
                )
                .max(Comparator.comparing(DangKyGoi::getNgayHetHan))
                .orElseThrow(() -> new BusinessException(
                        "NO_ACTIVE_PACKAGE",
                        "Hội viên không có gói tập còn hiệu lực",
                        HttpStatus.BAD_REQUEST
                ));

        

        LocalDate ngayHetHanGoi =
                goiDangSuDung.getNgayHetHan();

        LocalDate ngayKetThucLop =
                lopHoc.getNgayKetThuc();

        LocalDate ngayHetHanDangKyLop;

        if (ngayHetHanGoi.isBefore(ngayKetThucLop)) {
            ngayHetHanDangKyLop = ngayHetHanGoi;
        } else {
            ngayHetHanDangKyLop = ngayKetThucLop;
        }


        DangKyLopHoc dangKy = new DangKyLopHoc();

        dangKy.setHoiVien(hoiVien);
        dangKy.setLopHoc(lopHoc);
        dangKy.setThoiGianDangKy(LocalDateTime.now());
        dangKy.setNgayHetHan(ngayHetHanDangKyLop);
        dangKy.setNgayHuy(null);
        dangKy.setTrangThai("Đăng kí thành công");

        DangKyLopHoc dangKyDaLuu =
                dangKyLopHocRepository.save(dangKy);

        

        lopHoc.setSoNguoiDaDangKy(
                lopHoc.getSoNguoiDaDangKy() + 1
        );

        capNhatTrangThaiLop(lopHoc);

        lopHocRepository.save(lopHoc);

        return  chuyenSangResponseDTO(dangKyDaLuu);
    }

    
    @Transactional
    public DangKyLopHocResponseDTO huyDangKyLopHoc(
        Integer maHv,
        Integer maLop
        ) {

      

        DangKyLopHoc dangKy =
                dangKyLopHocRepository
                        .findTopByHoiVien_MaHvAndLopHoc_MaLopOrderByMaDkLopDesc(
                                maHv,
                                maLop
                        )
                        .orElseThrow(() -> new BusinessException(
                                "REGISTRATION_NOT_FOUND",
                                "Không tìm thấy đăng ký lớp học",
                                HttpStatus.NOT_FOUND
                        ));

       

        if ("Đã bị hủy".equals(dangKy.getTrangThai())) {
            throw new BusinessException(
                    "REGISTRATION_ALREADY_CANCELLED",
                    "Đăng ký lớp học đã được hủy",
                    HttpStatus.BAD_REQUEST
            );
        }

       

        dangKy.setTrangThai("Đã bị hủy");
        dangKy.setNgayHuy(LocalDateTime.now());

        DangKyLopHoc dangKyDaHuy =
                dangKyLopHocRepository.save(dangKy);

      

        LopHoc lopHoc = dangKy.getLopHoc();

        int soNguoiHienTai = lopHoc.getSoNguoiDaDangKy();

        if (soNguoiHienTai > 0) {
            lopHoc.setSoNguoiDaDangKy(
                    soNguoiHienTai - 1
            );
        }

        capNhatTrangThaiLop(lopHoc);

        lopHocRepository.save(lopHoc);

        return chuyenSangResponseDTO(dangKyDaHuy);
    }

  
    private void capNhatTrangThaiLop(LopHoc lopHoc) {

        LocalDate homNay = LocalDate.now();

   
        if (homNay.isAfter(lopHoc.getNgayKetThuc())) {

            lopHoc.setTrangThai("Đã đóng");

            return;
        }


        if (lopHoc.getSoNguoiDaDangKy()
                >= lopHoc.getPhongTap().getSucChua()) {

            lopHoc.setTrangThai("Đã đủ người");

            return;
        }

        lopHoc.setTrangThai("Lớp đang mở");
    }

    private DangKyLopHocResponseDTO chuyenSangResponseDTO(DangKyLopHoc dangKy) {
    return new DangKyLopHocResponseDTO(
            dangKy.getMaDkLop(),
            dangKy.getHoiVien().getMaHv(),
            dangKy.getLopHoc().getMaLop(),
            dangKy.getThoiGianDangKy(),
            dangKy.getNgayHetHan(),
            dangKy.getNgayHuy(),
            dangKy.getTrangThai()
    );
}

public List<DangKyLopHocResponseDTO> layDanhSachLopCuaToi(
        Integer maHv
) {

    return dangKyLopHocRepository
            .findByHoiVien_MaHvAndTrangThai(
                    maHv,
                    "Đăng kí thành công"
            )
            .stream()
            .map(this::chuyenSangResponseDTO)
            .toList();
}

}