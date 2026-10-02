CREATE DATABASE IF NOT EXISTS gym_management
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE gym_management;
CREATE TABLE `TAI_KHOAN`(
    `ma_tk` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ten_dang_nhap` VARCHAR(255) NOT NULL,
    `mat_khau` VARCHAR(255) NOT NULL,
    `vai_tro` VARCHAR(50) NOT NULL,
    `trang_thai` ENUM('Active', 'Blocked') NOT NULL,
    UNIQUE `tai_khoan_ten_dang_nhap_unique`(`ten_dang_nhap`)
);

CREATE TABLE `HOI_VIEN`(
    `ma_hv` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_tk` INT UNSIGNED NOT NULL,
    `cccd` VARCHAR(15) NOT NULL,
    `ho_ten` VARCHAR(255) NOT NULL,
    `ngay_sinh` DATE NOT NULL,
    `gioi_tinh` VARCHAR(20) NOT NULL,
    `dia_chi` VARCHAR(255) NOT NULL,
    `sdt` VARCHAR(15) NOT NULL,
    `trang_thai` ENUM('Đang hoạt động', 'Đã ngừng tham gia') NOT NULL,
    FOREIGN KEY (`ma_tk`) REFERENCES `TAI_KHOAN`(`ma_tk`),
    UNIQUE `hoi_vien_ma_tk_unique`(`ma_tk`),
    UNIQUE `hoi_vien_cccd_unique`(`cccd`),
    UNIQUE `hoi_vien_sdt_unique`(`sdt`)
);

CREATE TABLE `NHAN_VIEN`(
    `ma_nv` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_tk` INT UNSIGNED NOT NULL,
    `cccd` VARCHAR(15) NOT NULL,
    `ho_ten` VARCHAR(255) NOT NULL,
    `ngay_sinh` DATE NOT NULL,
    `sdt` VARCHAR(15) NOT NULL,
    `luong_co_ban` DECIMAL(12, 2) NOT NULL,
    `trang_thai` ENUM('Đang làm việc', 'Nghỉ phép', 'Đã nghỉ việc') NOT NULL,
    `ngay_vao_lam` DATE NOT NULL,
    FOREIGN KEY (`ma_tk`) REFERENCES `TAI_KHOAN`(`ma_tk`),
    UNIQUE `nhan_vien_ma_tk_unique`(`ma_tk`),
    UNIQUE `nhan_vien_cccd_unique`(`cccd`),
    UNIQUE `nhan_vien_sdt_unique`(`sdt`)
);

CREATE TABLE `PT`(
    `ma_pt` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_tk` INT UNSIGNED NOT NULL,
    `cccd` VARCHAR(15) NOT NULL,
    `ho_ten` VARCHAR(255) NOT NULL,
    `ngay_sinh` DATE NOT NULL,
    `sdt` VARCHAR(15) NOT NULL,
    `chuyen_mon` VARCHAR(255) NOT NULL,
    `so_nam_kinh_nghiem` INT NOT NULL,
    `luong_co_ban` DECIMAL(12, 2) NOT NULL,
    `trang_thai` ENUM('Đang làm việc', 'Nghỉ phép', 'Đã nghỉ việc') NOT NULL,
    FOREIGN KEY (`ma_tk`) REFERENCES `TAI_KHOAN`(`ma_tk`),
    UNIQUE `pt_ma_tk_unique`(`ma_tk`),
    UNIQUE `pt_cccd_unique`(`cccd`),
    UNIQUE `pt_sdt_unique`(`sdt`)
);

CREATE TABLE `PHONG_TAP`(
    `ma_phong` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ten_phong` VARCHAR(255) NOT NULL,
    `vi_tri` VARCHAR(255) NOT NULL,
    `suc_chua` INT NOT NULL,
    `trang_thai` ENUM('Đang sử dụng', 'Bảo trì') NOT NULL,
    UNIQUE `phong_tap_ten_phong_unique`(`ten_phong`)
);

CREATE TABLE `LOP_HOC`(
    `ma_lop` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_phong` INT UNSIGNED NOT NULL,
    `ma_pt` INT UNSIGNED NOT NULL,
    `so_nguoi_da_dang_ky` INT NOT NULL DEFAULT 0,
    `ten_lop` VARCHAR(255) NOT NULL,
    `don_gia_pt` DECIMAL(12, 2) NOT NULL,
    `thu_hoc` VARCHAR(255) NOT NULL,
    `gio_bat_dau` TIME NOT NULL,
    `gio_ket_thuc` TIME NOT NULL,
    `ngay_bat_dau` DATE NOT NULL,
    `ngay_ket_thuc` DATE NOT NULL,
    `trang_thai` ENUM('Lớp đang mở', 'Đã đủ người', 'Đã đóng') NOT NULL,
    FOREIGN KEY(`ma_phong`) REFERENCES `PHONG_TAP`(`ma_phong`),
    FOREIGN KEY(`ma_pt`) REFERENCES `PT`(`ma_pt`)
);

CREATE TABLE `GOI_TAP`(
    `ma_goi` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ten_goi` VARCHAR(255) NOT NULL,
    `thoi_han_thang` INT NOT NULL,
    `gia_tien` DECIMAL(12, 2) NOT NULL,
    `so_buoi_pt` INT NOT NULL,
    `trang_thai` ENUM('Active', 'Inactive') NOT NULL
);

CREATE TABLE `DANG_KY_GOI`(
    `ma_dk_goi` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_hv` INT UNSIGNED NOT NULL,
    `ma_goi` INT UNSIGNED NOT NULL,
    `ngay_bat_dau` DATE NOT NULL,
    `ngay_het_han` DATE NOT NULL,
    `ngay_dang_ky` DATE NOT NULL,
    `trang_thai` ENUM(
        'Đang chờ kích hoạt',
        'Đang sử dụng',
        'Đã hết hạn',
        'Đã hủy'
    ) NOT NULL,
    FOREIGN KEY(`ma_goi`) REFERENCES `GOI_TAP`(`ma_goi`),
    FOREIGN KEY(`ma_hv`) REFERENCES `HOI_VIEN`(`ma_hv`)
);

CREATE TABLE `DANG_KY_LOP_HOC`(
    `ma_dk_lop` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_hv` INT UNSIGNED NOT NULL,
    `ma_lop` INT UNSIGNED NOT NULL,
    `thoi_gian_dang_ky` DATETIME NOT NULL,
    `ngay_het_han` DATE NOT NULL,
    `ngay_huy` DATETIME,
    `trang_thai` ENUM('Đăng kí thành công', 'Chờ duyệt', 'Đã bị hủy') NOT NULL,
    FOREIGN KEY(`ma_lop`) REFERENCES `LOP_HOC`(`ma_lop`),
    FOREIGN KEY(`ma_hv`) REFERENCES `HOI_VIEN`(`ma_hv`)
);


CREATE TABLE `CHI_SO_CO_THE`(
    `ma_lan_do` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_hv` INT UNSIGNED NOT NULL,
    `ngay_do` DATE NOT NULL,
    `can_nang` DECIMAL(8, 2) NOT NULL,
    `chieu_cao` DECIMAL(8, 2) NOT NULL,
    `phan_tram_mo` DECIMAL(8, 2) NOT NULL,
    `vong_eo` DECIMAL(8, 2) NOT NULL,
    `ghi_chu` VARCHAR(255),
    FOREIGN KEY(`ma_hv`) REFERENCES `HOI_VIEN`(`ma_hv`)
);

CREATE TABLE `BUOI_PT`(
    `ma_buoi_pt` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_hv` INT UNSIGNED NOT NULL,
    `ma_pt` INT UNSIGNED NOT NULL,
    `thoi_gian_bat_dau` DATETIME NOT NULL,
    `don_gia_pt` DECIMAL(12, 2) NOT NULL,
    `trang_thai` ENUM('Đã lên lịch', 'Đã hoàn thành', 'Vắng mặt', 'Bị hủy') NOT NULL,
    `thoi_gian_dat` DATETIME NOT NULL,
    `thoi_gian_huy` DATETIME,
    FOREIGN KEY(`ma_hv`) REFERENCES `HOI_VIEN`(`ma_hv`),
    FOREIGN KEY(`ma_pt`) REFERENCES `PT`(`ma_pt`)
);

CREATE TABLE `CHECK_IN_OUT`(
    `ma_luot` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_hv` INT UNSIGNED NOT NULL,
    `thoi_gian_check_in` DATETIME NOT NULL,
    `thoi_gian_check_out` DATETIME,
    FOREIGN KEY(`ma_hv`) REFERENCES `HOI_VIEN`(`ma_hv`)
);

CREATE TABLE `BUOI_HOC`(
    `ma_buoi` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ma_lop` INT UNSIGNED NOT NULL,
    `ngay_hoc` DATE NOT NULL,
    `trang_thai` ENUM('Sắp diễn ra', 'Đang học', 'Đã hoàn thành', 'Bị hủy') NOT NULL,
    FOREIGN KEY(`ma_lop`) REFERENCES `LOP_HOC`(`ma_lop`),
    UNIQUE (ma_lop, ngay_hoc)
);


CREATE TABLE `CAU_HINH_PT`(
    `ma_cau_hinh` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `don_gia_pt` DECIMAL(12, 2) NOT NULL,
    `trang_thai` ENUM('Đang áp dụng', 'Ngừng áp dụng') NOT NULL
);
INSERT INTO `CAU_HINH_PT`
(`don_gia_pt`, `trang_thai`)
VALUES
(200000, 'Đang áp dụng');

