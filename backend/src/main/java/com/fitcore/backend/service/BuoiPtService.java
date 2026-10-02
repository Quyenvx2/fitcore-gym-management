package com.fitcore.backend.service;

import com.fitcore.backend.dto.BuoiPtRequestDTO;
import com.fitcore.backend.dto.BuoiPtResponseDTO;
import com.fitcore.backend.entity.BuoiPt;
import com.fitcore.backend.entity.CauHinhPt;
import com.fitcore.backend.entity.GoiTap;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.entity.Pt;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.BuoiHocRepository;
import com.fitcore.backend.repository.BuoiPtRepository;
import com.fitcore.backend.repository.CauHinhPtRepository;
import com.fitcore.backend.repository.DangKyGoiRepository;
import com.fitcore.backend.repository.HoiVienRepository;
import com.fitcore.backend.repository.PtRepository;
import com.fitcore.backend.repository.TaiKhoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class BuoiPtService {

    private final BuoiPtRepository buoiPtRepository;
    private final HoiVienRepository hoiVienRepository;
    private final PtRepository ptRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final DangKyGoiRepository dangKyGoiRepository;
    private final CauHinhPtRepository cauHinhPtRepository;
    private final BuoiHocRepository buoiHocRepository;

    public BuoiPtService(
            BuoiPtRepository buoiPtRepository,
            HoiVienRepository hoiVienRepository,
            PtRepository ptRepository,
            TaiKhoanRepository taiKhoanRepository,
            DangKyGoiRepository dangKyGoiRepository,
            CauHinhPtRepository cauHinhPtRepository,
            BuoiHocRepository buoiHocRepository
    ) {
        this.buoiPtRepository = buoiPtRepository;
        this.hoiVienRepository = hoiVienRepository;
        this.ptRepository = ptRepository;
        this.taiKhoanRepository = taiKhoanRepository;
        this.dangKyGoiRepository = dangKyGoiRepository;
        this.cauHinhPtRepository = cauHinhPtRepository;
        this.buoiHocRepository = buoiHocRepository;
    }

    @Transactional
    public BuoiPtResponseDTO datBuoiPt(
            BuoiPtRequestDTO request,
            String tenDangNhap
    ) {

        TaiKhoan taiKhoan = taiKhoanRepository
                .findByTenDangNhap(tenDangNhap)
                .orElseThrow(() -> new BusinessException(
                        "ACCOUNT_NOT_FOUND",
                        "Không tìm thấy tài khoản",
                        HttpStatus.NOT_FOUND
                ));

       
        if (!"HOI_VIEN".equals(taiKhoan.getVaiTro())) {
            throw new BusinessException(
                    "FORBIDDEN",
                    "Chỉ hội viên mới được đặt buổi PT",
                    HttpStatus.FORBIDDEN
            );
        }

        
        HoiVien hoiVien = hoiVienRepository
                .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
                .orElseThrow(() -> new BusinessException(
                        "MEMBER_NOT_FOUND",
                        "Không tìm thấy thông tin hội viên",
                        HttpStatus.NOT_FOUND
                ));

    
        if (!"Đang hoạt động".equals(hoiVien.getTrangThai())) {
            throw new BusinessException(
                    "MEMBER_INACTIVE",
                    "Hội viên không còn hoạt động",
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
                    "PT_NOT_AVAILABLE",
                    "PT hiện không làm việc",
                    HttpStatus.BAD_REQUEST
            );
        }

        LocalDateTime thoiGianBatDau = request.getThoiGianBatDau();

       
        boolean ptDangDayLop = buoiHocRepository.existsPtDayLopAtTime(
                pt.getMaPt(),
                thoiGianBatDau.toLocalDate(),
                thoiGianBatDau.toLocalTime()
        );

        if (ptDangDayLop) {
            throw new BusinessException(
                    "PT_BUSY_TEACHING_CLASS",
                    "PT đang dạy lớp vào thời điểm này",
                    HttpStatus.CONFLICT
            );
        }

       
        boolean ptDaCoLich = buoiPtRepository
                .findByPt_MaPtAndThoiGianBatDauBetween(
                        pt.getMaPt(),
                        thoiGianBatDau,
                        thoiGianBatDau
                )
                .stream()
                .anyMatch(buoi -> !"Bị hủy".equals(buoi.getTrangThai()));

        if (ptDaCoLich) {
            throw new BusinessException(
                    "PT_ALREADY_BOOKED",
                    "PT đã có lịch vào thời điểm này",
                    HttpStatus.CONFLICT
            );
        }

      
        LocalDate homNay = LocalDate.now();

        List<com.fitcore.backend.entity.DangKyGoi> cacGoiDangSuDung =
                dangKyGoiRepository.findByHoiVien_MaHvAndTrangThaiIn(
                        hoiVien.getMaHv(),
                        List.of("Đang sử dụng")
                )
                .stream()
                .filter(g ->
                        !g.getNgayBatDau().isAfter(homNay)
                                && !g.getNgayHetHan().isBefore(homNay)
                )
                .toList();

        if (cacGoiDangSuDung.isEmpty()) {
            throw new BusinessException(
                    "NO_ACTIVE_PACKAGE",
                    "Hội viên không có gói tập đang sử dụng",
                    HttpStatus.BAD_REQUEST
            );
        }

        
        com.fitcore.backend.entity.DangKyGoi dangKyGoi =
                cacGoiDangSuDung.stream()
                        .max(Comparator.comparing(
                                com.fitcore.backend.entity.DangKyGoi::getNgayHetHan
                        ))
                        .orElseThrow();

        GoiTap goiTap = dangKyGoi.getGoiTap();

       
        LocalDate ngayDat = thoiGianBatDau.toLocalDate();

        if (ngayDat.isBefore(dangKyGoi.getNgayBatDau())
                || ngayDat.isAfter(dangKyGoi.getNgayHetHan())) {

            throw new BusinessException(
                    "OUTSIDE_PACKAGE_PERIOD",
                    "Thời gian đặt nằm ngoài thời hạn gói tập",
                    HttpStatus.BAD_REQUEST
            );
        }

        
        Integer soBuoiPtDuocHuong = goiTap.getSoBuoiPt();

        if (soBuoiPtDuocHuong == null || soBuoiPtDuocHuong <= 0) {
            throw new BusinessException(
                    "NO_PT_SESSIONS",
                    "Gói tập không có buổi PT",
                    HttpStatus.BAD_REQUEST
            );
        }

       
        long soBuoiDaSuDung = buoiPtRepository.demSoBuoiDaSuDung(
                hoiVien.getMaHv(),
                dangKyGoi.getNgayBatDau().atStartOfDay(),
                dangKyGoi.getNgayHetHan().atTime(23, 59, 59)
        );

        if (soBuoiDaSuDung >= soBuoiPtDuocHuong) {
            throw new BusinessException(
                    "PT_SESSION_LIMIT_REACHED",
                    "Hội viên đã sử dụng hết số buổi PT của gói",
                    HttpStatus.BAD_REQUEST
            );
        }

       
        CauHinhPt cauHinh = cauHinhPtRepository
                .findByTrangThai("Đang áp dụng")
                .orElseThrow(() -> new BusinessException(
                        "PT_PRICE_NOT_FOUND",
                        "Chưa có đơn giá PT đang được áp dụng",
                        HttpStatus.NOT_FOUND
                ));

       
        BuoiPt buoiPt = new BuoiPt();

        buoiPt.setHoiVien(hoiVien);
        buoiPt.setPt(pt);
        buoiPt.setThoiGianBatDau(thoiGianBatDau);

        
        buoiPt.setDonGiaPt(cauHinh.getDonGiaPt());

        buoiPt.setTrangThai("Đã lên lịch");
        buoiPt.setThoiGianDat(LocalDateTime.now());
        buoiPt.setThoiGianHuy(null);

        BuoiPt saved = buoiPtRepository.save(buoiPt);

        return chuyenSangResponseDTO(saved);
    }

    @Transactional
    public BuoiPtResponseDTO huyBuoiPt(
        Integer maBuoiPt,
        String tenDangNhap
) {

   
    TaiKhoan taiKhoan = taiKhoanRepository
            .findByTenDangNhap(tenDangNhap)
            .orElseThrow(() -> new BusinessException(
                    "ACCOUNT_NOT_FOUND",
                    "Không tìm thấy tài khoản",
                    HttpStatus.NOT_FOUND
            ));

  
    if (!"HOI_VIEN".equals(taiKhoan.getVaiTro())) {
        throw new BusinessException(
                "FORBIDDEN",
                "Chỉ hội viên mới được hủy buổi PT",
                HttpStatus.FORBIDDEN
        );
    }

   
    HoiVien hoiVien = hoiVienRepository
            .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
            .orElseThrow(() -> new BusinessException(
                    "MEMBER_NOT_FOUND",
                    "Không tìm thấy thông tin hội viên",
                    HttpStatus.NOT_FOUND
            ));

    
    BuoiPt buoiPt = buoiPtRepository.findById(maBuoiPt)
            .orElseThrow(() -> new BusinessException(
                    "PT_SESSION_NOT_FOUND",
                    "Không tìm thấy buổi PT",
                    HttpStatus.NOT_FOUND
            ));

  
    if (!buoiPt.getHoiVien().getMaHv().equals(hoiVien.getMaHv())) {
        throw new BusinessException(
                "FORBIDDEN",
                "Bạn không có quyền hủy buổi PT này",
                HttpStatus.FORBIDDEN
        );
    }

   
    if (!"Đã lên lịch".equals(buoiPt.getTrangThai())) {
        throw new BusinessException(
                "INVALID_STATUS",
                "Chỉ có thể hủy buổi PT đang lên lịch",
                HttpStatus.BAD_REQUEST
        );
    }

   
    LocalDateTime now = LocalDateTime.now();

    if (!buoiPt.getThoiGianBatDau().isAfter(now)) {
        throw new BusinessException(
                "SESSION_ALREADY_STARTED",
                "Không thể hủy buổi PT đã bắt đầu",
                HttpStatus.BAD_REQUEST
        );
    }

   
    buoiPt.setTrangThai("Bị hủy");
    buoiPt.setThoiGianHuy(now);

    BuoiPt saved = buoiPtRepository.save(buoiPt);

    return chuyenSangResponseDTO(saved);
}

    public List<BuoiPtResponseDTO> layDanhSachCuaHoiVien(Integer maHv) {

    return buoiPtRepository
            .findByHoiVien_MaHv(maHv)
            .stream()
            .map(this::chuyenSangResponseDTO)
            .toList();
}

    private BuoiPtResponseDTO chuyenSangResponseDTO(BuoiPt buoiPt) {

        return new BuoiPtResponseDTO(
                buoiPt.getMaBuoiPt(),
                buoiPt.getHoiVien().getMaHv(),
                buoiPt.getPt().getMaPt(),
                buoiPt.getThoiGianBatDau(),
                buoiPt.getDonGiaPt(),
                buoiPt.getTrangThai(),
                buoiPt.getThoiGianDat(),
                buoiPt.getThoiGianHuy()
        );
    }

    @Transactional
    public BuoiPtResponseDTO capNhatTrangThai(
        Integer maBuoiPt,
        String trangThaiMoi,
        String tenDangNhap
) {
    // 1. Kiểm tra trạng thái hợp lệ
    List<String> trangThaiHopLe = List.of(
            "Đã lên lịch",
            "Đã hoàn thành",
            "Vắng mặt",
            "Bị hủy"
    );

    if (!trangThaiHopLe.contains(trangThaiMoi)) {
        throw new BusinessException(
                "INVALID_STATUS",
                "Trạng thái buổi PT không hợp lệ",
                HttpStatus.BAD_REQUEST
        );
    }

    // 2. Tìm tài khoản
    TaiKhoan taiKhoan = taiKhoanRepository
            .findByTenDangNhap(tenDangNhap)
            .orElseThrow(() -> new BusinessException(
                    "ACCOUNT_NOT_FOUND",
                    "Không tìm thấy tài khoản",
                    HttpStatus.NOT_FOUND
            ));

    // 3. Chỉ NHAN_VIEN hoặc PT được cập nhật
    String vaiTro = taiKhoan.getVaiTro();

    if (!"NHAN_VIEN".equals(vaiTro) && !"PT".equals(vaiTro)) {
        throw new BusinessException(
                "FORBIDDEN",
                "Bạn không có quyền cập nhật trạng thái buổi PT",
                HttpStatus.FORBIDDEN
        );
    }

    // 4. Tìm buổi PT
    BuoiPt buoiPt = buoiPtRepository.findById(maBuoiPt)
            .orElseThrow(() -> new BusinessException(
                    "PT_SESSION_NOT_FOUND",
                    "Không tìm thấy buổi PT",
                    HttpStatus.NOT_FOUND
            ));

    // 5. Nếu là PT → chỉ được sửa buổi PT của chính mình
    if ("PT".equals(vaiTro)) {

        Pt pt = ptRepository
                .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
                .orElseThrow(() -> new BusinessException(
                        "PT_NOT_FOUND",
                        "Tài khoản chưa được liên kết với PT",
                        HttpStatus.NOT_FOUND
                ));

        if (!buoiPt.getPt().getMaPt().equals(pt.getMaPt())) {
            throw new BusinessException(
                    "FORBIDDEN",
                    "Bạn không có quyền cập nhật buổi PT của PT khác",
                    HttpStatus.FORBIDDEN
            );
        }

        // PT chỉ xác nhận kết quả buổi tập
        if (!"Đã hoàn thành".equals(trangThaiMoi)
                && !"Vắng mặt".equals(trangThaiMoi)) {

            throw new BusinessException(
                    "INVALID_STATUS_FOR_PT",
                    "PT chỉ được cập nhật trạng thái Đã hoàn thành hoặc Vắng mặt",
                    HttpStatus.FORBIDDEN
            );
        }
    }

    // 6. Không cho sửa buổi đã hủy
    if ("Bị hủy".equals(buoiPt.getTrangThai())) {
        throw new BusinessException(
                "SESSION_ALREADY_CANCELLED",
                "Buổi PT đã bị hủy và không thể cập nhật trạng thái",
                HttpStatus.BAD_REQUEST
        );
    }

    // 7. Không cho thay đổi buổi đã kết thúc
    if ("Đã hoàn thành".equals(buoiPt.getTrangThai())
            || "Vắng mặt".equals(buoiPt.getTrangThai())) {

        throw new BusinessException(
                "SESSION_ALREADY_FINISHED",
                "Buổi PT đã có trạng thái kết thúc",
                HttpStatus.BAD_REQUEST
        );
    }

    // 8. Chỉ xử lý từ trạng thái Đã lên lịch
    if (!"Đã lên lịch".equals(buoiPt.getTrangThai())) {
        throw new BusinessException(
                "INVALID_STATUS_TRANSITION",
                "Chỉ có thể cập nhật buổi PT đang ở trạng thái Đã lên lịch",
                HttpStatus.BAD_REQUEST
        );
    }

    // 9. Cập nhật trạng thái
    buoiPt.setTrangThai(trangThaiMoi);

    // 10. Nếu bị hủy thì lưu thời gian hủy
    if ("Bị hủy".equals(trangThaiMoi)) {
        buoiPt.setThoiGianHuy(LocalDateTime.now());
    } else {
        buoiPt.setThoiGianHuy(null);
    }

    BuoiPt saved = buoiPtRepository.save(buoiPt);

    return chuyenSangResponseDTO(saved);
}

    public List<BuoiPtResponseDTO> layDanhSachTatCa() {

    return buoiPtRepository.findAll()
            .stream()
            .map(this::chuyenSangResponseDTO)
            .toList();
            }

    public List<BuoiPtResponseDTO> layDanhSachCuaPt(Integer maPt) {

    return buoiPtRepository.findByPt_MaPt(maPt)
            .stream()
            .map(this::chuyenSangResponseDTO)
            .toList();
    }
}