import { defineComponent } from 'vue'
import { useAppStore } from '../store/app.js'
import { css } from '@emotion/css'

const ClassName = css`
    .title {
        position: relative;
        text-align: center;
        top: 25vh;
        font-weight: 600;
        letter-spacing: -0.5px;
        font-size: 72px;
    }

    .title > span {
        font-size: 72px;
        background: linear-gradient(90deg, #f87171, #4ade80, #60a5fa);
        -webkit-background-clip: text;
        background-clip: text;
        color: transparent;
    }

    .title > a {
        text-decoration: none;
    }

`

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
            <div class={ClassName}>
                <div class="title">
                    <span>{name}</span> <a href="https://github.com/nano-exp" target="_blank">🪄</a>
                </div>
            </div>
        )
    },
})
