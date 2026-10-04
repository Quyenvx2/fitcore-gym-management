package com.fitcore.backend.repository;

import com.fitcore.backend.entity.DangKyLopHoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DangKyLopHocRepository
        extends JpaRepository<DangKyLopHoc, Integer> {

    boolean existsByHoiVien_MaHvAndLopHoc_MaLopAndTrangThaiNot(
            Integer maHv,
            Integer maLop,
            String trangThai
    );

    Optional<DangKyLopHoc>
    findTopByHoiVien_MaHvAndLopHoc_MaLopOrderByMaDkLopDesc(
        Integer maHv,
        Integer maLop
    );
    List<DangKyLopHoc> findByLopHoc_MaLop(Integer maLop);

    List<DangKyLopHoc> findByHoiVien_MaHv(Integer maHv);
    List<DangKyLopHoc> findByHoiVien_MaHvAndTrangThai(
        Integer maHv,
        String trangThai
);
}