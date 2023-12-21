import { createApp } from '../../deps/deps.js'
import App from './App.js'

function main() {
    const app = createApp(App)
    app.mount('#app')
}

main()