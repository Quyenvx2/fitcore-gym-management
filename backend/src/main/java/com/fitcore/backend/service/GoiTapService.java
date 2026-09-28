package com.fitcore.backend.service;

import com.fitcore.backend.dto.GoiTapRequestDTO;
import com.fitcore.backend.dto.GoiTapResponseDTO;
import com.fitcore.backend.entity.GoiTap;
import com.fitcore.backend.exception.BusinessException;

import com.fitcore.backend.repository.GoiTapRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
@Service 
public class GoiTapService {
    private final GoiTapRepository repository;

    public GoiTapService (GoiTapRepository repository){
        this.repository= repository;
    }

    private GoiTapResponseDTO chuyenSangResponseDTO(GoiTap goiTap) {
        return new GoiTapResponseDTO(
                goiTap.getMaGoi(),
                goiTap.getTenGoi(),
                goiTap.getThoiHanThang(),
                goiTap.getGiaTien(),
                goiTap.getSoBuoiPt(),
                goiTap.getTrangThai()
        );
    }
    public List<GoiTapResponseDTO> layDanhSachGoiTap(){
        return repository.findAll().stream()
                .map(this::chuyenSangResponseDTO)
                .toList();
    }

    public GoiTapResponseDTO layGoiTapTheoId(Integer id) {
        GoiTap goiTap = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(
                                "GOI_TAP_NOT_FOUND",
                                "Không tìm thấy gói tập với mã: " + id,
                                HttpStatus.NOT_FOUND
                        )
                );

        return chuyenSangResponseDTO(goiTap);
    }

    public GoiTapResponseDTO taoGoiTap(GoiTapRequestDTO request) {
        GoiTap goiTap = new GoiTap();
        goiTap.setTenGoi(request.getTenGoi());
        goiTap.setThoiHanThang(request.getThoiHanThang());
        goiTap.setGiaTien(request.getGiaTien());
        goiTap.setSoBuoiPt(request.getSoBuoiPt());
        goiTap.setTrangThai(request.getTrangThai());

        GoiTap savedGoiTap = repository.save(goiTap);

        return chuyenSangResponseDTO(savedGoiTap);
    }

    public GoiTapResponseDTO capNhatGoiTap(
            Integer id,
            GoiTapRequestDTO request) {

        GoiTap goiTap = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(
                                "GOI_TAP_NOT_FOUND",
                                "Không tìm thấy gói tập với mã: " + id,
                                HttpStatus.NOT_FOUND
                        )
                );

        goiTap.setTenGoi(request.getTenGoi());
        goiTap.setThoiHanThang(request.getThoiHanThang());
        goiTap.setGiaTien(request.getGiaTien());
        goiTap.setSoBuoiPt(request.getSoBuoiPt());
        goiTap.setTrangThai(request.getTrangThai());

        GoiTap savedGoiTap = repository.save(goiTap);

        return chuyenSangResponseDTO(savedGoiTap);
    }

    public void xoaGoiTap(Integer id) {

        if (!repository.existsById(id)) {
            throw new BusinessException(
                    "GOI_TAP_NOT_FOUND",
                    "Không tìm thấy gói tập với mã: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

        repository.deleteById(id);
    }

}
