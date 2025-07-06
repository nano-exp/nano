import { defineConfig } from 'vite'
import vueJsx from '@vitejs/plugin-vue-jsx'

export default defineConfig({
    plugins: [
        vueJsx(),
    ],
    server: {
        proxy: {
            '/api': {
                target: process.env.PROXY_TARGET ?? 'http://localhost:9000',
                changeOrigin: true,
            },
        }
    }
})
