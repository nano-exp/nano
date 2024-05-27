import { defineConfig } from 'vite'
import vueJsx from '@vitejs/plugin-vue-jsx'

export default defineConfig({
    root: 'www',
    plugins: [
        vueJsx(),
    ],
    server: {
        proxy: {
            '/api': {
                target: 'http://localhost:9000',
                changeOrigin: true,
            },
        }
    }
})
