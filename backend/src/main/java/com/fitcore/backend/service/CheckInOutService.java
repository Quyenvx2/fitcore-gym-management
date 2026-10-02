package com.fitcore.backend.service;

import com.fitcore.backend.dto.CheckInOutResponseDTO;
import com.fitcore.backend.entity.CheckInOut;
import com.fitcore.backend.entity.HoiVien;
import com.fitcore.backend.exception.BusinessException;
import com.fitcore.backend.repository.CheckInOutRepository;
import com.fitcore.backend.repository.HoiVienRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckInOutService {

    private final CheckInOutRepository checkInOutRepository;
    private final HoiVienRepository hoiVienRepository;

    public CheckInOutService(
            CheckInOutRepository checkInOutRepository,
            HoiVienRepository hoiVienRepository
    ) {
        this.checkInOutRepository = checkInOutRepository;
        this.hoiVienRepository = hoiVienRepository;
    }

    @Transactional
    public CheckInOutResponseDTO checkIn(Integer maHv) {

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

        boolean dangTrongGym = checkInOutRepository
                .findFirstByHoiVien_MaHvAndThoiGianCheckOutIsNullOrderByThoiGianCheckInDesc(
                        maHv
                )
                .isPresent();

        if (dangTrongGym) {
            throw new BusinessException(
                    "ALREADY_CHECKED_IN",
                    "Hội viên đang ở trong phòng gym",
                    HttpStatus.CONFLICT
            );
        }

        CheckInOut checkInOut = new CheckInOut();

        checkInOut.setHoiVien(hoiVien);
        checkInOut.setThoiGianCheckIn(LocalDateTime.now());
        checkInOut.setThoiGianCheckOut(null);

        CheckInOut saved = checkInOutRepository.save(checkInOut);

        return chuyenSangResponseDTO(saved);
    }

    @Transactional
    public CheckInOutResponseDTO checkOut(Integer maHv) {

        CheckInOut checkInOut = checkInOutRepository
                .findFirstByHoiVien_MaHvAndThoiGianCheckOutIsNullOrderByThoiGianCheckInDesc(
                        maHv
                )
                .orElseThrow(() -> new BusinessException(
                        "NOT_CHECKED_IN",
                        "Hội viên hiện không có lượt check-in đang hoạt động",
                        HttpStatus.BAD_REQUEST
                ));

    
        checkInOut.setThoiGianCheckOut(LocalDateTime.now());

        CheckInOut saved = checkInOutRepository.save(checkInOut);

        return chuyenSangResponseDTO(saved);
    }

    public List<CheckInOutResponseDTO> layLichSuCuaHoiVien(
            Integer maHv
    ) {

        return checkInOutRepository
                .findByHoiVien_MaHvOrderByThoiGianCheckInDesc(maHv)
                .stream()
                .map(this::chuyenSangResponseDTO)
                .toList();
    }

    private CheckInOutResponseDTO chuyenSangResponseDTO(
            CheckInOut checkInOut
    ) {

        return new CheckInOutResponseDTO(
                checkInOut.getMaLuot(),
                checkInOut.getHoiVien().getMaHv(),
                checkInOut.getThoiGianCheckIn(),
                checkInOut.getThoiGianCheckOut()
        );
    }

    public List<CheckInOutResponseDTO> layTatCa() {

    return checkInOutRepository.findAll()
            .stream()
            .map(this::chuyenSangResponseDTO)
            .toList();
    }
}