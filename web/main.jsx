import { createApp, } from 'vue'
import router from './router.jsx'
import App from './App.jsx'
import { createPinia } from 'pinia'

function main() {
    const pinia = createPinia()
    const app = createApp(App)
    app.use(router)
    app.use(pinia)
    app.mount('#app')
}

main()
