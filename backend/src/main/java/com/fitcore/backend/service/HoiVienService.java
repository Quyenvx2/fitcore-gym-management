package com.fitcore.backend.service;

import com.fitcore.backend.dto.HoiVienRequestDTO;
import com.fitcore.backend.dto.HoiVienResponseDTO;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.HoiVienRepository;
import com.fitcore.backend.repository.TaiKhoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fitcore.backend.dto.HoanThienHoiVienRequestDTO;

import java.util.List;

@Service
public class HoiVienService {

    private final HoiVienRepository hoiVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    public HoiVienService(
            HoiVienRepository hoiVienRepository,
            TaiKhoanRepository taiKhoanRepository) {

        this.hoiVienRepository = hoiVienRepository;
        this.taiKhoanRepository = taiKhoanRepository;
    }



    public List<HoiVienResponseDTO> layDanhSachHoiVien() {

        return hoiVienRepository.findAll()
                .stream()
                .map(this::chuyenSangResponseDTO)
                .toList();
    }



    public HoiVienResponseDTO layHoiVienTheoId(Integer id) {

        HoiVien hoiVien = hoiVienRepository
                .findById(id)
                .orElseThrow(() ->
                        new BusinessException(
                                "HOI_VIEN_NOT_FOUND",
                                "Không tìm thấy hội viên với mã: " + id,
                                HttpStatus.NOT_FOUND
                        )
                );

        return chuyenSangResponseDTO(hoiVien);
    }



    public HoiVienResponseDTO taoHoiVien(
            HoiVienRequestDTO request) {

        if (hoiVienRepository
                .findByCccd(request.getCccd())
                .isPresent()) {

            throw new BusinessException(
                    "HOI_VIEN_CCCD_EXISTS",
                    "CCCD đã tồn tại",
                    HttpStatus.BAD_REQUEST
            );
        }


        
        if (hoiVienRepository
                .findBySdt(request.getSdt())
                .isPresent()) {

            throw new BusinessException(
                    "HOI_VIEN_SDT_EXISTS",
                    "Số điện thoại đã tồn tại",
                    HttpStatus.BAD_REQUEST
            );
        }


        TaiKhoan taiKhoan = taiKhoanRepository
                .findById(request.getMaTk())
                .orElseThrow(() ->
                        new BusinessException(
                                "TAI_KHOAN_NOT_FOUND",
                                "Không tìm thấy tài khoản với mã: "
                                        + request.getMaTk(),
                                HttpStatus.NOT_FOUND
                        )
                );


       
        if (!"HOI_VIEN".equals(taiKhoan.getVaiTro())) {

            throw new BusinessException(
                    "TAI_KHOAN_INVALID_ROLE",
                    "Tài khoản được chọn không có vai trò HOI_VIEN",
                    HttpStatus.BAD_REQUEST
            );
        }


        
        if (hoiVienRepository
                .findByTaiKhoan_MaTk(request.getMaTk())
                .isPresent()) {

            throw new BusinessException(
                    "TAI_KHOAN_ALREADY_LINKED",
                    "Tài khoản này đã được gắn cho một hội viên",
                    HttpStatus.BAD_REQUEST
            );
        }


    
        HoiVien hoiVien = new HoiVien();

        hoiVien.setTaiKhoan(taiKhoan);
        hoiVien.setCccd(request.getCccd());
        hoiVien.setHoTen(request.getHoTen());
        hoiVien.setNgaySinh(request.getNgaySinh());
        hoiVien.setGioiTinh(request.getGioiTinh());
        hoiVien.setDiaChi(request.getDiaChi());
        hoiVien.setSdt(request.getSdt());
        hoiVien.setTrangThai(request.getTrangThai());


       
        HoiVien savedHoiVien =
                hoiVienRepository.save(hoiVien);

        return chuyenSangResponseDTO(savedHoiVien);
    }


 

    public HoiVienResponseDTO capNhatHoiVien(
            Integer id,
            HoiVienRequestDTO request) {

        
        HoiVien hoiVien = hoiVienRepository
                .findById(id)
                .orElseThrow(() ->
                        new BusinessException(
                                "HOI_VIEN_NOT_FOUND",
                                "Không tìm thấy hội viên với mã: " + id,
                                HttpStatus.NOT_FOUND
                        )
                );


      
        hoiVienRepository
                .findByCccd(request.getCccd())
                .ifPresent(existing -> {

                    if (!existing.getMaHv().equals(id)) {

                        throw new BusinessException(
                                "HOI_VIEN_CCCD_EXISTS",
                                "CCCD đã tồn tại",
                                HttpStatus.BAD_REQUEST
                        );
                    }
                });


       
        hoiVienRepository
                .findBySdt(request.getSdt())
                .ifPresent(existing -> {

                    if (!existing.getMaHv().equals(id)) {

                        throw new BusinessException(
                                "HOI_VIEN_SDT_EXISTS",
                                "Số điện thoại đã tồn tại",
                                HttpStatus.BAD_REQUEST
                        );
                    }
                });


      
        TaiKhoan taiKhoan = taiKhoanRepository
                .findById(request.getMaTk())
                .orElseThrow(() ->
                        new BusinessException(
                                "TAI_KHOAN_NOT_FOUND",
                                "Không tìm thấy tài khoản với mã: "
                                        + request.getMaTk(),
                                HttpStatus.NOT_FOUND
                        )
                );


   
        if (!"HOI_VIEN".equals(taiKhoan.getVaiTro())) {

            throw new BusinessException(
                    "TAI_KHOAN_INVALID_ROLE",
                    "Tài khoản được chọn không có vai trò HOI_VIEN",
                    HttpStatus.BAD_REQUEST
            );
        }



        hoiVienRepository
                .findByTaiKhoan_MaTk(request.getMaTk())
                .ifPresent(existing -> {

                    if (!existing.getMaHv().equals(id)) {

                        throw new BusinessException(
                                "TAI_KHOAN_ALREADY_LINKED",
                                "Tài khoản này đã được gắn cho hội viên khác",
                                HttpStatus.BAD_REQUEST
                        );
                    }
                });


        
        hoiVien.setTaiKhoan(taiKhoan);
        hoiVien.setCccd(request.getCccd());
        hoiVien.setHoTen(request.getHoTen());
        hoiVien.setNgaySinh(request.getNgaySinh());
        hoiVien.setGioiTinh(request.getGioiTinh());
        hoiVien.setDiaChi(request.getDiaChi());
        hoiVien.setSdt(request.getSdt());
        hoiVien.setTrangThai(request.getTrangThai());


      
        HoiVien savedHoiVien =
                hoiVienRepository.save(hoiVien);

        return chuyenSangResponseDTO(savedHoiVien);
    }


    
    public void xoaHoiVien(Integer id) {

        if (!hoiVienRepository.existsById(id)) {

            throw new BusinessException(
                    "HOI_VIEN_NOT_FOUND",
                    "Không tìm thấy hội viên với mã: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

        hoiVienRepository.deleteById(id);
    }


  

    private HoiVienResponseDTO chuyenSangResponseDTO(
            HoiVien hoiVien) {

        Integer maTk = null;

        if (hoiVien.getTaiKhoan() != null) {
            maTk = hoiVien.getTaiKhoan().getMaTk();
        }

        return new HoiVienResponseDTO(
                hoiVien.getMaHv(),
                maTk,
                hoiVien.getCccd(),
                hoiVien.getHoTen(),
                hoiVien.getNgaySinh(),
                hoiVien.getGioiTinh(),
                hoiVien.getDiaChi(),
                hoiVien.getSdt(),
                hoiVien.getTrangThai()
        );
    }
    public HoiVienResponseDTO hoanThienHoSo(
        HoanThienHoiVienRequestDTO request,
        String tenDangNhap) {

    TaiKhoan taiKhoan = taiKhoanRepository
            .findByTenDangNhap(tenDangNhap)
            .orElseThrow(() -> new BusinessException(
                    "TAI_KHOAN_NOT_FOUND",
                    "Không tìm thấy tài khoản",
                    HttpStatus.NOT_FOUND
            ));

    if (!"HOI_VIEN".equals(taiKhoan.getVaiTro())) {
        throw new BusinessException(
                "AUTH_INVALID_ROLE",
                "Tài khoản không phải hội viên",
                HttpStatus.FORBIDDEN
        );
    }

    if (hoiVienRepository
            .findByTaiKhoan_MaTk(taiKhoan.getMaTk())
            .isPresent()) {

        throw new BusinessException(
                "HOI_VIEN_PROFILE_EXISTS",
                "Tài khoản đã có hồ sơ hội viên",
                HttpStatus.CONFLICT
        );
    }

    if (hoiVienRepository
            .findByCccd(request.getCccd())
            .isPresent()) {

        throw new BusinessException(
                "HOI_VIEN_CCCD_EXISTS",
                "CCCD đã tồn tại",
                HttpStatus.CONFLICT
        );
    }

    if (hoiVienRepository
            .findBySdt(request.getSdt())
            .isPresent()) {

        throw new BusinessException(
                "HOI_VIEN_SDT_EXISTS",
                "Số điện thoại đã tồn tại",
                HttpStatus.CONFLICT
        );
    }

    HoiVien hoiVien = new HoiVien();

    hoiVien.setTaiKhoan(taiKhoan);
    hoiVien.setCccd(request.getCccd());
    hoiVien.setHoTen(request.getHoTen());
    hoiVien.setNgaySinh(request.getNgaySinh());
    hoiVien.setGioiTinh(request.getGioiTinh());
    hoiVien.setDiaChi(request.getDiaChi());
    hoiVien.setSdt(request.getSdt());
    hoiVien.setTrangThai("Đang hoạt động");

    HoiVien saved = hoiVienRepository.save(hoiVien);

    return chuyenSangResponseDTO(saved);
}
}