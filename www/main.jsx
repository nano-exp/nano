import { createApp, } from 'vue'
import { RouterView } from 'vue-router'
import router from './router.jsx'

function main() {
    const app = createApp({
        render() {
            return (
                <div>
                    <RouterView/>
                </div>
            )
        },
    })
    app.use(router)
    app.mount('#app')
}

main()