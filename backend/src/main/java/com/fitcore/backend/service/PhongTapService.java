package com.fitcore.backend.service;

import com.fitcore.backend.dto.PhongTapRequestDTO;
import com.fitcore.backend.dto.PhongTapResponseDTO;
import com.fitcore.backend.entity.PhongTap;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.PhongTapRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhongTapService {

    private final PhongTapRepository repository;

    public PhongTapService(PhongTapRepository repository) {
        this.repository = repository;
    }

   
    public List<PhongTapResponseDTO> layDanhSachPhong() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    
    public PhongTapResponseDTO layPhongTheoId(Integer id) {
        PhongTap phong = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "PHONG_NOT_FOUND",
                        "Không tìm thấy phòng tập",
                        HttpStatus.NOT_FOUND
                ));

        return toResponse(phong);
    }

    
    public PhongTapResponseDTO taoPhong(PhongTapRequestDTO request) {

        kiemTraTrangThai(request.getTrangThai());

        if (repository.findByTenPhong(request.getTenPhong()).isPresent()) {
            throw new BusinessException(
                    "PHONG_NAME_EXISTS",
                    "Tên phòng tập đã tồn tại",
                    HttpStatus.CONFLICT
            );
        }

        PhongTap phong = new PhongTap();

        phong.setTenPhong(request.getTenPhong());
        phong.setViTri(request.getViTri());
        phong.setSucChua(request.getSucChua());
        phong.setTrangThai(request.getTrangThai());

        return toResponse(repository.save(phong));
    }

    
    public PhongTapResponseDTO capNhatPhong(
            Integer id,
            PhongTapRequestDTO request) {

        kiemTraTrangThai(request.getTrangThai());

        PhongTap phong = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "PHONG_NOT_FOUND",
                        "Không tìm thấy phòng tập",
                        HttpStatus.NOT_FOUND
                ));

       
        repository.findByTenPhong(request.getTenPhong())
                .ifPresent(phongTrung -> {
                    if (!phongTrung.getMaPhong().equals(id)) {
                        throw new BusinessException(
                                "PHONG_NAME_EXISTS",
                                "Tên phòng tập đã tồn tại",
                                HttpStatus.CONFLICT
                        );
                    }
                });

        phong.setTenPhong(request.getTenPhong());
        phong.setViTri(request.getViTri());
        phong.setSucChua(request.getSucChua());
        phong.setTrangThai(request.getTrangThai());

        return toResponse(repository.save(phong));
    }

    
    public void xoaPhong(Integer id) {

        PhongTap phong = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "PHONG_NOT_FOUND",
                        "Không tìm thấy phòng tập",
                        HttpStatus.NOT_FOUND
                ));

        repository.delete(phong);
    }

    
    private void kiemTraTrangThai(String trangThai) {

        if (!"Đang sử dụng".equals(trangThai)
                && !"Bảo trì".equals(trangThai)) {

            throw new BusinessException(
                    "PHONG_INVALID_STATUS",
                    "Trạng thái phòng chỉ được là 'Đang sử dụng' hoặc 'Bảo trì'",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    
    private PhongTapResponseDTO toResponse(PhongTap phong) {

        return new PhongTapResponseDTO(
                phong.getMaPhong(),
                phong.getTenPhong(),
                phong.getViTri(),
                phong.getSucChua(),
                phong.getTrangThai()
        );
    }
}