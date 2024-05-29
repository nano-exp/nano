import { defineComponent } from 'vue'
import { useAppStore } from '../store/app.js'

export default defineComponent({
    setup() {
        const appStore = useAppStore()
        return {
            appStore,
        }
    },
    render({ appStore }) {
        const { name } = appStore
        return (
            <div>{name}</div>
        )
    },
})