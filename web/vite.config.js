import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': {
        target: process.env.PROXY_TARGET ?? 'http://localhost:9000',
        changeOrigin: true,
      },
    },
  },
})
