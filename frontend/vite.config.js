import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
// Proxy /api -> Backend, nên KHÔNG cần cấu hình CORS bên Spring Boot khi chạy dev.
export default defineConfig({plugins:[react()],server:{port:5173,proxy:{'/api':{target:'http://localhost:8080',changeOrigin:true}}}})
