import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'

// Local development: /api is proxied to Spring Boot.
// host + allowedHosts make the Vite dev server reachable through a Cloudflare Quick Tunnel.
// For a production deployment, prefer Cloudflare Pages/Workers or a named Tunnel with a locked hostname.
export default defineConfig({
  plugins:[react()],
  server:{
    host:'0.0.0.0',
    port:5173,
    allowedHosts:true,
    proxy:{
      '/api':{target:'http://localhost:8080',changeOrigin:true}
    }
  },
  preview:{
    host:'0.0.0.0',
    port:4173,
    allowedHosts:true
  }
})
