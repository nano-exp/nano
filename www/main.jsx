import { createApp, } from 'vue'
import router from './router.jsx'
import App from './App.jsx'

function main() {
    const app = createApp(App)
    app.use(router)
    app.mount('#app')
}

main()