# FitCore Frontend (React + Vite)
1. Cài đặt: `npm install`
2. Chạy Backend Spring Boot ở http://localhost:8080
3. Chạy Frontend: `npm run dev` → http://localhost:5173
Vite proxy `/api` → `localhost:8080` nên không cần cấu hình CORS khi dev.
Đổi địa chỉ API (production): tạo `.env` với `VITE_API_BASE=https://domain/api`.
Cấu trúc: src/api/client.js (fetch + JWT + parse lỗi), src/auth.jsx, src/components/ui.jsx, src/pages/{Auth,Member,Pt,Admin}.jsx
