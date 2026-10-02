# FITCORE — BACKEND / FRONTEND HANDOFF DOCUMENT

> Tài liệu này được tổng hợp trực tiếp từ source code trong `backend(1).zip` mà nhóm đang dùng.
>
> Mục đích: giao cho người làm Frontend biết chính xác Backend hiện tại làm gì, gọi API nào, role nào được phép làm gì, request/response ra sao, nghiệp vụ nào Backend đã xử lý, và Frontend cần tuân thủ quy tắc nào để tích hợp ổn định.
>
> **Nguyên tắc:** Backend là nguồn dữ liệu và nghiệp vụ cuối cùng. Frontend chỉ chịu trách nhiệm giao diện, nhập dữ liệu, gọi API, hiển thị trạng thái và điều hướng.

---

# 1. TỔNG QUAN BACKEND

## 1.1. Công nghệ hiện tại

Backend trong source hiện tại sử dụng:

- Java 21.
- Spring Boot 4.1.1.
- Spring Web MVC.
- Spring Data JPA.
- MySQL.
- Spring Security.
- JWT với JJWT 0.12.6.
- BCrypt để mã hóa mật khẩu.
- Bean Validation (`jakarta.validation`).

Port hiện tại:

```text
http://localhost:8080
```

API base:

```text
http://localhost:8080/api
```

Database:

```text
gym_management
```

Hibernate:

```text
spring.jpa.hibernate.ddl-auto=none
```

=> Backend hiện tại **không tự tạo/sửa schema bằng Hibernate**.

---

# 2. KIẾN TRÚC LOGIC

Backend có thể hiểu theo các lớp:

```text
Frontend
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
MySQL
```

Authentication:

```text
Frontend
   |
   | Authorization: Bearer <JWT>
   v
JwtAuthenticationFilter
   |
   v
Spring Security
   |
   v
Controller
```

Frontend không nên gọi Repository hoặc DB trực tiếp.

---

# 3. BA ROLE CHÍNH

Backend hiện dùng đúng 3 role:

```text
NHAN_VIEN
PT
HOI_VIEN
```

## 3.1. NHAN_VIEN

NHAN_VIEN là role quản trị/nghiệp vụ vận hành:

- Tài khoản.
- Hội viên.
- PT.
- Phòng tập.
- Gói tập.
- Lớp học.
- Đăng ký gói.
- Buổi PT.
- Check-in/out toàn hệ thống.
- Nhập chỉ số cơ thể cho hội viên.
- Cấu hình giá PT.
- Thống kê tổng quan.

## 3.2. PT

PT phụ trách công việc chuyên môn của chính mình:

- Xem/cập nhật hồ sơ cá nhân.
- Xem lớp học.
- Xem buổi PT của mình.
- Cập nhật kết quả buổi PT của chính mình.
- Xem thống kê cá nhân.

PT không phải admin.

## 3.3. HOI_VIEN

Hội viên dùng các chức năng self-service:

- Đăng ký tài khoản.
- Hoàn thiện hồ sơ.
- Xem/cập nhật hồ sơ cá nhân.
- Xem gói tập.
- Đăng ký gói.
- Xem đăng ký gói của mình.
- Hủy đăng ký gói đang chờ kích hoạt.
- Xem lớp.
- Đăng ký lớp.
- Hủy đăng ký lớp.
- Đặt buổi PT.
- Xem/hủy buổi PT của mình.
- Check-in/check-out.
- Xem lịch sử chỉ số cơ thể.
- Xem chỉ số mới nhất.
- Xem thống kê cá nhân.

---

# 4. AUTHENTICATION / JWT

## 4.1. Login

```http
POST /api/auth/login
```

Request:

```json
{
  "tenDangNhap": "hoivien01",
  "matKhau": "123456"
}
```

Response:

```json
{
  "token": "...",
  "maTk": 1,
  "tenDangNhap": "hoivien01",
  "vaiTro": "HOI_VIEN",
  "trangThai": "Active"
}
```

Frontend cần lưu token và thông tin authentication cần thiết.

---

## 4.2. Gọi API cần đăng nhập

Header:

```http
Authorization: Bearer <JWT>
```

Ví dụ:

```http
GET /api/lop-hoc
Authorization: Bearer eyJ...
```

Không gửi:

```text
Authorization: <JWT>
```

Phải có:

```text
Authorization: Bearer <JWT>
```

---

## 4.3. JWT chứa gì?

Backend tạo JWT với:

```text
subject = username
claim userId = maTk
claim role = vaiTro
issuedAt
expiration
```

Frontend có thể sử dụng response login để lưu role.

Backend vẫn là bên quyết định quyền truy cập.

**Không được coi role ở Frontend là cơ chế bảo mật.**

Frontend có thể dùng role để:

- render menu;
- render dashboard;
- route;
- ẩn/hiện button.

Nhưng Backend vẫn phải kiểm tra quyền.

---

## 4.4. Thời gian sống token

Cấu hình hiện tại:

```text
jwt.expiration = 86400000
```

Tương đương 24 giờ.

Backend hiện tại **không có refresh-token endpoint** trong source.

Frontend nên xử lý trường hợp JWT hết hạn bằng cách đưa người dùng về Login.

---

# 5. REGISTER

```http
POST /api/auth/register
```

Request:

```json
{
  "tenDangNhap": "hoivien01",
  "matKhau": "123456"
}
```

Backend tự đặt:

```text
vaiTro = HOI_VIEN
trangThai = Active
```

Frontend **không được gửi role để biến mình thành PT/NHAN_VIEN**.

Sau register:

```text
TAI_KHOAN được tạo
    |
    v
role = HOI_VIEN
    |
    v
chưa chắc đã có HOI_VIEN profile
    |
    v
hoàn thiện hồ sơ
```

---

# 6. HOÀN THIỆN HỒ SƠ HỘI VIÊN

Endpoint:

```http
POST /api/hoi-vien/cua-toi
```

Request:

```json
{
  "cccd": "001234567890",
  "hoTen": "Nguyễn Văn A",
  "ngaySinh": "2004-01-01",
  "gioiTinh": "Nam",
  "diaChi": "Hải Dương",
  "sdt": "0901234567"
}
```

Backend tự liên kết profile với account trong JWT.

Frontend **không gửi `maTk`** cho flow self-service này.

Backend kiểm tra:

- tài khoản tồn tại;
- role phải là `HOI_VIEN`;
- tài khoản chưa có profile;
- CCCD không trùng;
- SĐT không trùng.

Khi tạo profile self-service, trạng thái hội viên được đặt:

```text
Đang hoạt động
```

---

# 7. HỒ SƠ HỘI VIÊN CỦA TÔI

## Xem

```http
GET /api/hoi-vien/cua-toi
```

## Sửa

```http
PUT /api/hoi-vien/cua-toi
```

Body giống phần hoàn thiện hồ sơ:

```json
{
  "cccd": "001234567890",
  "hoTen": "Nguyễn Văn A",
  "ngaySinh": "2004-01-01",
  "gioiTinh": "Nam",
  "diaChi": "Hải Dương",
  "sdt": "0901234567"
}
```

Frontend không gửi `maHv`.

Backend lấy hội viên từ JWT.

---

# 8. HỘI VIÊN — API ADMIN

Các API:

```http
GET    /api/hoi-vien
GET    /api/hoi-vien/{id}
POST   /api/hoi-vien
PUT    /api/hoi-vien/{id}
DELETE /api/hoi-vien/{id}
```

Role:

```text
NHAN_VIEN
```

Request admin chứa:

```text
maTk
cccd
hoTen
ngaySinh
gioiTinh
diaChi
sdt
trangThai
```

Response:

```json
{
  "maHv": 1,
  "maTk": 10,
  "cccd": "001234567890",
  "hoTen": "Nguyễn Văn A",
  "ngaySinh": "2004-01-01",
  "gioiTinh": "Nam",
  "diaChi": "Hải Dương",
  "sdt": "0901234567",
  "trangThai": "Đang hoạt động"
}
```

---

# 9. GÓI TẬP

## 9.1. Xem danh sách

```http
GET /api/goi-tap
```

Role:

```text
NHAN_VIEN
HOI_VIEN
```

## 9.2. Xem chi tiết

```http
GET /api/goi-tap/{id}
```

Role:

```text
NHAN_VIEN
HOI_VIEN
```

## 9.3. Admin CRUD

```http
POST   /api/goi-tap
PUT    /api/goi-tap/{id}
DELETE /api/goi-tap/{id}
```

Role:

```text
NHAN_VIEN
```

Request:

```json
{
  "tenGoi": "Gói Premium",
  "thoiHanThang": 3,
  "giaTien": 1500000,
  "soBuoiPt": 4,
  "trangThai": "Active"
}
```

Response:

```json
{
  "maGoi": 1,
  "tenGoi": "Gói Premium",
  "thoiHanThang": 3,
  "giaTien": 1500000,
  "soBuoiPt": 4,
  "trangThai": "Active"
}
```

---

# 10. ĐĂNG KÝ GÓI TẬP

## 10.1. Hội viên xem các gói đã đăng ký

```http
GET /api/dang-ky-goi/cua-toi
```

Role:

```text
HOI_VIEN
```

## 10.2. Hội viên đăng ký

```http
POST /api/dang-ky-goi/cua-toi
```

Request:

```json
{
  "maGoi": 1,
  "ngayBatDau": "2026-10-05"
}
```

Backend tự lấy `maHv` từ JWT.

Không gửi:

```text
maHv
```

Backend kiểm tra:

- hội viên tồn tại;
- không có gói đang chờ kích hoạt;
- không có gói đang sử dụng;
- gói tồn tại;
- gói phải `Active`.

Ngày hết hạn được tính:

```text
ngayHetHan = ngayBatDau + thoiHanThang
```

Trạng thái mới:

```text
Đang chờ kích hoạt
```

---

## 10.3. Hội viên hủy đăng ký gói của mình

```http
PATCH /api/dang-ky-goi/cua-toi/{id}/huy
```

Chỉ hủy được trạng thái:

```text
Đang chờ kích hoạt
```

Nếu đã:

```text
Đang sử dụng
```

thì không được hủy bằng flow này.

---

## 10.4. Admin quản lý đăng ký gói

```http
GET    /api/dang-ky-goi
GET    /api/dang-ky-goi/{id}
GET    /api/dang-ky-goi/hoi-vien/{maHv}
POST   /api/dang-ky-goi
PATCH  /api/dang-ky-goi/{id}/kich-hoat
PATCH  /api/dang-ky-goi/{id}/huy
```

Role:

```text
NHAN_VIEN
```

Admin có thể kích hoạt:

```text
Đang chờ kích hoạt
        |
        v
Đang sử dụng
```

Nhưng Backend vẫn kiểm tra:

- đăng ký tồn tại;
- đang ở trạng thái chờ kích hoạt;
- chưa hết hạn;
- hội viên chưa có gói đang sử dụng.

---

# 11. LỚP HỌC

## 11.1. Xem danh sách lớp

```http
GET /api/lop-hoc
```

Role:

```text
NHAN_VIEN
PT
HOI_VIEN
```

## 11.2. Xem chi tiết

```http
GET /api/lop-hoc/{id}
```

Role:

```text
NHAN_VIEN
PT
HOI_VIEN
```

## 11.3. Xem các buổi học của lớp

```http
GET /api/lop-hoc/{id}/buoi-hoc
```

Role:

```text
NHAN_VIEN
PT
HOI_VIEN
```

Response buổi học:

```json
{
  "maBuoi": 1,
  "maLop": 2,
  "ngayHoc": "2026-10-05",
  "trangThai": "Sắp diễn ra"
}
```

---

# 12. TRẠNG THÁI LỚP HỌC

Backend hiện dùng:

```text
Lớp đang mở
Đã đủ người
Đã đóng
```

Logic hiện tại:

```text
Nếu hôm nay > ngày kết thúc
    => Đã đóng

Nếu số người đăng ký >= sức chứa phòng
    => Đã đủ người

Ngược lại
    => Lớp đang mở
```

Frontend nên hiển thị đúng `trangThai` từ Backend.

Không nên tự coi Frontend là nguồn sự thật cuối cùng.

---

# 13. ADMIN TẠO LỚP

```http
POST /api/lop-hoc
```

Request:

```json
{
  "maPhong": 1,
  "maPt": 1,
  "tenLop": "Yoga Beginner",
  "donGiaPt": 200000,
  "thuHoc": "2,4,6",
  "gioBatDau": "18:00",
  "gioKetThuc": "19:00",
  "ngayBatDau": "2026-10-05",
  "ngayKetThuc": "2026-12-31"
}
```

Role:

```text
NHAN_VIEN
```

Backend kiểm tra:

- tên lớp không trùng;
- phòng tồn tại;
- phòng không bảo trì;
- PT tồn tại;
- PT phải `Đang làm việc`;
- giờ bắt đầu < giờ kết thúc;
- ngày bắt đầu <= ngày kết thúc;
- `thuHoc` có format hợp lệ.

Format `thuHoc`:

```text
2,4,6
7,CN
2,3,5,7
```

Các giá trị hợp lệ:

```text
2
3
4
5
6
7
CN
```

---

# 14. SINH BUỔI HỌC

Khi tạo lớp, Backend tự sinh `BUOI_HOC` dựa trên:

```text
ngày bắt đầu
ngày kết thúc
thuHoc
```

Frontend **không tạo từng buổi học**.

Ví dụ:

```text
thuHoc = 2,4,6
```

=> Backend tự sinh các buổi vào thứ 2, 4, 6 trong khoảng ngày.

---

# 15. ĐĂNG KÝ LỚP

Endpoint hiện tại:

```http
POST /api/dang-ky-lop/cua-toi
```

Role:

```text
HOI_VIEN
```

Body:

```json
{
  "maLop": 1
}
```

Backend tự xác định hội viên từ JWT.

Backend kiểm tra:

- hội viên tồn tại;
- hội viên đang hoạt động;
- lớp tồn tại;
- lớp chưa đóng;
- lớp chưa đầy;
- hội viên chưa đăng ký lớp;
- hội viên phải có gói đang sử dụng và còn hiệu lực.

Khi đăng ký thành công:

```text
trangThai = Đăng kí thành công
```

`ngayHetHan` của đăng ký lớp được lấy theo ngày nhỏ hơn giữa:

```text
ngày hết hạn gói
ngày kết thúc lớp
```

---

# 16. HỦY ĐĂNG KÝ LỚP

```http
DELETE /api/dang-ky-lop/cua-toi?maLop=1
```

Role:

```text
HOI_VIEN
```

Backend:

- tìm đăng ký mới nhất của hội viên và lớp;
- nếu đã hủy => báo lỗi;
- đặt trạng thái `Đã bị hủy`;
- lưu `ngayHuy`;
- giảm `soNguoiDaDangKy`;
- cập nhật lại trạng thái lớp.

Frontend sau khi hủy nên refresh:

```text
đăng ký
↓
chi tiết lớp
↓
số người
↓
trạng thái lớp
```

---

# 17. BUỔI PT

## 17.1. Hội viên đặt buổi PT

```http
POST /api/buoi-pt/cua-toi
```

Body:

```json
{
  "maPt": 1,
  "thoiGianBatDau": "2026-10-05T09:00:00"
}
```

`thoiGianBatDau` phải ở tương lai.

Backend tự:

- lấy hội viên từ JWT;
- kiểm tra hội viên đang hoạt động;
- kiểm tra PT;
- PT phải đang làm việc;
- PT không đang dạy lớp tại thời điểm đó;
- PT không có lịch trùng;
- hội viên phải có gói đang sử dụng;
- thời gian đặt phải nằm trong thời hạn gói;
- gói phải có số buổi PT;
- chưa sử dụng hết số buổi PT;
- lấy giá PT hiện hành từ `CAU_HINH_PT`.

Frontend **không gửi `donGiaPt`**.

Frontend **không tự tính số buổi còn lại**.

Backend mới quyết định.

---

# 18. TRẠNG THÁI BUỔI PT

```text
Đã lên lịch
Đã hoàn thành
Vắng mặt
Bị hủy
```

Response:

```json
{
  "maBuoiPt": 1,
  "maHv": 1,
  "maPt": 1,
  "thoiGianBatDau": "2026-10-05T09:00:00",
  "donGiaPt": 200000,
  "trangThai": "Đã lên lịch",
  "thoiGianDat": "2026-10-02T21:00:00",
  "thoiGianHuy": null
}
```

---

# 19. HỘI VIÊN — BUỔI PT

## Xem lịch của mình

```http
GET /api/buoi-pt/cua-toi
```

## Hủy

```http
DELETE /api/buoi-pt/cua-toi/{maBuoiPt}
```

Chỉ hủy được:

```text
Đã lên lịch
```

và thời điểm buổi PT phải chưa bắt đầu.

Hội viên không thể hủy buổi của người khác.

---

# 20. PT — BUỔI PT

## Xem lịch của mình

```http
GET /api/buoi-pt/pt-cua-toi
```

## Cập nhật trạng thái

```http
PUT /api/buoi-pt/{maBuoiPt}/trang-thai
```

Body:

```json
{
  "trangThai": "Đã hoàn thành"
}
```

hoặc:

```json
{
  "trangThai": "Vắng mặt"
}
```

PT chỉ được:

```text
Đã lên lịch
    |
    +--> Đã hoàn thành
    |
    +--> Vắng mặt
```

PT không được cập nhật buổi của PT khác.

PT không được tự đổi trạng thái sang:

```text
Bị hủy
```

---

# 21. NHÂN VIÊN — BUỔI PT

Xem tất cả:

```http
GET /api/buoi-pt
```

Cập nhật trạng thái:

```http
PUT /api/buoi-pt/{maBuoiPt}/trang-thai
```

NHAN_VIEN có phạm vi rộng hơn PT.

---

# 22. CHECK-IN / CHECK-OUT

## Hội viên check-in

```http
POST /api/check-in-out/cua-toi/check-in
```

Không cần body.

Backend lấy hội viên từ JWT.

Backend kiểm tra:

- hội viên tồn tại;
- hội viên đang hoạt động;
- chưa có lượt check-in chưa checkout.

Nếu đang ở gym:

```text
ALREADY_CHECKED_IN
```

---

## Hội viên check-out

```http
PUT /api/check-in-out/cua-toi/check-out
```

Không cần body.

Nếu không có lượt đang mở:

```text
NOT_CHECKED_IN
```

---

## Xem lịch sử

```http
GET /api/check-in-out/cua-toi
```

## Nhân viên xem toàn hệ thống

```http
GET /api/check-in-out
```

Role:

```text
NHAN_VIEN
```

---

# 23. CHỈ SỐ CƠ THỂ

## Nhân viên nhập

```http
POST /api/chi-so-co-the/hoi-vien/{maHv}
```

Body:

```json
{
  "ngayDo": "2026-10-02",
  "canNang": 60.5,
  "chieuCao": 170,
  "phanTramMo": 18.5,
  "vongEo": 75,
  "ghiChu": "Đo định kỳ"
}
```

Validation:

```text
ngayDo <= hôm nay
canNang > 0
chieuCao > 0
phanTramMo: 0..100
vongEo > 0
```

---

## Hội viên xem lịch sử

```http
GET /api/chi-so-co-the/cua-toi
```

## Hội viên xem mới nhất

```http
GET /api/chi-so-co-the/cua-toi/moi-nhat
```

Nếu chưa có dữ liệu mới nhất:

```text
404
BODY_METRICS_NOT_FOUND
```

---

# 24. THỐNG KÊ

## Nhân viên

```http
GET /api/thong-ke/tong-quan
```

Response dùng các field:

```text
tongHoiVien
hoiVienDangHoatDong
tongPt
ptDangLamViec
tongGoiTapDangSuDung
tongLopHoc
tongBuoiPt
tongLuotCheckIn
```

Các field phạm vi PT/Hội viên sẽ null trong response tổng quan.

---

## PT

```http
GET /api/thong-ke/pt/cua-toi
```

Dùng:

```text
soHocVien
soBuoiPtDaLenLich
soBuoiPtDaHoanThanh
soBuoiPtVangMat
```

Các field khác có thể null.

---

## Hội viên

```http
GET /api/thong-ke/hoi-vien/cua-toi
```

Dùng:

```text
soGoiTapDaDangKy
soBuoiPtDaSuDung
soLopHocDaDangKy
soLanCheckIn
soLanDoChiSoCoThe
```

Các field khác có thể null.

---

# 25. PT PROFILE

## PT xem profile

```http
GET /api/pt/cua-toi
```

## PT cập nhật profile

```http
PUT /api/pt/cua-toi
```

Request:

```json
{
  "cccd": "001300000001",
  "hoTen": "Nguyễn Hoàng Nam",
  "ngaySinh": "1990-04-12",
  "sdt": "0903000001",
  "chuyenMon": "Gym và tăng cơ",
  "soNamKinhNghiem": 7
}
```

PT self-service không được gửi các field:

```text
maTk
luongCoBan
trangThai
```

Các field này thuộc quản lý nhân sự.

---

# 26. ADMIN QUẢN LÝ PT

```http
GET    /api/pt
GET    /api/pt/{id}
POST   /api/pt
PUT    /api/pt/{id}
DELETE /api/pt/{id}
```

Role:

```text
NHAN_VIEN
```

Request admin:

```json
{
  "maTk": 20,
  "cccd": "001300000001",
  "hoTen": "Nguyễn Hoàng Nam",
  "ngaySinh": "1990-04-12",
  "sdt": "0903000001",
  "chuyenMon": "Gym và tăng cơ",
  "soNamKinhNghiem": 7,
  "luongCoBan": 18000000,
  "trangThai": "Đang làm việc"
}
```

Backend kiểm tra:

- tài khoản tồn tại;
- role tài khoản là `PT`;
- tài khoản chưa liên kết PT khác;
- CCCD không trùng;
- SĐT không trùng.

---

# 27. PHÒNG TẬP

CRUD admin:

```http
GET    /api/phong-tap
GET    /api/phong-tap/{id}
POST   /api/phong-tap
PUT    /api/phong-tap/{id}
DELETE /api/phong-tap/{id}
```

Role:

```text
NHAN_VIEN
```

Request:

```json
{
  "tenPhong": "Phòng Yoga",
  "viTri": "Tầng 1 - Khu A",
  "sucChua": 12,
  "trangThai": "Đang sử dụng"
}
```

Trạng thái hợp lệ:

```text
Đang sử dụng
Bảo trì
```

Tên phòng không được trùng.

---

# 28. CẤU HÌNH GIÁ PT

## Xem giá hiện tại

```http
GET /api/cau-hinh-pt
```

Role:

```text
NHAN_VIEN
```

## Cập nhật giá

```http
PUT /api/cau-hinh-pt
```

Body:

```json
{
  "donGiaPt": 200000
}
```

Backend:

1. trạng thái giá cũ -> `Ngừng áp dụng`;
2. tạo cấu hình mới;
3. cấu hình mới -> `Đang áp dụng`.

Frontend không nên tự lấy giá cũ rồi tính giá mới.

---

# 29. TÀI KHOẢN — ADMIN

```http
GET    /api/tai-khoan
GET    /api/tai-khoan/{id}
POST   /api/tai-khoan
PUT    /api/tai-khoan/{id}
DELETE /api/tai-khoan/{id}
```

Role:

```text
NHAN_VIEN
```

Request:

```json
{
  "tenDangNhap": "pt01",
  "matKhau": "123456",
  "vaiTro": "PT",
  "trangThai": "Active"
}
```

Mật khẩu được BCrypt hash trước khi lưu.

Frontend không bao giờ hiển thị password dạng plaintext từ response vì response account không chứa password.

---

# 30. BẢNG MA TRẬN API / ROLE

| API | Method | NHAN_VIEN | PT | HOI_VIEN |
|---|---|---:|---:|---:|
| `/api/auth/login` | POST | ✅ | ✅ | ✅ |
| `/api/auth/register` | POST | ✅* | ✅* | ✅ |
| `/api/tai-khoan` | GET/POST | ✅ | ❌ | ❌ |
| `/api/hoi-vien` | CRUD | ✅ | ❌ | ❌ |
| `/api/hoi-vien/cua-toi` | GET/POST/PUT | ❌ | ❌ | ✅ |
| `/api/pt` | CRUD | ✅ | ❌ | ❌ |
| `/api/pt/cua-toi` | GET/PUT | ❌ | ✅ | ❌ |
| `/api/goi-tap` | GET | ✅ | ❌ | ✅ |
| `/api/goi-tap` | POST/PUT/DELETE | ✅ | ❌ | ❌ |
| `/api/dang-ky-goi` | Admin API | ✅ | ❌ | ❌ |
| `/api/dang-ky-goi/cua-toi` | GET/POST/PATCH | ❌ | ❌ | ✅ |
| `/api/phong-tap` | CRUD | ✅ | ❌ | ❌ |
| `/api/lop-hoc` | GET | ✅ | ✅ | ✅ |
| `/api/lop-hoc` | POST/PUT/DELETE | ✅ | ❌ | ❌ |
| `/api/lop-hoc/{id}/buoi-hoc` | GET | ✅ | ✅ | ✅ |
| `/api/dang-ky-lop/cua-toi` | POST/DELETE | ❌ | ❌ | ✅ |
| `/api/buoi-pt` | GET all | ✅ | ❌ | ❌ |
| `/api/buoi-pt/cua-toi` | POST/GET/DELETE | ❌ | ❌ | ✅ |
| `/api/buoi-pt/pt-cua-toi` | GET | ❌ | ✅ | ❌ |
| `/api/buoi-pt/{id}/trang-thai` | PUT | ✅ | ✅ | ❌ |
| `/api/check-in-out/cua-toi/...` | POST/PUT/GET | ❌ | ❌ | ✅ |
| `/api/check-in-out` | GET | ✅ | ❌ | ❌ |
| `/api/chi-so-co-the/hoi-vien/{maHv}` | POST | ✅ | ❌ | ❌ |
| `/api/chi-so-co-the/cua-toi` | GET | ❌ | ❌ | ✅ |
| `/api/chi-so-co-the/cua-toi/moi-nhat` | GET | ❌ | ❌ | ✅ |
| `/api/cau-hinh-pt` | GET/PUT | ✅ | ❌ | ❌ |
| `/api/thong-ke/tong-quan` | GET | ✅ | ❌ | ❌ |
| `/api/thong-ke/pt/cua-toi` | GET | ❌ | ✅ | ❌ |
| `/api/thong-ke/hoi-vien/cua-toi` | GET | ❌ | ❌ | ✅ |

`*` Register vẫn tạo **HOI_VIEN**; endpoint permitAll vì chưa đăng nhập.

---

# 31. REQUEST / RESPONSE CONVENTIONS

## LocalDate

Dùng dạng:

```text
YYYY-MM-DD
```

Ví dụ:

```text
2026-10-05
```

## LocalDateTime

Dùng dạng ISO:

```text
YYYY-MM-DDTHH:mm:ss
```

Ví dụ:

```text
2026-10-05T09:00:00
```

## LocalTime

Ví dụ:

```text
18:00:00
```

Frontend nên dùng chuẩn ISO khi gửi data thời gian.

---

# 32. VALIDATION FRONTEND NÊN PHẢN ÁNH

Backend đã có validation.

Frontend nên validate trước để UX tốt hơn, nhưng vẫn phải coi validation Backend là nguồn cuối cùng.

## Username

```text
3 - 50 ký tự
```

## Password

```text
>= 6 ký tự
```

## Số điện thoại hội viên

```text
10 - 11 số
```

## Sức chứa phòng

```text
>= 1
```

## Số năm kinh nghiệm

```text
>= 0
```

## Giá tiền

```text
> 0
```

## Số buổi PT

```text
>= 0
```

## Chỉ số cơ thể

```text
Cân nặng > 0
Chiều cao > 0
% mỡ từ 0 đến 100
Vòng eo > 0
Ngày đo <= hiện tại
```

## Buổi PT

```text
thoiGianBatDau phải ở tương lai
```

---

# 33. ERROR RESPONSE

## BusinessException

Backend trả:

```json
{
  "status": 400,
  "code": "LOP_FULL",
  "message": "Lớp học đã đủ số người"
}
```

Frontend nên dùng:

```text
code
message
```

để hiển thị UX phù hợp.

Ví dụ:

```text
LOP_FULL
=> "Lớp này đã đủ người."
```

---

## Validation error

Backend trả dạng:

```json
{
  "status": 400,
  "message": "Dữ liệu không hợp lệ",
  "errors": {
    "sdt": "Số điện thoại phải từ 10 đến 11 số"
  }
}
```

Frontend cần hiển thị lỗi đúng field nếu có thể.

---

# 34. CÁC STATUS HTTP FRONTEND CẦN XỬ LÝ

## 200

Request thành công.

## 400

Dữ liệu không hợp lệ hoặc vi phạm nghiệp vụ.

Ví dụ:

```text
LOP_FULL
NO_ACTIVE_PACKAGE
PT_NOT_AVAILABLE
INVALID_STATUS
```

## 401

Authentication không hợp lệ:

```text
token thiếu
token sai
token hết hạn
account không Active
login sai
```

Frontend nên đưa user về Login khi session không còn hợp lệ.

## 403

Có đăng nhập nhưng không có quyền.

Ví dụ:

```text
PT gọi API chỉ dành cho HOI_VIEN
HOI_VIEN gọi API admin
```

Không nên coi 403 là lỗi đăng nhập.

## 404

Resource không tồn tại.

Ví dụ:

```text
LOP_NOT_FOUND
PT_NOT_FOUND
GOI_TAP_NOT_FOUND
```

## 409

Xung đột dữ liệu.

Ví dụ:

```text
CCCD trùng
SĐT trùng
Username trùng
PT đã có lịch
Đã check-in
```

---

# 35. FRONTEND NÊN CÓ API CLIENT RIÊNG

Nên tổ chức kiểu:

```text
src/
├── api/
│   ├── client.js
│   ├── authApi.js
│   ├── taiKhoanApi.js
│   ├── hoiVienApi.js
│   ├── ptApi.js
│   ├── phongTapApi.js
│   ├── goiTapApi.js
│   ├── dangKyGoiApi.js
│   ├── lopHocApi.js
│   ├── dangKyLopApi.js
│   ├── buoiPtApi.js
│   ├── checkInOutApi.js
│   ├── chiSoCoTheApi.js
│   └── thongKeApi.js
```

Ví dụ:

```javascript
const apiClient = axios.create({
  baseURL: "http://localhost:8080/api"
});
```

Interceptor nên tự gắn:

```text
Authorization: Bearer <token>
```

để component không phải tự làm việc này cho từng API.

---

# 36. FRONTEND ROUTE GỢI Ý

## NHAN_VIEN

```text
/admin
/admin/dashboard
/admin/tai-khoan
/admin/hoi-vien
/admin/pt
/admin/phong-tap
/admin/goi-tap
/admin/lop-hoc
/admin/dang-ky-goi
/admin/buoi-pt
/admin/check-in-out
/admin/chi-so-co-the
/admin/cau-hinh-pt
/admin/thong-ke
```

## PT

```text
/pt
/pt/dashboard
/pt/profile
/pt/classes
/pt/buoi-pt
/pt/statistics
```

## HOI_VIEN

```text
/member
/member/dashboard
/member/profile
/member/packages
/member/classes
/member/my-classes
/member/personal-training
/member/check-in
/member/body-metrics
/member/statistics
```

Đây là gợi ý frontend routing, không phải API của Backend.

---

# 37. FRONTEND KHÔNG NÊN TỰ TÍNH

Không tự coi Frontend là nguồn dữ liệu cuối cùng cho:

```text
role
trạng thái account
trạng thái gói
ngày hết hạn gói
số buổi PT còn lại
giá PT
số người đăng ký lớp
trạng thái lớp
trạng thái buổi PT
quyền sở hữu dữ liệu
điều kiện đăng ký
điều kiện hủy
```

Ví dụ:

```text
Frontend:
"button Đăng ký đang enable"

Backend:
"NO_ACTIVE_PACKAGE"
```

=> Backend mới là kết quả cuối cùng.

---

# 38. FRONTEND KHÔNG NÊN GỬI CÁC ID SELF-SERVICE KHÔNG CẦN THIẾT

Các API dạng:

```text
/cua-toi
```

được thiết kế để Backend lấy user từ JWT.

Ví dụ:

```text
GET /api/hoi-vien/cua-toi
GET /api/buoi-pt/cua-toi
GET /api/check-in-out/cua-toi
GET /api/dang-ky-goi/cua-toi
GET /api/thong-ke/hoi-vien/cua-toi
```

Không cần:

```text
maHv
```

trong URL/body nếu controller không yêu cầu.

---

# 39. FLOW HỘI VIÊN

```text
REGISTER
   |
   v
LOGIN
   |
   v
kiểm tra profile
   |
   +---- chưa có ----> Hoàn thiện profile
   |
   v
DASHBOARD
   |
   +--> Packages
   |       |
   |       +--> đăng ký gói
   |
   +--> Classes
   |       |
   |       +--> đăng ký lớp
   |       +--> hủy lớp
   |
   +--> Personal Training
   |       |
   |       +--> đặt PT
   |       +--> hủy PT
   |
   +--> Check-in/out
   |
   +--> Body Metrics
   |
   +--> Statistics
```

---

# 40. FLOW PT

```text
LOGIN
  |
  v
PT DASHBOARD
  |
  +--> Profile
  |
  +--> Classes
  |
  +--> PT Sessions
  |      |
  |      +--> Đã hoàn thành
  |      +--> Vắng mặt
  |
  +--> Statistics
```

---

# 41. FLOW NHÂN VIÊN

```text
LOGIN
  |
  v
ADMIN DASHBOARD
  |
  +--> Accounts
  +--> Members
  +--> PT
  +--> Rooms
  +--> Packages
  +--> Classes
  +--> Package registrations
  +--> PT sessions
  +--> Check-in/out
  +--> Body metrics
  +--> PT pricing
  +--> Statistics
```

---

# 42. CÁC NGUYÊN TẮC UI

## Loading state

Mọi API async nên có:

```text
loading
success
error
empty
```

Không để giao diện trắng khi đang request.

## Empty state

Ví dụ:

```text
Chưa có gói tập.
Chưa có lớp đăng ký.
Chưa có buổi PT.
Chưa có dữ liệu chỉ số.
```

## Error state

Luôn có thông báo dễ hiểu.

Không hiển thị raw stack trace.

---

# 43. CÁC NÚT NÊN DÙNG ROLE + DATA STATUS

Ví dụ đăng ký lớp:

```text
role = HOI_VIEN
AND
trangThaiLop = "Lớp đang mở"
```

=> hiển thị nút đăng ký.

Nhưng dù UI đang enable:

```text
POST /api/dang-ky-lop/cua-toi
```

vẫn có thể bị Backend từ chối.

Frontend phải xử lý response đó.

---

# 44. CÁC VẤN ĐỀ / GIỚI HẠN ĐÃ QUAN SÁT TRONG BACKEND HIỆN TẠI

Phần này rất quan trọng khi Frontend tích hợp.

## 44.1. Chưa thấy refresh-token API

Source hiện tại có login JWT và expiration, nhưng không có controller refresh-token.

=> Frontend chỉ nên triển khai access-token flow hiện tại.

---

## 44.2. CORS

Source hiện tại chưa có cấu hình CORS riêng.

Nếu Frontend chạy bằng:

```text
http://localhost:5173
```

và Backend:

```text
http://localhost:8080
```

đây là hai origin khác nhau.

Vì vậy khi chạy browser thật, nhóm cần một trong hai cách:

### Cách A
Cấu hình CORS ở Backend.

### Cách B
Dùng proxy của Vite trong môi trường development.

Frontend team nên thống nhất một cách trước khi tích hợp.

---

## 44.3. `RuntimeException` trong một số Controller

Một số helper trong:

```text
BuoiPtController
CheckInOutController
ChiSoCoTheController
```

vẫn dùng `RuntimeException` thay vì `BusinessException`.

Ví dụ:

```text
Không tìm thấy tài khoản
Chỉ hội viên mới được sử dụng API này
Không tìm thấy PT
```

GlobalExceptionHandler hiện tại không có handler riêng cho RuntimeException.

=> Trong một số trường hợp bất thường, frontend có thể nhận 500 thay vì JSON business error chuẩn.

**Frontend không nên phụ thuộc vào message của RuntimeException trong các controller này.**

---

## 44.4. Validation error và BusinessException có hai format khác nhau

Business:

```json
{
  "status": 400,
  "code": "...",
  "message": "..."
}
```

Validation:

```json
{
  "status": 400,
  "message": "Dữ liệu không hợp lệ",
  "errors": {
    "field": "..."
  }
}
```

Frontend nên có parser lỗi chung xử lý cả hai format.

---

## 44.5. Một số CRUD account hiện tại trả `null` khi ID không tồn tại

`TaiKhoanService` hiện tại có các phương thức lookup/update dùng `null` trong một số trường hợp thay vì ném `BusinessException`.

=> Frontend admin nên không giả định mọi trường hợp "không tồn tại" đều trả cùng một format 404.

Đây là điểm có thể refactor Backend sau này.

---

## 44.6. Delete API không phải trọng tâm Frontend

Các API DELETE đã tồn tại cho:

```text
tài khoản
hội viên
PT
phòng
gói
lớp
```

nhưng project hiện tại có nghiệp vụ liên kết nhiều bảng.

Frontend không nên tùy tiện expose nút Delete ở mọi nơi nếu nghiệp vụ thực tế chưa yêu cầu.

Đặc biệt phải cẩn thận với dữ liệu có quan hệ FK.

---

## 44.7. Chỉnh sửa lớp học và các BUOI_HOC đã sinh

Backend:

- tạo lớp => sinh `BUOI_HOC`;
- update lớp => sửa `LOP_HOC` nhưng source hiện tại không có bước sinh lại toàn bộ `BUOI_HOC`.

Frontend không được giả định rằng:

```text
PUT /api/lop-hoc/{id}
```

sẽ tự động tái tạo lịch các buổi học đã sinh.

Nếu sau này nghiệp vụ yêu cầu thay lịch toàn bộ, Backend cần được xử lý riêng.

---

## 44.8. Quy tắc "lớp đã diễn ra không sửa"

Source hiện tại chưa thể hiện đầy đủ một validation riêng kiểu:

```text
buổi đã diễn ra => cấm sửa lớp
```

Nếu Frontend muốn disable nút sửa theo nghiệp vụ này thì cần thống nhất thêm một contract rõ ràng với Backend.

Không nên tự coi UI disable là validation.

---

# 45. NHỮNG GÌ BACKEND ĐÃ CÓ VÀ FRONTEND CÓ THỂ DÙNG NGAY

```text
Authentication JWT                ✅
Role-based authorization          ✅
Member self-service               ✅
PT self-service                   ✅
Admin management                  ✅
Package                            ✅
Package registration               ✅
Class                              ✅
Class registration                 ✅
Personal training                  ✅
Check-in/out                       ✅
Body metrics                       ✅
Statistics                         ✅
PT pricing config                  ✅
Validation                         ✅
Business exception                 ✅
```

---

# 46. FRONTEND CHECKLIST TRƯỚC KHI CODE

## Authentication

```text
[ ] Login
[ ] Register
[ ] Store JWT
[ ] Restore session
[ ] Logout
[ ] 401 handling
```

## Role

```text
[ ] NHAN_VIEN route guard
[ ] PT route guard
[ ] HOI_VIEN route guard
[ ] Hide unauthorized menu
```

## API client

```text
[ ] Axios/fetch wrapper
[ ] Base URL
[ ] Bearer token interceptor
[ ] Error parser
[ ] Loading handling
```

## Hội viên

```text
[ ] Profile
[ ] Packages
[ ] Package registration
[ ] Classes
[ ] Class registration
[ ] PT booking
[ ] Check-in/out
[ ] Body metrics
[ ] Statistics
```

## PT

```text
[ ] Profile
[ ] Classes
[ ] PT sessions
[ ] Session status
[ ] Statistics
```

## Nhân viên

```text
[ ] Accounts
[ ] Members
[ ] PT
[ ] Rooms
[ ] Packages
[ ] Classes
[ ] Package registrations
[ ] PT sessions
[ ] Check-in/out
[ ] Body metrics
[ ] PT price
[ ] Statistics
```

---

# 47. FRONTEND BACKLOG — THỨ TỰ NÊN LÀM

## Phase 1 — nền tảng

```text
1. API client
2. Login
3. Register
4. JWT storage
5. Protected routes
6. Role routing
7. Error handling
```

## Phase 2 — Hội viên

```text
8. Profile
9. Packages
10. Classes
11. Class registration
12. PT booking
13. Check-in/out
14. Body metrics
15. Statistics
```

## Phase 3 — PT

```text
16. Profile
17. Classes
18. PT sessions
19. Statistics
```

## Phase 4 — Nhân viên

```text
20. Dashboard
21. Accounts
22. Members
23. PT
24. Rooms
25. Packages
26. Classes
27. Package registrations
28. PT sessions
29. Check-in/out
30. Body metrics
31. PT price
32. Statistics
```

---

# 48. QUY ƯỚC GIAO TIẾP GIỮA BACKEND VÀ FRONTEND

Khi Backend thêm/sửa API, phải báo cho Frontend ít nhất:

```text
1. Method
2. URL
3. Role
4. Request JSON
5. Response JSON
6. Status code
7. Error code
8. Business rule mới
```

Ví dụ:

```text
[BACKEND CHANGE]

POST /api/dang-ky-lop/cua-toi

Role:
HOI_VIEN

Request:
{
  "maLop": 1
}

Success:
200

Error:
LOP_FULL
NO_ACTIVE_PACKAGE
ALREADY_REGISTERED
```

Frontend không nên phải đọc source Backend để đoán contract mỗi lần.

---

# 49. KẾT LUẬN

Backend hiện tại đã hình thành đủ các module chính để Frontend bắt đầu tích hợp:

```text
AUTH
  ↓
ROLE
  ↓
PROFILE
  ↓
PACKAGE
  ↓
CLASS
  ↓
PT
  ↓
CHECK-IN
  ↓
BODY METRICS
  ↓
STATISTICS
```

Ba nguyên tắc quan trọng nhất dành cho Frontend:

### 1. JWT là chìa khóa phiên đăng nhập

Luôn gửi:

```http
Authorization: Bearer <token>
```

### 2. Backend là nguồn sự thật

Frontend không tự quyết định:

```text
quyền
trạng thái
giá
số lượng
ngày hết hạn
điều kiện đăng ký
```

### 3. API `/cua-toi` là self-service

Không tự gửi `maHv` hoặc `maPt` khi Backend đã xác định user từ JWT.

---

# 50. TÀI LIỆU NÀY DÙNG NHƯ THẾ NÀO?

Frontend developer có thể sử dụng file này để:

```text
- thiết kế routing;
- thiết kế sidebar/menu;
- thiết kế role guard;
- thiết kế API client;
- thiết kế form;
- thiết kế validation;
- thiết kế error handling;
- thiết kế loading/empty/error state;
- kết nối từng module với Backend.
```

Khi bắt đầu code Frontend, nên làm theo thứ tự:

```text
Auth
→ Role routing
→ API client
→ Hội viên
→ PT
→ Nhân viên
```

---

# 51. THÔNG TIN CÒN CÓ THỂ BỔ SUNG SAU

Để biến tài liệu này thành API contract "đóng băng" hoàn toàn cho team, những phần nên thống nhất tiếp theo là:

```text
1. CORS strategy
2. Chuẩn response 401/403 từ Spring Security
3. Có/không refresh token
4. API DELETE có thực sự expose trên UI không
5. Quy tắc sửa lịch lớp
6. Pagination/filter/search nếu Frontend cần
7. Chuẩn format tiền tệ và timezone hiển thị
8. Thiết kế dashboard card/chart cụ thể
```

Các điểm trên là phần tích hợp/triển khai tiếp theo; không nên để Frontend tự suy diễn.

---

# 52. TÓM TẮT NGẮN DÀNH CHO FRONTEND DEV

```text
Backend:
http://localhost:8080

API:
http://localhost:8080/api

Auth:
JWT Bearer

Roles:
NHAN_VIEN
PT
HOI_VIEN

Member:
self-service /cua-toi

Class:
GET → 3 role
POST/PUT/DELETE → NHAN_VIEN
Đăng ký → HOI_VIEN

PT:
self profile → PT
Admin PT → NHAN_VIEN

Package:
GET → NHAN_VIEN + HOI_VIEN
Manage → NHAN_VIEN
Register → HOI_VIEN

Body metrics:
NHAN_VIEN nhập
HOI_VIEN xem

Check-in:
HOI_VIEN tự check-in/out
NHAN_VIEN xem toàn hệ thống

Statistics:
NHAN_VIEN / PT / HOI_VIEN có endpoint riêng

Backend:
là nguồn sự thật cuối cùng.
```

END OF DOCUMENT
