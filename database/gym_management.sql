-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: gym_management
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `buoi_hoc`
--

DROP TABLE IF EXISTS `buoi_hoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buoi_hoc` (
  `ma_buoi` int unsigned NOT NULL AUTO_INCREMENT,
  `ma_lop` int unsigned NOT NULL,
  `ngay_hoc` date NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_buoi`),
  UNIQUE KEY `buoi_hoc_ma_lop_ngay_hoc_unique` (`ma_lop`,`ngay_hoc`),
  CONSTRAINT `buoi_hoc_ma_lop_foreign` FOREIGN KEY (`ma_lop`) REFERENCES `lop_hoc` (`ma_lop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buoi_hoc`
--

LOCK TABLES `buoi_hoc` WRITE;
/*!40000 ALTER TABLE `buoi_hoc` DISABLE KEYS */;
/*!40000 ALTER TABLE `buoi_hoc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buoi_pt`
--

DROP TABLE IF EXISTS `buoi_pt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buoi_pt` (
  `ma_buoi_pt` int unsigned NOT NULL AUTO_INCREMENT,
  `ma_hv` int unsigned NOT NULL,
  `ma_pt` int unsigned NOT NULL,
  `thoi_gian_bat_dau` datetime NOT NULL,
  `thoi_luong` int NOT NULL,
  `don_gia_pt` decimal(12,2) NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `thoi_gian_dat` datetime NOT NULL,
  `thoi_gian_huy` datetime DEFAULT NULL,
  PRIMARY KEY (`ma_buoi_pt`),
  KEY `buoi_pt_ma_hv_foreign` (`ma_hv`),
  KEY `buoi_pt_ma_pt_foreign` (`ma_pt`),
  CONSTRAINT `buoi_pt_ma_hv_foreign` FOREIGN KEY (`ma_hv`) REFERENCES `hoi_vien` (`ma_hv`),
  CONSTRAINT `buoi_pt_ma_pt_foreign` FOREIGN KEY (`ma_pt`) REFERENCES `pt` (`ma_pt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buoi_pt`
--

LOCK TABLES `buoi_pt` WRITE;
/*!40000 ALTER TABLE `buoi_pt` DISABLE KEYS */;
/*!40000 ALTER TABLE `buoi_pt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_in_out`
--

DROP TABLE IF EXISTS `check_in_out`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_in_out` (
  `ma_luot` int unsigned NOT NULL AUTO_INCREMENT,
  `ma_hv` int unsigned NOT NULL,
  `thoi_gian_check_in` datetime NOT NULL,
  `thoi_gian_checkout` datetime DEFAULT NULL,
  PRIMARY KEY (`ma_luot`),
  KEY `check_in_out_ma_hv_foreign` (`ma_hv`),
  CONSTRAINT `check_in_out_ma_hv_foreign` FOREIGN KEY (`ma_hv`) REFERENCES `hoi_vien` (`ma_hv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_in_out`
--

LOCK TABLES `check_in_out` WRITE;
/*!40000 ALTER TABLE `check_in_out` DISABLE KEYS */;
/*!40000 ALTER TABLE `check_in_out` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chi_so_co_the`
--

DROP TABLE IF EXISTS `chi_so_co_the`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_so_co_the` (
  `ma_lan_do` int unsigned NOT NULL AUTO_INCREMENT,
  `ma_hv` int unsigned NOT NULL,
  `ngay_do` date NOT NULL,
  `can_nang` decimal(8,2) NOT NULL,
  `chieu_cao` decimal(8,2) NOT NULL,
  `phan_tram_mo` decimal(8,2) NOT NULL,
  `vong_eo` decimal(8,2) NOT NULL,
  `ghi_chu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_lan_do`),
  KEY `chi_so_co_the_ma_hv_foreign` (`ma_hv`),
  CONSTRAINT `chi_so_co_the_ma_hv_foreign` FOREIGN KEY (`ma_hv`) REFERENCES `hoi_vien` (`ma_hv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_so_co_the`
--

LOCK TABLES `chi_so_co_the` WRITE;
/*!40000 ALTER TABLE `chi_so_co_the` DISABLE KEYS */;
/*!40000 ALTER TABLE `chi_so_co_the` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dang_ky_buoi_hoc`
--

DROP TABLE IF EXISTS `dang_ky_buoi_hoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dang_ky_buoi_hoc` (
  `ma_dk_buoi` int unsigned NOT NULL AUTO_INCREMENT,
  `ma_hv` int unsigned NOT NULL,
  `ma_buoi` int unsigned NOT NULL,
  `thoi_gian_dang_ky` datetime NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_dk_buoi`),
  UNIQUE KEY `dang_ky_buoi_hoc_ma_hv_ma_buoi_unique` (`ma_hv`,`ma_buoi`),
  KEY `dang_ky_buoi_hoc_ma_buoi_foreign` (`ma_buoi`),
  CONSTRAINT `dang_ky_buoi_hoc_ma_buoi_foreign` FOREIGN KEY (`ma_buoi`) REFERENCES `buoi_hoc` (`ma_buoi`),
  CONSTRAINT `dang_ky_buoi_hoc_ma_hv_foreign` FOREIGN KEY (`ma_hv`) REFERENCES `hoi_vien` (`ma_hv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dang_ky_buoi_hoc`
--

LOCK TABLES `dang_ky_buoi_hoc` WRITE;
/*!40000 ALTER TABLE `dang_ky_buoi_hoc` DISABLE KEYS */;
/*!40000 ALTER TABLE `dang_ky_buoi_hoc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dang_ky_goi`
--

DROP TABLE IF EXISTS `dang_ky_goi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dang_ky_goi` (
  `ma_dk_goi` int unsigned NOT NULL AUTO_INCREMENT,
  `ma_hv` int unsigned NOT NULL,
  `ma_goi` int unsigned NOT NULL,
  `ngay_bat_dau` date NOT NULL,
  `ngay_het_han` date NOT NULL,
  `ngay_dang_ky` date NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_dk_goi`),
  KEY `dang_ky_goi_ma_goi_foreign` (`ma_goi`),
  KEY `dang_ky_goi_ma_hv_foreign` (`ma_hv`),
  CONSTRAINT `dang_ky_goi_ma_goi_foreign` FOREIGN KEY (`ma_goi`) REFERENCES `goi_tap` (`ma_goi`),
  CONSTRAINT `dang_ky_goi_ma_hv_foreign` FOREIGN KEY (`ma_hv`) REFERENCES `hoi_vien` (`ma_hv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dang_ky_goi`
--

LOCK TABLES `dang_ky_goi` WRITE;
/*!40000 ALTER TABLE `dang_ky_goi` DISABLE KEYS */;
/*!40000 ALTER TABLE `dang_ky_goi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goi_tap`
--

DROP TABLE IF EXISTS `goi_tap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goi_tap` (
  `ma_goi` int unsigned NOT NULL AUTO_INCREMENT,
  `ten_goi` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `thoi_han_thang` int NOT NULL,
  `gia_tien` decimal(12,2) NOT NULL,
  `so_buoi_pt` int NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_goi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goi_tap`
--

LOCK TABLES `goi_tap` WRITE;
/*!40000 ALTER TABLE `goi_tap` DISABLE KEYS */;
/*!40000 ALTER TABLE `goi_tap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoi_vien`
--

DROP TABLE IF EXISTS `hoi_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoi_vien` (
  `ma_hv` int unsigned NOT NULL AUTO_INCREMENT,
  `cccd` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_sinh` date NOT NULL,
  `gioi_tinh` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dia_chi` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sdt` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_hv`),
  UNIQUE KEY `hoi_vien_cccd_unique` (`cccd`),
  UNIQUE KEY `hoi_vien_sdt_unique` (`sdt`),
  CONSTRAINT `hoi_vien_ma_hv_foreign` FOREIGN KEY (`ma_hv`) REFERENCES `tai_khoan` (`ma_hv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoi_vien`
--

LOCK TABLES `hoi_vien` WRITE;
/*!40000 ALTER TABLE `hoi_vien` DISABLE KEYS */;
/*!40000 ALTER TABLE `hoi_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai_lop`
--

DROP TABLE IF EXISTS `loai_lop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_lop` (
  `ma_loai` int unsigned NOT NULL AUTO_INCREMENT,
  `ten_loai` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `don_gia_pt` decimal(12,2) NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_loai`),
  UNIQUE KEY `loai_lop_ten_loai_unique` (`ten_loai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai_lop`
--

LOCK TABLES `loai_lop` WRITE;
/*!40000 ALTER TABLE `loai_lop` DISABLE KEYS */;
/*!40000 ALTER TABLE `loai_lop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lop_hoc`
--

DROP TABLE IF EXISTS `lop_hoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lop_hoc` (
  `ma_lop` int unsigned NOT NULL AUTO_INCREMENT,
  `ten_lop` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ma_phong` int unsigned NOT NULL,
  `ma_pt` int unsigned NOT NULL,
  `ma_loai` int unsigned NOT NULL,
  `thu_hoc` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gio_bat_dau` time NOT NULL,
  `gio_ket_thuc` time NOT NULL,
  `ngay_bat_dau` date NOT NULL,
  `ngay_ket_thuc` date NOT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_lop`),
  KEY `lop_hoc_ma_pt_foreign` (`ma_pt`),
  KEY `lop_hoc_ma_phong_foreign` (`ma_phong`),
  KEY `lop_hoc_ma_loai_foreign` (`ma_loai`),
  CONSTRAINT `lop_hoc_ma_loai_foreign` FOREIGN KEY (`ma_loai`) REFERENCES `loai_lop` (`ma_loai`),
  CONSTRAINT `lop_hoc_ma_phong_foreign` FOREIGN KEY (`ma_phong`) REFERENCES `phong_tap` (`ma_phong`),
  CONSTRAINT `lop_hoc_ma_pt_foreign` FOREIGN KEY (`ma_pt`) REFERENCES `pt` (`ma_pt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lop_hoc`
--

LOCK TABLES `lop_hoc` WRITE;
/*!40000 ALTER TABLE `lop_hoc` DISABLE KEYS */;
/*!40000 ALTER TABLE `lop_hoc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhan_vien`
--

DROP TABLE IF EXISTS `nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhan_vien` (
  `ma_nv` int unsigned NOT NULL AUTO_INCREMENT,
  `cccd` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_sinh` date NOT NULL,
  `sdt` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `luong_co_ban` decimal(12,2) NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_vao_lam` date NOT NULL,
  PRIMARY KEY (`ma_nv`),
  UNIQUE KEY `nhan_vien_cccd_unique` (`cccd`),
  UNIQUE KEY `nhan_vien_sdt_unique` (`sdt`),
  CONSTRAINT `nhan_vien_ma_nv_foreign` FOREIGN KEY (`ma_nv`) REFERENCES `tai_khoan` (`ma_nv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhan_vien`
--

LOCK TABLES `nhan_vien` WRITE;
/*!40000 ALTER TABLE `nhan_vien` DISABLE KEYS */;
/*!40000 ALTER TABLE `nhan_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phong_tap`
--

DROP TABLE IF EXISTS `phong_tap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phong_tap` (
  `ma_phong` int unsigned NOT NULL AUTO_INCREMENT,
  `ten_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `vi_tri` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `suc_chua` int NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_phong`),
  UNIQUE KEY `phong_tap_ten_phong_unique` (`ten_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phong_tap`
--

LOCK TABLES `phong_tap` WRITE;
/*!40000 ALTER TABLE `phong_tap` DISABLE KEYS */;
/*!40000 ALTER TABLE `phong_tap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pt`
--

DROP TABLE IF EXISTS `pt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pt` (
  `ma_pt` int unsigned NOT NULL AUTO_INCREMENT,
  `cccd` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_sinh` date NOT NULL,
  `sdt` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `chuyen_mon` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `so_nam_kinh_nghiem` int NOT NULL,
  `luong_co_ban` decimal(12,2) NOT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_pt`),
  UNIQUE KEY `pt_cccd_unique` (`cccd`),
  UNIQUE KEY `pt_sdt_unique` (`sdt`),
  CONSTRAINT `pt_ma_pt_foreign` FOREIGN KEY (`ma_pt`) REFERENCES `tai_khoan` (`ma_pt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pt`
--

LOCK TABLES `pt` WRITE;
/*!40000 ALTER TABLE `pt` DISABLE KEYS */;
/*!40000 ALTER TABLE `pt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tai_khoan`
--

DROP TABLE IF EXISTS `tai_khoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tai_khoan` (
  `ma_tk` int unsigned NOT NULL AUTO_INCREMENT,
  `ten_dang_nhap` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mat_khau` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `vai_tro` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ma_hv` int unsigned DEFAULT NULL,
  `ma_nv` int unsigned DEFAULT NULL,
  `ma_pt` int unsigned DEFAULT NULL,
  `trang_thai` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_tk`),
  UNIQUE KEY `tai_khoan_ten_dang_nhap_unique` (`ten_dang_nhap`),
  UNIQUE KEY `tai_khoan_ma_hv_unique` (`ma_hv`),
  UNIQUE KEY `tai_khoan_ma_nv_unique` (`ma_nv`),
  UNIQUE KEY `tai_khoan_ma_pt_unique` (`ma_pt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tai_khoan`
--

LOCK TABLES `tai_khoan` WRITE;
/*!40000 ALTER TABLE `tai_khoan` DISABLE KEYS */;
/*!40000 ALTER TABLE `tai_khoan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-02 14:45:30
