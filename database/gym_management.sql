CREATE DATABASE FITCOREGYM ;
USE FITCOREGYM;
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
    `trang_thai` ENUM('Đang chờ kích hoạt', 'Đang sử dụng', 'Đã hết hạn') NOT NULL,
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
use fitcoregym;
INSERT INTO TAI_KHOAN
(ma_tk, ten_dang_nhap, mat_khau, vai_tro, trang_thai)
VALUES
(1,'hv01','Fitcore@123','HOI_VIEN','Active'),
(2,'hv02','Fitcore@123','HOI_VIEN','Active'),
(3,'hv03','Fitcore@123','HOI_VIEN','Active'),
(4,'hv04','Fitcore@123','HOI_VIEN','Active'),
(5,'hv05','Fitcore@123','HOI_VIEN','Active'),
(6,'hv06','Fitcore@123','HOI_VIEN','Active'),
(7,'hv07','Fitcore@123','HOI_VIEN','Active'),
(8,'hv08','Fitcore@123','HOI_VIEN','Active'),
(9,'hv09','Fitcore@123','HOI_VIEN','Active'),
(10,'hv10','Fitcore@123','HOI_VIEN','Active'),
(11,'hv11','Fitcore@123','HOI_VIEN','Active'),
(12,'hv12','Fitcore@123','HOI_VIEN','Active'),
(13,'hv13','Fitcore@123','HOI_VIEN','Active'),
(14,'hv14','Fitcore@123','HOI_VIEN','Active'),
(15,'hv15','Fitcore@123','HOI_VIEN','Active'),
(16,'hv16','Fitcore@123','HOI_VIEN','Active'),
(17,'hv17','Fitcore@123','HOI_VIEN','Active'),
(18,'hv18','Fitcore@123','HOI_VIEN','Active'),
(19,'hv19','Fitcore@123','HOI_VIEN','Active'),
(20,'hv20','Fitcore@123','HOI_VIEN','Active'),
(21,'hv21','Fitcore@123','HOI_VIEN','Active'),
(22,'hv22','Fitcore@123','HOI_VIEN','Active'),
(23,'hv23','Fitcore@123','HOI_VIEN','Active'),
(24,'hv24','Fitcore@123','HOI_VIEN','Active'),
(25,'hv25','Fitcore@123','HOI_VIEN','Active'),
(26,'hv26','Fitcore@123','HOI_VIEN','Active'),
(27,'hv27','Fitcore@123','HOI_VIEN','Active'),
(28,'hv28','Fitcore@123','HOI_VIEN','Active'),
(29,'hv29','Fitcore@123','HOI_VIEN','Active'),
(30,'hv30','Fitcore@123','HOI_VIEN','Active'),
(31,'hv31','Fitcore@123','HOI_VIEN','Active'),
(32,'hv32','Fitcore@123','HOI_VIEN','Active'),
(33,'hv33','Fitcore@123','HOI_VIEN','Active'),
(34,'hv34','Fitcore@123','HOI_VIEN','Active'),
(35,'hv35','Fitcore@123','HOI_VIEN','Active'),
(36,'hv36','Fitcore@123','HOI_VIEN','Active'),
(37,'hv37','Fitcore@123','HOI_VIEN','Active'),
(38,'hv38','Fitcore@123','HOI_VIEN','Active'),
(39,'hv39','Fitcore@123','HOI_VIEN','Active'),
(40,'hv40','Fitcore@123','HOI_VIEN','Active'),
(41,'hv41','Fitcore@123','HOI_VIEN','Active'),
(42,'hv42','Fitcore@123','HOI_VIEN','Active'),
(43,'hv43','Fitcore@123','HOI_VIEN','Active'),
(44,'hv44','Fitcore@123','HOI_VIEN','Active'),
(45,'hv45','Fitcore@123','HOI_VIEN','Active'),
(46,'hv46','Fitcore@123','HOI_VIEN','Active'),
(47,'hv47','Fitcore@123','HOI_VIEN','Active'),
(48,'hv48','Fitcore@123','HOI_VIEN','Active'),
(49,'hv49','Fitcore@123','HOI_VIEN','Active'),
(50,'hv50','Fitcore@123','HOI_VIEN','Active'),
(51,'hv51','Fitcore@123','HOI_VIEN','Active'),
(52,'hv52','Fitcore@123','HOI_VIEN','Active'),
(53,'hv53','Fitcore@123','HOI_VIEN','Active'),
(54,'hv54','Fitcore@123','HOI_VIEN','Active'),
(55,'hv55','Fitcore@123','HOI_VIEN','Active'),
(56,'hv56','Fitcore@123','HOI_VIEN','Active'),
(57,'hv57','Fitcore@123','HOI_VIEN','Active'),
(58,'hv58','Fitcore@123','HOI_VIEN','Active'),
(59,'hv59','Fitcore@123','HOI_VIEN','Active'),
(60,'hv60','Fitcore@123','HOI_VIEN','Active'),
(61,'hv61','Fitcore@123','HOI_VIEN','Blocked'),
(62,'hv62','Fitcore@123','HOI_VIEN','Blocked'),
(63,'hv63','Fitcore@123','HOI_VIEN','Blocked'),
(64,'hv64','Fitcore@123','HOI_VIEN','Blocked'),
(65,'hv65','Fitcore@123','HOI_VIEN','Blocked'),
(66,'hv66','Fitcore@123','HOI_VIEN','Blocked'),
(67,'hv67','Fitcore@123','HOI_VIEN','Blocked'),
(68,'hv68','Fitcore@123','HOI_VIEN','Blocked'),
(69,'hv69','Fitcore@123','HOI_VIEN','Blocked'),
(70,'hv70','Fitcore@123','HOI_VIEN','Blocked'),

(71,'nv01','Fitcore@123','NHAN_VIEN','Active'),
(72,'nv02','Fitcore@123','NHAN_VIEN','Active'),
(73,'nv03','Fitcore@123','NHAN_VIEN','Active'),
(74,'nv04','Fitcore@123','NHAN_VIEN','Active'),
(75,'nv05','Fitcore@123','NHAN_VIEN','Active'),
(76,'nv06','Fitcore@123','NHAN_VIEN','Active'),
(77,'nv07','Fitcore@123','NHAN_VIEN','Active'),
(78,'nv08','Fitcore@123','NHAN_VIEN','Active'),

(79,'pt01','Fitcore@123','PT','Active'),
(80,'pt02','Fitcore@123','PT','Active'),
(81,'pt03','Fitcore@123','PT','Active'),
(82,'pt04','Fitcore@123','PT','Active'),
(83,'pt05','Fitcore@123','PT','Active'),
(84,'pt06','Fitcore@123','PT','Active');

INSERT INTO HOI_VIEN
(ma_hv, ma_tk, cccd, ho_ten, ngay_sinh, gioi_tinh, dia_chi, sdt, trang_thai)
VALUES
(1,1,'001100000001','Nguyễn Văn An','2002-03-15','Nam','Hà Nội','0901000001','Đang hoạt động'),
(2,2,'001100000002','Trần Thị Bình','2001-07-21','Nữ','Hà Nội','0901000002','Đang hoạt động'),
(3,3,'001100000003','Lê Minh Anh','2003-01-12','Nữ','Hà Nội','0901000003','Đang hoạt động'),
(4,4,'001100000004','Phạm Quốc Bảo','2000-11-08','Nam','Hà Nội','0901000004','Đang hoạt động'),
(5,5,'001100000005','Hoàng Thu Hà','2002-05-19','Nữ','Hà Nội','0901000005','Đang hoạt động'),
(6,6,'001100000006','Vũ Đức Anh','2001-09-10','Nam','Hà Nội','0901000006','Đang hoạt động'),
(7,7,'001100000007','Đặng Ngọc Mai','2003-02-27','Nữ','Hà Nội','0901000007','Đang hoạt động'),
(8,8,'001100000008','Bùi Quang Huy','2000-06-14','Nam','Hà Nội','0901000008','Đang hoạt động'),
(9,9,'001100000009','Đỗ Thị Lan','2001-12-05','Nữ','Hà Nội','0901000009','Đang hoạt động'),
(10,10,'001100000010','Ngô Minh Đức','1999-08-18','Nam','Hà Nội','0901000010','Đang hoạt động'),

(11,11,'001100000011','Phan Tuấn Kiệt','2002-04-23','Nam','Hà Nội','0901000011','Đang hoạt động'),
(12,12,'001100000012','Trương Khánh Linh','2003-10-11','Nữ','Hà Nội','0901000012','Đang hoạt động'),
(13,13,'001100000013','Dương Văn Nam','2001-01-30','Nam','Hà Nội','0901000013','Đang hoạt động'),
(14,14,'001100000014','Mai Thu Trang','2002-08-17','Nữ','Hà Nội','0901000014','Đang hoạt động'),
(15,15,'001100000015','Đinh Hoàng Long','2000-03-29','Nam','Hà Nội','0901000015','Đang hoạt động'),
(16,16,'001100000016','Cao Ngọc Anh','2003-06-08','Nữ','Hà Nội','0901000016','Đang hoạt động'),
(17,17,'001100000017','Lý Minh Quân','2001-11-16','Nam','Hà Nội','0901000017','Đang hoạt động'),
(18,18,'001100000018','Hà Phương Thảo','2002-02-04','Nữ','Hà Nội','0901000018','Đang hoạt động'),
(19,19,'001100000019','Tạ Đức Minh','2000-09-25','Nam','Hà Nội','0901000019','Đang hoạt động'),
(20,20,'001100000020','Chu Hải Yến','2003-05-13','Nữ','Hà Nội','0901000020','Đang hoạt động'),

(21,21,'001100000021','Nguyễn Thành Công','1999-10-02','Nam','Hà Nội','0901000021','Đang hoạt động'),
(22,22,'001100000022','Trần Ngọc Hân','2002-01-18','Nữ','Hà Nội','0901000022','Đang hoạt động'),
(23,23,'001100000023','Lê Quốc Việt','2000-07-09','Nam','Hà Nội','0901000023','Đang hoạt động'),
(24,24,'001100000024','Phạm Thùy Dương','2001-03-22','Nữ','Hà Nội','0901000024','Đang hoạt động'),
(25,25,'001100000025','Hoàng Minh Khang','2003-09-14','Nam','Hà Nội','0901000025','Đang hoạt động'),
(26,26,'001100000026','Vũ Thu Uyên','2002-12-26','Nữ','Hà Nội','0901000026','Đang hoạt động'),
(27,27,'001100000027','Đặng Văn Sơn','1998-05-07','Nam','Hà Nội','0901000027','Đang hoạt động'),
(28,28,'001100000028','Bùi Ngọc Ánh','2001-08-31','Nữ','Hà Nội','0901000028','Đang hoạt động'),
(29,29,'001100000029','Đỗ Minh Khôi','2000-02-15','Nam','Hà Nội','0901000029','Đang hoạt động'),
(30,30,'001100000030','Ngô Thanh Huyền','2003-11-20','Nữ','Hà Nội','0901000030','Đang hoạt động'),

(31,31,'001100000031','Phan Đức Toàn','1999-04-12','Nam','Hà Nội','0901000031','Đang hoạt động'),
(32,32,'001100000032','Trương Mai Chi','2002-06-24','Nữ','Hà Nội','0901000032','Đang hoạt động'),
(33,33,'001100000033','Dương Quốc Hùng','2000-10-19','Nam','Hà Nội','0901000033','Đang hoạt động'),
(34,34,'001100000034','Mai Ngọc Linh','2001-01-07','Nữ','Hà Nội','0901000034','Đang hoạt động'),
(35,35,'001100000035','Đinh Anh Tuấn','1999-07-27','Nam','Hà Nội','0901000035','Đang hoạt động'),
(36,36,'001100000036','Cao Thị Hương','2003-03-11','Nữ','Hà Nội','0901000036','Đang hoạt động'),
(37,37,'001100000037','Lý Hoàng Nam','2000-12-09','Nam','Hà Nội','0901000037','Đang hoạt động'),
(38,38,'001100000038','Hà Minh Châu','2002-09-03','Nữ','Hà Nội','0901000038','Đang hoạt động'),
(39,39,'001100000039','Tạ Quang Vinh','1998-11-28','Nam','Hà Nội','0901000039','Đang hoạt động'),
(40,40,'001100000040','Chu Ngọc Lan','2001-05-16','Nữ','Hà Nội','0901000040','Đang hoạt động'),

(41,41,'001100000041','Nguyễn Đức Thành','1999-08-07','Nam','Hà Nội','0901000041','Đang hoạt động'),
(42,42,'001100000042','Trần Phương Anh','2003-04-18','Nữ','Hà Nội','0901000042','Đang hoạt động'),
(43,43,'001100000043','Lê Minh Hoàng','2000-06-29','Nam','Hà Nội','0901000043','Đang hoạt động'),
(44,44,'001100000044','Phạm Ngọc Diệp','2002-10-06','Nữ','Hà Nội','0901000044','Đang hoạt động'),
(45,45,'001100000045','Hoàng Quốc Dũng','1998-02-21','Nam','Hà Nội','0901000045','Đang hoạt động'),
(46,46,'001100000046','Vũ Khánh Vy','2003-07-13','Nữ','Hà Nội','0901000046','Đang hoạt động'),
(47,47,'001100000047','Đặng Minh Tâm','2001-09-22','Nam','Hà Nội','0901000047','Đang hoạt động'),
(48,48,'001100000048','Bùi Thùy Linh','2002-12-02','Nữ','Hà Nội','0901000048','Đang hoạt động'),
(49,49,'001100000049','Đỗ Quốc Anh','1999-01-25','Nam','Hà Nội','0901000049','Đang hoạt động'),
(50,50,'001100000050','Ngô Bảo Trâm','2003-05-09','Nữ','Hà Nội','0901000050','Đang hoạt động'),

(51,51,'001100000051','Phan Minh Nhật','2000-03-17','Nam','Hà Nội','0901000051','Đang hoạt động'),
(52,52,'001100000052','Trương Đức Huy','2001-08-12','Nam','Hà Nội','0901000052','Đang hoạt động'),
(53,53,'001100000053','Dương Thu Hằng','2002-11-04','Nữ','Hà Nội','0901000053','Đang hoạt động'),
(54,54,'001100000054','Mai Quốc Khánh','1999-06-20','Nam','Hà Nội','0901000054','Đang hoạt động'),
(55,55,'001100000055','Đinh Ngọc Thảo','2003-02-08','Nữ','Hà Nội','0901000055','Đang hoạt động'),
(56,56,'001100000056','Cao Minh Đức','2000-09-17','Nam','Hà Nội','0901000056','Đang hoạt động'),
(57,57,'001100000057','Lý Thanh Tùng','1998-12-11','Nam','Hà Nội','0901000057','Đang hoạt động'),
(58,58,'001100000058','Hà Ngọc Mai','2002-04-06','Nữ','Hà Nội','0901000058','Đang hoạt động'),
(59,59,'001100000059','Tạ Minh Quân','2001-07-30','Nam','Hà Nội','0901000059','Đang hoạt động'),
(60,60,'001100000060','Chu Thùy An','2003-10-23','Nữ','Hà Nội','0901000060','Đang hoạt động'),

(61,61,'001100000061','Nguyễn Văn Phúc','1997-03-12','Nam','Hà Nội','0901000061','Đã ngừng tham gia'),
(62,62,'001100000062','Trần Thị Ngân','1998-08-05','Nữ','Hà Nội','0901000062','Đã ngừng tham gia'),
(63,63,'001100000063','Lê Đức Anh','1996-11-19','Nam','Hà Nội','0901000063','Đã ngừng tham gia'),
(64,64,'001100000064','Phạm Minh Tân','1997-05-26','Nam','Hà Nội','0901000064','Đã ngừng tham gia'),
(65,65,'001100000065','Hoàng Ngọc Sơn','1999-01-13','Nam','Hà Nội','0901000065','Đã ngừng tham gia'),
(66,66,'001100000066','Vũ Hải Nam','1996-07-08','Nam','Hà Nội','0901000066','Đã ngừng tham gia'),
(67,67,'001100000067','Đặng Thu Hà','1998-10-17','Nữ','Hà Nội','0901000067','Đã ngừng tham gia'),
(68,68,'001100000068','Bùi Đức Long','1997-02-22','Nam','Hà Nội','0901000068','Đã ngừng tham gia'),
(69,69,'001100000069','Đỗ Thanh Mai','1999-09-06','Nữ','Hà Nội','0901000069','Đã ngừng tham gia'),
(70,70,'001100000070','Ngô Quốc Thịnh','1996-04-29','Nam','Hà Nội','0901000070','Đã ngừng tham gia');


INSERT INTO NHAN_VIEN
(ma_nv, ma_tk, cccd, ho_ten, ngay_sinh, sdt, luong_co_ban, trang_thai, ngay_vao_lam)
VALUES
(1,71,'001200000001','Nguyễn Thị Hạnh','1992-03-15','0902000001',12000000,'Đang làm việc','2026-05-20'),
(2,72,'001200000002','Trần Văn Phong','1990-07-21','0902000002',13000000,'Đang làm việc','2026-05-20'),
(3,73,'001200000003','Lê Thị Thu','1994-01-12','0902000003',11000000,'Đang làm việc','2026-05-22'),
(4,74,'001200000004','Phạm Minh Tuấn','1991-11-08','0902000004',12500000,'Đang làm việc','2026-05-20'),
(5,75,'001200000005','Hoàng Thị Ngọc','1993-05-19','0902000005',11500000,'Đang làm việc','2026-05-25'),
(6,76,'001200000006','Vũ Văn Dũng','1989-09-10','0902000006',14000000,'Đang làm việc','2026-05-20'),
(7,77,'001200000007','Đặng Thị Mai','1995-02-27','0902000007',10500000,'Đang làm việc','2026-06-01'),
(8,78,'001200000008','Bùi Quốc Khánh','1990-06-14','0902000008',13000000,'Đang làm việc','2026-06-01');

INSERT INTO PT
(ma_pt, ma_tk, cccd, ho_ten, ngay_sinh, sdt, chuyen_mon,
 so_nam_kinh_nghiem, luong_co_ban, trang_thai)
VALUES
(1,79,'001300000001','Nguyễn Hoàng Nam','1990-04-12','0903000001','Gym và tăng cơ',7,18000000,'Đang làm việc'),
(2,80,'001300000002','Trần Đức Minh','1992-08-20','0903000002','HIIT và giảm cân',6,17000000,'Đang làm việc'),
(3,81,'001300000003','Lê Khánh Vy','1994-02-15','0903000003','Yoga và Pilates',5,16000000,'Đang làm việc'),
(4,82,'001300000004','Phạm Quốc Hùng','1988-11-05','0903000004','Thể hình chuyên sâu',9,20000000,'Đang làm việc'),
(5,83,'001300000005','Hoàng Minh Anh','1993-06-18','0903000005','Zumba và Cardio',6,16500000,'Đang làm việc'),
(6,84,'001300000006','Vũ Thành Đạt','1991-12-09','0903000006','Functional Training',8,19000000,'Đang làm việc');

INSERT INTO PHONG_TAP
(ma_phong, ten_phong, vi_tri, suc_chua, trang_thai)
VALUES
(1,'Phòng Yoga','Tầng 1 - Khu A',12,'Đang sử dụng'),
(2,'Phòng HIIT','Tầng 1 - Khu B',20,'Đang sử dụng'),
(3,'Phòng Cardio','Tầng 2 - Khu A',25,'Đang sử dụng'),
(4,'Phòng Pilates','Tầng 2 - Khu B',25,'Đang sử dụng'),
(5,'Phòng PT','Tầng 2 - Khu C',15,'Đang sử dụng');

INSERT INTO LOP_HOC
(ma_lop, ma_phong, ma_pt, so_nguoi_da_dang_ky, ten_lop,
 don_gia_pt, thu_hoc, gio_bat_dau, gio_ket_thuc,
 ngay_bat_dau, ngay_ket_thuc, trang_thai)
VALUES
(1,1,1,12,'Yoga cơ bản',300000,'Thứ 2, Thứ 4','18:00:00','19:00:00',
 '2026-06-01','2026-08-31','Đã đủ người'),

(2,2,2,13,'HIIT giảm cân',350000,'Thứ 3, Thứ 5','18:30:00','19:30:00',
 '2026-06-02','2026-08-31','Lớp đang mở'),

(3,3,5,13,'Zumba Fitness',300000,'Thứ 2, Thứ 6','19:00:00','20:00:00',
 '2026-06-08','2026-08-31','Lớp đang mở'),

(4,4,3,13,'Pilates cơ bản',320000,'Thứ 4, Thứ 7','17:30:00','18:30:00',
 '2026-06-03','2026-08-31','Lớp đang mở'),

(5,3,6,12,'Core Strength',300000,'Thứ 3','19:00:00','20:00:00',
 '2026-06-09','2026-08-11','Đã đóng'),

(6,5,4,12,'Functional Training',400000,'Thứ 5, Thứ 7','18:00:00','19:00:00',
 '2026-06-04','2026-07-30','Đã đóng'),

(7,2,2,12,'Cardio Beginner',280000,'Thứ 2','17:30:00','18:30:00',
 '2026-06-15','2026-08-17','Đã đóng'),

(8,5,6,12,'Body Conditioning',320000,'Thứ 6','18:30:00','19:30:00',
 '2026-06-12','2026-07-31','Đã đóng');
 
 INSERT INTO GOI_TAP
(ma_goi, ten_goi, thoi_han_thang, gia_tien, so_buoi_pt, trang_thai)
VALUES
(1,'Gói Basic',1,500000,0,'Active'),
(2,'Gói Standard',3,1200000,4,'Active'),
(3,'Gói Premium',6,2100000,8,'Active'),
(4,'Gói Student',1,350000,2,'Active'),
(5,'Gói Elite',12,3600000,20,'Active');
 
INSERT INTO DANG_KY_GOI (
    ma_dk_goi,
    ma_hv,
    ma_goi,
    ngay_bat_dau,
    ngay_het_han,
    ngay_dang_ky,
    trang_thai
)
WITH RECURSIVE nums AS (
    SELECT 1 AS n

    UNION ALL

    SELECT n + 1
    FROM nums
    WHERE n < 90
)
SELECT
    n AS ma_dk_goi,

    -- Mã hội viên: chỉ nằm trong khoảng 1 -> 70
    CASE
        WHEN n <= 70 THEN n
        ELSE n - 70
    END AS ma_hv,

    -- Mã gói
    CASE
        WHEN n BETWEEN 1 AND 30 THEN 1
        WHEN n BETWEEN 31 AND 60 THEN 2
        WHEN n BETWEEN 61 AND 70 THEN 1
        WHEN n BETWEEN 71 AND 75 THEN 3
        WHEN n BETWEEN 76 AND 80 THEN 5
        WHEN n BETWEEN 81 AND 85 THEN 2
        WHEN n BETWEEN 86 AND 90 THEN 4
    END AS ma_goi,

    -- NGÀY BẮT ĐẦU
    CASE
        -- Hội viên 1-30: gói cũ đã hết hạn
        WHEN n BETWEEN 1 AND 30 THEN
            DATE_ADD(
                '2026-06-01',
                INTERVAL MOD(n - 1, 10) DAY
            )

        -- Hội viên 31-60: gói Standard đang sử dụng
        WHEN n BETWEEN 31 AND 60 THEN
            DATE_ADD(
                '2026-06-15',
                INTERVAL MOD(n - 31, 10) DAY
            )

        -- Hội viên 61-70: gói cũ đã hết hạn
        WHEN n BETWEEN 61 AND 70 THEN
            DATE_ADD(
                '2026-06-01',
                INTERVAL MOD(n - 61, 10) DAY
            )

        -- Hội viên 1-5: mua thêm Premium
        WHEN n BETWEEN 71 AND 75 THEN
            DATE_ADD(
                '2026-07-15',
                INTERVAL MOD(n - 71, 5) DAY
            )

        -- Hội viên 6-10: mua Elite
        WHEN n BETWEEN 76 AND 80 THEN
            DATE_ADD(
                '2026-07-20',
                INTERVAL MOD(n - 76, 5) DAY
            )

        -- Hội viên 11-15: mua thêm Standard
        WHEN n BETWEEN 81 AND 85 THEN
            DATE_ADD(
                '2026-07-25',
                INTERVAL MOD(n - 81, 5) DAY
            )

        -- Hội viên 16-20: gói Student chờ kích hoạt
        WHEN n BETWEEN 86 AND 90 THEN
            DATE_ADD(
                '2026-09-01',
                INTERVAL MOD(n - 86, 5) DAY
            )
    END AS ngay_bat_dau,

    -- NGÀY HẾT HẠN
    CASE
        -- Basic: 1 tháng
        WHEN n BETWEEN 1 AND 30 THEN
            DATE_SUB(
                DATE_ADD(
                    DATE_ADD(
                        '2026-06-01',
                        INTERVAL MOD(n - 1, 10) DAY
                    ),
                    INTERVAL 1 MONTH
                ),
                INTERVAL 1 DAY
            )

        -- Standard: 3 tháng
        WHEN n BETWEEN 31 AND 60 THEN
            DATE_SUB(
                DATE_ADD(
                    DATE_ADD(
                        '2026-06-15',
                        INTERVAL MOD(n - 31, 10) DAY
                    ),
                    INTERVAL 3 MONTH
                ),
                INTERVAL 1 DAY
            )

        -- Basic: 1 tháng
        WHEN n BETWEEN 61 AND 70 THEN
            DATE_SUB(
                DATE_ADD(
                    DATE_ADD(
                        '2026-06-01',
                        INTERVAL MOD(n - 61, 10) DAY
                    ),
                    INTERVAL 1 MONTH
                ),
                INTERVAL 1 DAY
            )

        -- Premium: 6 tháng
        WHEN n BETWEEN 71 AND 75 THEN
            DATE_SUB(
                DATE_ADD(
                    DATE_ADD(
                        '2026-07-15',
                        INTERVAL MOD(n - 71, 5) DAY
                    ),
                    INTERVAL 6 MONTH
                ),
                INTERVAL 1 DAY
            )

        -- Elite: 12 tháng
        WHEN n BETWEEN 76 AND 80 THEN
            DATE_SUB(
                DATE_ADD(
                    DATE_ADD(
                        '2026-07-20',
                        INTERVAL MOD(n - 76, 5) DAY
                    ),
                    INTERVAL 12 MONTH
                ),
                INTERVAL 1 DAY
            )

        -- Standard: 3 tháng
        WHEN n BETWEEN 81 AND 85 THEN
            DATE_SUB(
                DATE_ADD(
                    DATE_ADD(
                        '2026-07-25',
                        INTERVAL MOD(n - 81, 5) DAY
                    ),
                    INTERVAL 3 MONTH
                ),
                INTERVAL 1 DAY
            )

        -- Student: 1 tháng
        WHEN n BETWEEN 86 AND 90 THEN
            '2026-09-30'
    END AS ngay_het_han,

    -- NGÀY ĐĂNG KÝ
    CASE
        -- Gói cũ
        WHEN n BETWEEN 1 AND 30 THEN
            DATE_ADD(
                '2026-06-01',
                INTERVAL MOD(n - 1, 10) DAY
            )

        -- Standard
        WHEN n BETWEEN 31 AND 60 THEN
            DATE_SUB(
                DATE_ADD(
                    '2026-06-15',
                    INTERVAL MOD(n - 31, 10) DAY
                ),
                INTERVAL 5 DAY
            )

        -- Gói cũ
        WHEN n BETWEEN 61 AND 70 THEN
            DATE_ADD(
                '2026-06-01',
                INTERVAL MOD(n - 61, 10) DAY
            )

        -- Premium
        WHEN n BETWEEN 71 AND 75 THEN
            DATE_SUB(
                DATE_ADD(
                    '2026-07-15',
                    INTERVAL MOD(n - 71, 5) DAY
                ),
                INTERVAL 5 DAY
            )

        -- Elite
        WHEN n BETWEEN 76 AND 80 THEN
            DATE_SUB(
                DATE_ADD(
                    '2026-07-20',
                    INTERVAL MOD(n - 76, 5) DAY
                ),
                INTERVAL 5 DAY
            )

        -- Standard
        WHEN n BETWEEN 81 AND 85 THEN
            DATE_SUB(
                DATE_ADD(
                    '2026-07-25',
                    INTERVAL MOD(n - 81, 5) DAY
                ),
                INTERVAL 5 DAY
            )

        -- Student đăng ký trước ngày kích hoạt
        WHEN n BETWEEN 86 AND 90 THEN
            DATE_ADD(
                '2026-08-25',
                INTERVAL MOD(n - 86, 5) DAY
            )
    END AS ngay_dang_ky,

    -- TRẠNG THÁI
    CASE
        WHEN n BETWEEN 1 AND 30 THEN
            'Đã hết hạn'

        WHEN n BETWEEN 31 AND 60 THEN
            'Đang sử dụng'

        WHEN n BETWEEN 61 AND 70 THEN
            'Đã hết hạn'

        WHEN n BETWEEN 71 AND 85 THEN
            'Đang sử dụng'

        WHEN n BETWEEN 86 AND 90 THEN
            'Đang chờ kích hoạt'
    END AS trang_thai

FROM nums;


INSERT INTO CHI_SO_CO_THE (
    ma_lan_do,
    ma_hv,
    ngay_do,
    can_nang,
    chieu_cao,
    phan_tram_mo,
    vong_eo,
    ghi_chu
)
WITH RECURSIVE nums AS (
    SELECT 1 AS n

    UNION ALL

    SELECT n + 1
    FROM nums
    WHERE n < 150
)
SELECT
    n AS ma_lan_do,

    MOD(n - 1, 60) + 1 AS ma_hv,

    DATE_ADD(
        '2026-06-05',
        INTERVAL MOD(n - 1, 85) DAY
    ) AS ngay_do,

    ROUND(
        55 + MOD(n * 37, 200) / 10,
        2
    ) AS can_nang,

    ROUND(
        160 + MOD(n * 17, 200) / 10,
        2
    ) AS chieu_cao,

    ROUND(
        16 + MOD(n * 23, 100) / 10,
        2
    ) AS phan_tram_mo,

    ROUND(
        65 + MOD(n * 19, 100) / 10,
        2
    ) AS vong_eo,

    CASE
        WHEN MOD(n, 5) = 0 THEN 'Đo định kỳ'
        WHEN MOD(n, 5) = 1 THEN 'Đo sau khi đăng ký'
        WHEN MOD(n, 5) = 2 THEN 'Theo dõi tiến độ'
        WHEN MOD(n, 5) = 3 THEN 'Kiểm tra thể trạng'
        ELSE NULL
    END AS ghi_chu

FROM nums;

INSERT INTO BUOI_PT (
    ma_buoi_pt,
    ma_hv,
    ma_pt,
    thoi_gian_bat_dau,
    don_gia_pt,
    trang_thai,
    thoi_gian_dat,
    thoi_gian_huy
)
WITH RECURSIVE nums AS (
    SELECT 1 AS n

    UNION ALL

    SELECT n + 1
    FROM nums
    WHERE n < 100
)
SELECT
    n AS ma_buoi_pt,

    CASE
        WHEN MOD(n - 1, 45) < 15
            THEN MOD(n - 1, 15) + 1
        ELSE MOD(n - 1, 30) + 16
    END AS ma_hv,

    MOD(n - 1, 6) + 1 AS ma_pt,

    DATE_ADD(
        '2026-06-03 17:00:00',
        INTERVAL (n - 1) * 20 HOUR
    ) AS thoi_gian_bat_dau,

    CASE
        WHEN MOD(n, 3) = 0 THEN 500000
        WHEN MOD(n, 3) = 1 THEN 450000
        ELSE 400000
    END AS don_gia_pt,

    CASE
        WHEN n <= 60 THEN 'Đã hoàn thành'
        WHEN n <= 70 THEN 'Vắng mặt'
        WHEN n <= 80 THEN 'Bị hủy'
        ELSE 'Đã lên lịch'
    END AS trang_thai,

    DATE_SUB(
        DATE_ADD(
            '2026-06-03 17:00:00',
            INTERVAL (n - 1) * 20 HOUR
        ),
        INTERVAL 2 DAY
    ) AS thoi_gian_dat,

    CASE
        WHEN n BETWEEN 71 AND 80 THEN
            DATE_SUB(
                DATE_ADD(
                    '2026-06-03 17:00:00',
                    INTERVAL (n - 1) * 20 HOUR
                ),
                INTERVAL 1 DAY
            )
        ELSE NULL
    END AS thoi_gian_huy

FROM nums;

INSERT INTO CHECK_IN_OUT (
    ma_luot,
    ma_hv,
    thoi_gian_check_in,
    thoi_gian_check_out
)
WITH RECURSIVE nums AS (
    SELECT 1 AS n

    UNION ALL

    SELECT n + 1
    FROM nums
    WHERE n < 500
)
SELECT
    n AS ma_luot,

    CASE
        WHEN MOD(n - 1, 55) < 15
            THEN MOD(n - 1, 15) + 1
        ELSE MOD(n - 1, 35) + 21
    END AS ma_hv,

    DATE_ADD(
        '2026-06-05 06:00:00',
        INTERVAL (n - 1) * 4 HOUR
    ) AS thoi_gian_check_in,

    CASE
        WHEN MOD(n, 20) = 0 THEN NULL

        ELSE DATE_ADD(
            DATE_ADD(
                '2026-06-05 06:00:00',
                INTERVAL (n - 1) * 4 HOUR
            ),
            INTERVAL (45 + MOD(n, 60)) MINUTE
        )
    END AS thoi_gian_check_out

FROM nums;

INSERT INTO BUOI_HOC (
    ma_buoi,
    ma_lop,
    ngay_hoc,
    trang_thai
)
WITH RECURSIVE dates AS (
    SELECT DATE('2026-06-01') AS ngay

    UNION ALL

    SELECT DATE_ADD(ngay, INTERVAL 1 DAY)
    FROM dates
    WHERE ngay < '2026-08-31'
)
SELECT
    ROW_NUMBER() OVER (
        ORDER BY l.ma_lop, d.ngay
    ) AS ma_buoi,

    l.ma_lop,

    d.ngay AS ngay_hoc,

    CASE
        WHEN d.ngay < '2026-08-31'
            THEN 'Đã hoàn thành'

        WHEN d.ngay = '2026-08-31'
            THEN 'Đang học'

        ELSE 'Sắp diễn ra'
    END AS trang_thai

FROM LOP_HOC l
JOIN dates d
    ON d.ngay BETWEEN l.ngay_bat_dau AND l.ngay_ket_thuc

WHERE
       (FIND_IN_SET('Thứ 2', l.thu_hoc) > 0 AND DAYOFWEEK(d.ngay) = 2)
    OR (FIND_IN_SET('Thứ 3', l.thu_hoc) > 0 AND DAYOFWEEK(d.ngay) = 3)
    OR (FIND_IN_SET('Thứ 4', l.thu_hoc) > 0 AND DAYOFWEEK(d.ngay) = 4)
    OR (FIND_IN_SET('Thứ 5', l.thu_hoc) > 0 AND DAYOFWEEK(d.ngay) = 5)
    OR (FIND_IN_SET('Thứ 6', l.thu_hoc) > 0 AND DAYOFWEEK(d.ngay) = 6)
    OR (FIND_IN_SET('Thứ 7', l.thu_hoc) > 0 AND DAYOFWEEK(d.ngay) = 7)
    OR (FIND_IN_SET('Chủ nhật', l.thu_hoc) > 0 AND DAYOFWEEK(d.ngay) = 1)

ORDER BY l.ma_lop, d.ngay;

INSERT INTO DANG_KY_LOP_HOC (
    ma_dk_lop,
    ma_hv,
    ma_lop,
    thoi_gian_dang_ky,
    ngay_het_han,
    ngay_huy,
    trang_thai
)
WITH RECURSIVE nums AS (
    SELECT 1 AS n

    UNION ALL

    SELECT n + 1
    FROM nums
    WHERE n < 100
)
SELECT
    n AS ma_dk_lop,

    CASE
        WHEN n <= 70 THEN n
        ELSE n - 70
    END AS ma_hv,

    MOD(n - 1, 8) + 1 AS ma_lop,

    DATE_ADD(
        '2026-06-01 08:00:00',
        INTERVAL (n - 1) * 12 HOUR
    ) AS thoi_gian_dang_ky,

    '2026-08-31' AS ngay_het_han,

    CASE
        WHEN n = 1 THEN '2026-07-10 10:00:00'
        ELSE NULL
    END AS ngay_huy,

    CASE
        WHEN n = 1 THEN 'Đã bị hủy'
        ELSE 'Đăng kí thành công'
    END AS trang_thai

FROM nums;

