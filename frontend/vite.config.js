import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Proxy /api -> Backend
// Khi chạy dev, Vite chuyển các request /api sang Spring Boot :8080.
export default defineConfig({
  plugins: [react()],

  server: {
    port: 5173,

    // Cho phép truy cập Vite thông qua Cloudflare Quick Tunnel
    allowedHosts: [
      'cameron-glenn-sat-cash.trycloudflare.com'
    ],

    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})