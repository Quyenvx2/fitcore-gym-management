package com.fitcore.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
    name = "BUOI_HOC",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ma_lop", "ngay_hoc"})
    }
)
public class BuoiHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_buoi")
    private Integer maBuoi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_lop", nullable = false)
    private LopHoc lopHoc;

    @Column(name = "ngay_hoc", nullable = false)
    private LocalDate ngayHoc;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    public BuoiHoc() {
    }

    public Integer getMaBuoi() {
        return maBuoi;
    }

    public void setMaBuoi(Integer maBuoi) {
        this.maBuoi = maBuoi;
    }

    public LopHoc getLopHoc() {
        return lopHoc;
    }

    public void setLopHoc(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }

    public LocalDate getNgayHoc() {
        return ngayHoc;
    }

    public void setNgayHoc(LocalDate ngayHoc) {
        this.ngayHoc = ngayHoc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}