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

        kiemTraTrungLichLop(
        maHv,
        lopHoc,
        ngayHetHanDangKyLop
        );
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

    // Kiểm tra các lớp hội viên đã đăng ký thành công
private void kiemTraTrungLichLop(
        Integer maHv,
        LopHoc lopMoi,
        LocalDate ngayHetHanLopMoi
) {
    List<DangKyLopHoc> danhSachDangKy =
            dangKyLopHocRepository.findByHoiVien_MaHvAndTrangThai(
                    maHv,
                    "Đăng kí thành công"
            );

    for (DangKyLopHoc dangKyCu : danhSachDangKy) {
        LopHoc lopCu = dangKyCu.getLopHoc();

        // Bỏ qua nếu cùng mã lớp
        if (lopCu.getMaLop().equals(lopMoi.getMaLop())) {
            continue;
        }

        LocalDate ngayHetHanLopCu = dangKyCu.getNgayHetHan();

        // Dự phòng trường hợp đăng ký cũ chưa có ngày hết hạn
        if (ngayHetHanLopCu == null) {
            ngayHetHanLopCu = lopCu.getNgayKetThuc();
        }

        if (coTrungLich(
                lopMoi,
                lopCu,
                ngayHetHanLopMoi,
                ngayHetHanLopCu
        )) {
            throw new BusinessException(
                    "SCHEDULE_CONFLICT",
                    "Lịch lớp học bị trùng với lớp '"
                            + lopCu.getTenLop()
                            + "'. Vui lòng chọn lớp có khung giờ khác.",
                    HttpStatus.CONFLICT
            );
        }
    }
}


// Kiểm tra hai lớp có trùng ngày học và giờ học không
private boolean coTrungLich(
        LopHoc lopA,
        LopHoc lopB,
        LocalDate ngayHetHanA,
        LocalDate ngayHetHanB
) {
    // Hai khoảng giờ giao nhau khi điều kiện này đúng
    boolean trungGio =
            lopA.getGioBatDau().isBefore(lopB.getGioKetThuc())
            && lopB.getGioBatDau().isBefore(lopA.getGioKetThuc());

    if (!trungGio) {
        return false;
    }

    // Ngày bắt đầu chung: lấy ngày muộn hơn
    LocalDate ngayDau =
            lopA.getNgayBatDau().isAfter(lopB.getNgayBatDau())
                    ? lopA.getNgayBatDau()
                    : lopB.getNgayBatDau();

    // Không xét những buổi đã qua
    LocalDate homNay = LocalDate.now();

    if (homNay.isAfter(ngayDau)) {
        ngayDau = homNay;
    }

    // Ngày kết thúc chung: lấy ngày sớm nhất
    LocalDate ngayCuoi =
            lopA.getNgayKetThuc().isBefore(lopB.getNgayKetThuc())
                    ? lopA.getNgayKetThuc()
                    : lopB.getNgayKetThuc();

    if (ngayHetHanA.isBefore(ngayCuoi)) {
        ngayCuoi = ngayHetHanA;
    }

    if (ngayHetHanB.isBefore(ngayCuoi)) {
        ngayCuoi = ngayHetHanB;
    }

    // Không có khoảng ngày giao nhau
    if (ngayDau.isAfter(ngayCuoi)) {
        return false;
    }

    // Duyệt các ngày trong khoảng giao nhau
    for (LocalDate ngay = ngayDau;
         !ngay.isAfter(ngayCuoi);
         ngay = ngay.plusDays(1)) {

        String thu = chuyenNgaySangThu(ngay);

        if (coHocVaoThu(lopA, thu)
                && coHocVaoThu(lopB, thu)) {
            return true;
        }
    }

    return false;
}


// Kiểm tra một lớp có học vào thứ được xét không
private boolean coHocVaoThu(LopHoc lopHoc, String thu) {
    if (lopHoc.getThuHoc() == null
            || lopHoc.getThuHoc().isBlank()) {
        return false;
    }

    for (String thuDaCauHinh : lopHoc.getThuHoc().split(",")) {
        if (thuDaCauHinh.trim().equals(thu)) {
            return true;
        }
    }

    return false;
}


// Chuyển ngày trong tuần sang đúng định dạng thu_hoc của database
private String chuyenNgaySangThu(LocalDate ngay) {
    return switch (ngay.getDayOfWeek()) {
        case MONDAY -> "2";
        case TUESDAY -> "3";
        case WEDNESDAY -> "4";
        case THURSDAY -> "5";
        case FRIDAY -> "6";
        case SATURDAY -> "7";
        case SUNDAY -> "CN";
    };
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