import { ref } from 'vue'
import { css } from '@emotion/css'

const ButtonClassName = css`
    font-size: 1rem;
    height: 32px;
    padding: 0 .5rem;
`
export default {
    props: {
        onClick: Function,
    },
    setup(props, { slots }) {
        const loading = ref(false)

        async function onClickButton() {
            try {
                loading.value = true
                await props.onClick?.()
            } finally {
                loading.value = false
            }
        }

        console.log('slots', slots)

        return () => {
            return (
                <button class={ButtonClassName} onClick={onClickButton} disabled={loading.value}>
                    {slots.default()}
                </button>
            )
        }
    },
}