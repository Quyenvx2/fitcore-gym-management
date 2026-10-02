package com.fitcore.backend.service;

import com.fitcore.backend.dto.CauHinhPtRequestDTO;
import com.fitcore.backend.dto.CauHinhPtResponseDTO;
import com.fitcore.backend.entity.CauHinhPt;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.CauHinhPtRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauHinhPtService {

    private final CauHinhPtRepository cauHinhPtRepository;

    public CauHinhPtService(CauHinhPtRepository cauHinhPtRepository) {
        this.cauHinhPtRepository = cauHinhPtRepository;
    }

   

    public CauHinhPtResponseDTO layGiaPtHienTai() {

        CauHinhPt cauHinh = cauHinhPtRepository
                .findByTrangThai("Đang áp dụng")
                .orElseThrow(() -> new BusinessException(
                        "PT_PRICE_NOT_FOUND",
                        "Chưa có đơn giá PT đang được áp dụng",
                        HttpStatus.NOT_FOUND
                ));

        return chuyenSangResponseDTO(cauHinh);
    }


  

    @Transactional
    public CauHinhPtResponseDTO capNhatGiaPt(
            CauHinhPtRequestDTO request
    ) {

        CauHinhPt cauHinhCu = cauHinhPtRepository
                .findByTrangThai("Đang áp dụng")
                .orElse(null);

   
        if (cauHinhCu != null) {
            cauHinhCu.setTrangThai("Ngừng áp dụng");
            cauHinhPtRepository.save(cauHinhCu);
        }

        CauHinhPt cauHinhMoi = new CauHinhPt();

        cauHinhMoi.setDonGiaPt(request.getDonGiaPt());
        cauHinhMoi.setTrangThai("Đang áp dụng");

        CauHinhPt saved = cauHinhPtRepository.save(cauHinhMoi);

        return chuyenSangResponseDTO(saved);
    }



    private CauHinhPtResponseDTO chuyenSangResponseDTO(
            CauHinhPt cauHinh
    ) {

        return new CauHinhPtResponseDTO(
                cauHinh.getMaCauHinh(),
                cauHinh.getDonGiaPt(),
                cauHinh.getTrangThai()
        );
    }
}