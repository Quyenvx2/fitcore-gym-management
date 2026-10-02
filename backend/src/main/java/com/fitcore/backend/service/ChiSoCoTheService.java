package com.fitcore.backend.service;

import com.fitcore.backend.dto.ChiSoCoTheRequestDTO;
import com.fitcore.backend.dto.ChiSoCoTheResponseDTO;
import com.fitcore.backend.entity.ChiSoCoThe;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.ChiSoCoTheRepository;
import com.fitcore.backend.repository.HoiVienRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChiSoCoTheService {

    private final ChiSoCoTheRepository chiSoCoTheRepository;
    private final HoiVienRepository hoiVienRepository;

    public ChiSoCoTheService(
            ChiSoCoTheRepository chiSoCoTheRepository,
            HoiVienRepository hoiVienRepository
    ) {
        this.chiSoCoTheRepository = chiSoCoTheRepository;
        this.hoiVienRepository = hoiVienRepository;
    }

    @Transactional
    public ChiSoCoTheResponseDTO themChiSo(
            Integer maHv,
            ChiSoCoTheRequestDTO request
    ) {

        HoiVien hoiVien = hoiVienRepository.findById(maHv)
                .orElseThrow(() -> new BusinessException(
                        "MEMBER_NOT_FOUND",
                        "Không tìm thấy hội viên",
                        HttpStatus.NOT_FOUND
                ));

        if (!"Đang hoạt động".equals(hoiVien.getTrangThai())) {
            throw new BusinessException(
                    "MEMBER_INACTIVE",
                    "Hội viên không còn hoạt động",
                    HttpStatus.BAD_REQUEST
            );
        }

        ChiSoCoThe chiSo = new ChiSoCoThe();

        chiSo.setHoiVien(hoiVien);
        chiSo.setNgayDo(request.getNgayDo());
        chiSo.setCanNang(request.getCanNang());
        chiSo.setChieuCao(request.getChieuCao());
        chiSo.setPhanTramMo(request.getPhanTramMo());
        chiSo.setVongEo(request.getVongEo());
        chiSo.setGhiChu(request.getGhiChu());

        ChiSoCoThe saved = chiSoCoTheRepository.save(chiSo);

        return chuyenSangResponseDTO(saved);
    }

    public List<ChiSoCoTheResponseDTO> layLichSu(
            Integer maHv
    ) {

        hoiVienRepository.findById(maHv)
                .orElseThrow(() -> new BusinessException(
                        "MEMBER_NOT_FOUND",
                        "Không tìm thấy hội viên",
                        HttpStatus.NOT_FOUND
                ));

        return chiSoCoTheRepository
                .findByHoiVien_MaHvOrderByNgayDoDesc(maHv)
                .stream()
                .map(this::chuyenSangResponseDTO)
                .toList();
    }

    public ChiSoCoTheResponseDTO layMoiNhat(
            Integer maHv
    ) {

        List<ChiSoCoTheResponseDTO> danhSach = layLichSu(maHv);

        if (danhSach.isEmpty()) {
            throw new BusinessException(
                    "BODY_METRICS_NOT_FOUND",
                    "Hội viên chưa có dữ liệu chỉ số cơ thể",
                    HttpStatus.NOT_FOUND
            );
        }

        return danhSach.get(0);
    }

    private ChiSoCoTheResponseDTO chuyenSangResponseDTO(
            ChiSoCoThe chiSo
    ) {

        return new ChiSoCoTheResponseDTO(
                chiSo.getMaLanDo(),
                chiSo.getHoiVien().getMaHv(),
                chiSo.getNgayDo(),
                chiSo.getCanNang(),
                chiSo.getChieuCao(),
                chiSo.getPhanTramMo(),
                chiSo.getVongEo(),
                chiSo.getGhiChu()
        );
    }
}