package com.fitcore.backend.repository;
import com.fitcore.backend.entity.DangKyGoi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface DangKyGoiRepository extends JpaRepository<DangKyGoi, Integer> {
    List <DangKyGoi> findByHoiVien_MaHv(Integer maHv);
    List<DangKyGoi> findByGoiTap_MaGoi(Integer maGoi);
    List<DangKyGoi> findByHoiVien_MaHvAndTrangThaiIn(
        Integer maHv,
        List<String> trangThai);
}
