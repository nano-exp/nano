import { defineComponent } from 'vue'
import { css } from '@emotion/css'

const ClassName = css`
    min-height: 100vh;
    display: grid;
    place-items: center;
    font-size: 4rem;
    font-weight: bold;
`

const emoji = ['😭', '🤔', '🤕', '😫', '😵', '🙏', '🫣', '🤪', '👀']

export default defineComponent({
    render() {
        const emo = emoji[emoji.length * Math.random() | 0]
        return (
            <div class={ClassName}>
                4️⃣0️⃣4️⃣
                {emo}
            </div>
        )
    },
})
