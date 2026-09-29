package com.fitcore.backend.service;

import com.fitcore.backend.dto.PtRequestDTO;
import com.fitcore.backend.dto.PtResponseDTO;
import com.fitcore.backend.entity.Pt;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.PtRepository;
import com.fitcore.backend.repository.TaiKhoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fitcore.backend.dto.CapNhatHoSoPtRequestDTO;
import java.util.List;

@Service
public class PtService {

    private final PtRepository ptRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    public PtService(
            PtRepository ptRepository,
            TaiKhoanRepository taiKhoanRepository) {

        this.ptRepository = ptRepository;
        this.taiKhoanRepository = taiKhoanRepository;
    }

    public List<PtResponseDTO> layDanhSachPt() {
        return ptRepository.findAll()
                .stream()
                .map(this::chuyenSangResponseDTO)
                .toList();
    }

    public PtResponseDTO layPtTheoId(Integer id) {

        Pt pt = ptRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "PT_NOT_FOUND",
                        "Không tìm thấy PT",
                        HttpStatus.NOT_FOUND
                ));

        return chuyenSangResponseDTO(pt);
    }

    public PtResponseDTO layPtCuaToi(String tenDangNhap) {

        TaiKhoan taiKhoan = taiKhoanRepository
                .findByTenDangNhap(tenDangNhap)
                .orElseThrow(() -> new BusinessException(
                        "TAI_KHOAN_NOT_FOUND",
                        "Không tìm thấy tài khoản",
                        HttpStatus.NOT_FOUND
                ));

        Pt pt = ptRepository
                .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
                .orElseThrow(() -> new BusinessException(
                        "PT_NOT_FOUND",
                        "Không tìm thấy hồ sơ PT",
                        HttpStatus.NOT_FOUND
                ));

        return chuyenSangResponseDTO(pt);
    }

    public PtResponseDTO taoPt(PtRequestDTO request) {

        TaiKhoan taiKhoan = taiKhoanRepository
                .findById(request.getMaTk())
                .orElseThrow(() -> new BusinessException(
                        "TAI_KHOAN_NOT_FOUND",
                        "Không tìm thấy tài khoản",
                        HttpStatus.NOT_FOUND
                ));

        if (!"PT".equals(taiKhoan.getVaiTro())) {
            throw new BusinessException(
                    "TAI_KHOAN_INVALID_ROLE",
                    "Tài khoản không có vai trò PT",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (ptRepository
                .findByTaiKhoan_MaTk(request.getMaTk())
                .isPresent()) {

            throw new BusinessException(
                    "PT_ACCOUNT_ALREADY_LINKED",
                    "Tài khoản đã được liên kết với một PT",
                    HttpStatus.CONFLICT
            );
        }

        if (ptRepository
                .findByCccd(request.getCccd())
                .isPresent()) {

            throw new BusinessException(
                    "PT_CCCD_EXISTS",
                    "CCCD đã tồn tại",
                    HttpStatus.CONFLICT
            );
        }

        if (ptRepository
                .findBySdt(request.getSdt())
                .isPresent()) {

            throw new BusinessException(
                    "PT_SDT_EXISTS",
                    "Số điện thoại đã tồn tại",
                    HttpStatus.CONFLICT
            );
        }

        Pt pt = new Pt();

        pt.setTaiKhoan(taiKhoan);
        pt.setCccd(request.getCccd());
        pt.setHoTen(request.getHoTen());
        pt.setNgaySinh(request.getNgaySinh());
        pt.setSdt(request.getSdt());
        pt.setChuyenMon(request.getChuyenMon());
        pt.setSoNamKinhNghiem(request.getSoNamKinhNghiem());
        pt.setLuongCoBan(request.getLuongCoBan());
        pt.setTrangThai(request.getTrangThai());

        Pt saved = ptRepository.save(pt);

        return chuyenSangResponseDTO(saved);
    }

    public PtResponseDTO capNhatPt(
            Integer id,
            PtRequestDTO request) {

        Pt pt = ptRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "PT_NOT_FOUND",
                        "Không tìm thấy PT",
                        HttpStatus.NOT_FOUND
                ));

        TaiKhoan taiKhoan = taiKhoanRepository
                .findById(request.getMaTk())
                .orElseThrow(() -> new BusinessException(
                        "TAI_KHOAN_NOT_FOUND",
                        "Không tìm thấy tài khoản",
                        HttpStatus.NOT_FOUND
                ));

        if (!"PT".equals(taiKhoan.getVaiTro())) {
            throw new BusinessException(
                    "TAI_KHOAN_INVALID_ROLE",
                    "Tài khoản không có vai trò PT",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (!pt.getTaiKhoan().getMaTk().equals(request.getMaTk())
                && ptRepository
                .findByTaiKhoan_MaTk(request.getMaTk())
                .isPresent()) {

            throw new BusinessException(
                    "PT_ACCOUNT_ALREADY_LINKED",
                    "Tài khoản đã được liên kết với một PT",
                    HttpStatus.CONFLICT
            );
        }

        if (!pt.getCccd().equals(request.getCccd())
                && ptRepository
                .findByCccd(request.getCccd())
                .isPresent()) {

            throw new BusinessException(
                    "PT_CCCD_EXISTS",
                    "CCCD đã tồn tại",
                    HttpStatus.CONFLICT
            );
        }

        if (!pt.getSdt().equals(request.getSdt())
                && ptRepository
                .findBySdt(request.getSdt())
                .isPresent()) {

            throw new BusinessException(
                    "PT_SDT_EXISTS",
                    "Số điện thoại đã tồn tại",
                    HttpStatus.CONFLICT
            );
        }

        pt.setTaiKhoan(taiKhoan);
        pt.setCccd(request.getCccd());
        pt.setHoTen(request.getHoTen());
        pt.setNgaySinh(request.getNgaySinh());
        pt.setSdt(request.getSdt());
        pt.setChuyenMon(request.getChuyenMon());
        pt.setSoNamKinhNghiem(request.getSoNamKinhNghiem());
        pt.setLuongCoBan(request.getLuongCoBan());
        pt.setTrangThai(request.getTrangThai());

        Pt updated = ptRepository.save(pt);

        return chuyenSangResponseDTO(updated);
    }

    public PtResponseDTO capNhatHoSoCuaToi(
        String tenDangNhap,
        CapNhatHoSoPtRequestDTO request) {

    TaiKhoan taiKhoan = taiKhoanRepository
            .findByTenDangNhap(tenDangNhap)
            .orElseThrow(() -> new BusinessException(
                    "TAI_KHOAN_NOT_FOUND",
                    "Không tìm thấy tài khoản",
                    HttpStatus.NOT_FOUND
            ));

    Pt pt = ptRepository
            .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
            .orElseThrow(() -> new BusinessException(
                    "PT_NOT_FOUND",
                    "Không tìm thấy hồ sơ PT",
                    HttpStatus.NOT_FOUND
            ));

    if (!pt.getCccd().equals(request.getCccd())
            && ptRepository
            .findByCccd(request.getCccd())
            .isPresent()) {

        throw new BusinessException(
                "PT_CCCD_EXISTS",
                "CCCD đã tồn tại",
                HttpStatus.CONFLICT
        );
    }

    if (!pt.getSdt().equals(request.getSdt())
            && ptRepository
            .findBySdt(request.getSdt())
            .isPresent()) {

        throw new BusinessException(
                "PT_SDT_EXISTS",
                "Số điện thoại đã tồn tại",
                HttpStatus.CONFLICT
        );
    }

    pt.setCccd(request.getCccd());
    pt.setHoTen(request.getHoTen());
    pt.setNgaySinh(request.getNgaySinh());
    pt.setSdt(request.getSdt());
    pt.setChuyenMon(request.getChuyenMon());
    pt.setSoNamKinhNghiem(request.getSoNamKinhNghiem());

    Pt updated = ptRepository.save(pt);

    return chuyenSangResponseDTO(updated);
}

    public void xoaPt(Integer id) {

        Pt pt = ptRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "PT_NOT_FOUND",
                        "Không tìm thấy PT",
                        HttpStatus.NOT_FOUND
                ));

        ptRepository.delete(pt);
    }

    private PtResponseDTO chuyenSangResponseDTO(Pt pt) {

        return new PtResponseDTO(
                pt.getMaPt(),
                pt.getTaiKhoan().getMaTk(),
                pt.getCccd(),
                pt.getHoTen(),
                pt.getNgaySinh(),
                pt.getSdt(),
                pt.getChuyenMon(),
                pt.getSoNamKinhNghiem(),
                pt.getLuongCoBan(),
                pt.getTrangThai()
        );
    }
}