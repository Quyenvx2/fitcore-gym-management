package com.fitcore.backend.service;

import com.fitcore.backend.dto.LopHocRequestDTO;
import com.fitcore.backend.dto.LopHocResponseDTO;
import com.fitcore.backend.entity.BuoiHoc;
import com.fitcore.backend.entity.LopHoc;
import com.fitcore.backend.entity.PhongTap;
import com.fitcore.backend.entity.Pt;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.LopHocRepository;
import com.fitcore.backend.repository.PhongTapRepository;
import com.fitcore.backend.repository.PtRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fitcore.backend.repository.BuoiHocRepository;

import com.fitcore.backend.dto.BuoiHocResponseDTO;
import com.fitcore.backend.entity.BuoiHoc;


import java.time.LocalDate;
import java.util.List;

@Service
public class LopHocService {

    private final LopHocRepository lopHocRepository;
    private final PhongTapRepository phongTapRepository;
    private final PtRepository ptRepository;
        private final BuoiHocRepository buoiHocRepository;
    public LopHocService(
        LopHocRepository lopHocRepository,
        PhongTapRepository phongTapRepository,
        PtRepository ptRepository,
        BuoiHocRepository buoiHocRepository) {

    this.lopHocRepository = lopHocRepository;
    this.phongTapRepository = phongTapRepository;
    this.ptRepository = ptRepository;
    this.buoiHocRepository = buoiHocRepository;
}

  

    public List<LopHocResponseDTO> layDanhSachLop() {

        return lopHocRepository.findAll()
                .stream()
                .map(this::chuyenSangResponse)
                .toList();
    }


    public LopHocResponseDTO layLopTheoId(Integer id) {

        LopHoc lopHoc = lopHocRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "LOP_NOT_FOUND",
                        "Không tìm thấy lớp học",
                        HttpStatus.NOT_FOUND
                ));

        capNhatTrangThai(lopHoc);

        return chuyenSangResponse(lopHoc);
    }



    public LopHocResponseDTO taoLop(LopHocRequestDTO request) {

        kiemTraDuLieu(request);

        if (lopHocRepository.findByTenLop(request.getTenLop()).isPresent()) {

            throw new BusinessException(
                    "LOP_NAME_EXISTS",
                    "Tên lớp học đã tồn tại",
                    HttpStatus.CONFLICT
            );
        }

  
       PhongTap phongTap = phongTapRepository.findById(request.getMaPhong())
        .orElseThrow(() -> new BusinessException(
                "PHONG_NOT_FOUND",
                "Không tìm thấy phòng tập",
                HttpStatus.NOT_FOUND
        ));

if ("Bảo trì".equals(phongTap.getTrangThai())) {
    throw new BusinessException(
            "PHONG_UNAVAILABLE",
            "Phòng tập đang bảo trì, không thể sử dụng cho lớp học",
            HttpStatus.BAD_REQUEST
    );
}

Pt pt = ptRepository.findById(request.getMaPt())
        .orElseThrow(() -> new BusinessException(
                "PT_NOT_FOUND",
                "Không tìm thấy PT",
                HttpStatus.NOT_FOUND
        ));

if (!"Đang làm việc".equals(pt.getTrangThai())) {
    throw new BusinessException(
            "PT_UNAVAILABLE",
            "PT hiện không làm việc, không thể phân công cho lớp",
            HttpStatus.BAD_REQUEST
    );
}

    
        LopHoc lopHoc = new LopHoc();

        lopHoc.setPhongTap(phongTap);
        lopHoc.setPt(pt);


        lopHoc.setSoNguoiDaDangKy(0);

        lopHoc.setTenLop(request.getTenLop());
        lopHoc.setDonGiaPt(request.getDonGiaPt());
        lopHoc.setThuHoc(request.getThuHoc());
        lopHoc.setGioBatDau(request.getGioBatDau());
        lopHoc.setGioKetThuc(request.getGioKetThuc());
        lopHoc.setNgayBatDau(request.getNgayBatDau());
        lopHoc.setNgayKetThuc(request.getNgayKetThuc());

        lopHoc.setTrangThai(tinhTrangThai(lopHoc));

        LopHoc lopHocDaLuu = lopHocRepository.save(lopHoc);

        sinhBuoiHoc(lopHocDaLuu);
        return chuyenSangResponse(lopHocDaLuu);
    }



    public LopHocResponseDTO capNhatLop(
            Integer id,
            LopHocRequestDTO request) {

        kiemTraDuLieu(request);

        LopHoc lopHoc = lopHocRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "LOP_NOT_FOUND",
                        "Không tìm thấy lớp học",
                        HttpStatus.NOT_FOUND
                ));

        lopHocRepository.findByTenLop(request.getTenLop())
                .ifPresent(lopTrung -> {

                    if (!lopTrung.getMaLop().equals(id)) {

                        throw new BusinessException(
                                "LOP_NAME_EXISTS",
                                "Tên lớp học đã tồn tại",
                                HttpStatus.CONFLICT
                        );
                    }
                });

   
        PhongTap phongTap = phongTapRepository.findById(request.getMaPhong())
        .orElseThrow(() -> new BusinessException(
                "PHONG_NOT_FOUND",
                "Không tìm thấy phòng tập",
                HttpStatus.NOT_FOUND
        ));

if ("Bảo trì".equals(phongTap.getTrangThai())) {
    throw new BusinessException(
            "PHONG_UNAVAILABLE",
            "Phòng tập đang bảo trì, không thể sử dụng cho lớp học",
            HttpStatus.BAD_REQUEST
    );
}

Pt pt = ptRepository.findById(request.getMaPt())
        .orElseThrow(() -> new BusinessException(
                "PT_NOT_FOUND",
                "Không tìm thấy PT",
                HttpStatus.NOT_FOUND
        ));

if (!"Đang làm việc".equals(pt.getTrangThai())) {
    throw new BusinessException(
            "PT_UNAVAILABLE",
            "PT hiện không làm việc, không thể phân công cho lớp",
            HttpStatus.BAD_REQUEST
    );
}

        lopHoc.setPhongTap(phongTap);
        lopHoc.setPt(pt);
        lopHoc.setTenLop(request.getTenLop());
        lopHoc.setDonGiaPt(request.getDonGiaPt());
        lopHoc.setThuHoc(request.getThuHoc());
        lopHoc.setGioBatDau(request.getGioBatDau());
        lopHoc.setGioKetThuc(request.getGioKetThuc());
        lopHoc.setNgayBatDau(request.getNgayBatDau());
        lopHoc.setNgayKetThuc(request.getNgayKetThuc());

        capNhatTrangThai(lopHoc);

        LopHoc lopHocDaLuu = lopHocRepository.save(lopHoc);

        return chuyenSangResponse(lopHocDaLuu);
    }


    public void xoaLop(Integer id) {

        LopHoc lopHoc = lopHocRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "LOP_NOT_FOUND",
                        "Không tìm thấy lớp học",
                        HttpStatus.NOT_FOUND
                ));

        lopHocRepository.delete(lopHoc);
    }



    private String tinhTrangThai(LopHoc lopHoc) {

        LocalDate homNay = LocalDate.now();

        if (homNay.isAfter(lopHoc.getNgayKetThuc())) {
            return "Đã đóng";
        }

        if (lopHoc.getSoNguoiDaDangKy()
                >= lopHoc.getPhongTap().getSucChua()) {

            return "Đã đủ người";
        }

        return "Lớp đang mở";
    }

  

    private void capNhatTrangThai(LopHoc lopHoc) {

        String trangThaiMoi = tinhTrangThai(lopHoc);

        if (!trangThaiMoi.equals(lopHoc.getTrangThai())) {

            lopHoc.setTrangThai(trangThaiMoi);

            lopHocRepository.save(lopHoc);
        }
    }



    private void kiemTraDuLieu(LopHocRequestDTO request) {

        kiemTraThuHoc(request.getThuHoc());

        if (!request.getGioBatDau().isBefore(request.getGioKetThuc())) {

            throw new BusinessException(
                    "LOP_INVALID_TIME",
                    "Giờ bắt đầu phải trước giờ kết thúc",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (request.getNgayBatDau().isAfter(request.getNgayKetThuc())) {

            throw new BusinessException(
                    "LOP_INVALID_DATE",
                    "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

  
    private void kiemTraThuHoc(String thuHoc) {

        String[] danhSachThu = thuHoc.split(",");

        for (String thu : danhSachThu) {

            thu = thu.trim();

            if (!thu.equals("2")
                    && !thu.equals("3")
                    && !thu.equals("4")
                    && !thu.equals("5")
                    && !thu.equals("6")
                    && !thu.equals("7")
                    && !thu.equals("CN")) {

                throw new BusinessException(
                        "LOP_INVALID_DAY",
                        "Thứ học phải có dạng như '2,4,6' hoặc '7,CN'",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
    }

    

    private LopHocResponseDTO chuyenSangResponse(LopHoc lopHoc) {

        capNhatTrangThai(lopHoc);

        return new LopHocResponseDTO(
                lopHoc.getMaLop(),
                lopHoc.getPhongTap().getMaPhong(),
                lopHoc.getPt().getMaPt(),
                lopHoc.getSoNguoiDaDangKy(),
                lopHoc.getTenLop(),
                lopHoc.getDonGiaPt(),
                lopHoc.getThuHoc(),
                lopHoc.getGioBatDau(),
                lopHoc.getGioKetThuc(),
                lopHoc.getNgayBatDau(),
                lopHoc.getNgayKetThuc(),
                lopHoc.getTrangThai()
        );
    }

private void sinhBuoiHoc(LopHoc lopHoc) {

    LocalDate ngay = lopHoc.getNgayBatDau();

    while (!ngay.isAfter(lopHoc.getNgayKetThuc())) {

        String thu = chuyenNgaySangThu(ngay);

        if (lopHoc.getThuHoc().contains(thu)) {

            BuoiHoc buoiHoc = new BuoiHoc();

            buoiHoc.setLopHoc(lopHoc);
            buoiHoc.setNgayHoc(ngay);
            buoiHoc.setTrangThai("Sắp diễn ra");

            buoiHocRepository.save(buoiHoc);
        }

        ngay = ngay.plusDays(1);
    }
}


private String chuyenNgaySangThu(LocalDate ngay) {

    switch (ngay.getDayOfWeek()) {

        case MONDAY:
            return "2";

        case TUESDAY:
            return "3";

        case WEDNESDAY:
            return "4";

        case THURSDAY:
            return "5";

        case FRIDAY:
            return "6";

        case SATURDAY:
            return "7";

        case SUNDAY:
            return "CN";

        default:
            throw new IllegalStateException("Ngày không hợp lệ");
    }
}

public List<BuoiHocResponseDTO> layDanhSachBuoiHoc(Integer maLop) {

    if (!lopHocRepository.existsById(maLop)) {
        throw new BusinessException(
                "LOP_NOT_FOUND",
                "Không tìm thấy lớp học",
                HttpStatus.NOT_FOUND
        );
    }

    return buoiHocRepository.findByLopHoc_MaLop(maLop)
            .stream()
            .map(buoiHoc -> new BuoiHocResponseDTO(
                    buoiHoc.getMaBuoi(),
                    buoiHoc.getLopHoc().getMaLop(),
                    buoiHoc.getNgayHoc(),
                    buoiHoc.getTrangThai()
            ))
            .toList();
}

}