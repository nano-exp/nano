import { css, ref } from '../deps/deps.js'

const ButtonClassName = css`
    font-size: 1rem;
    height: 32px;
    padding: 0 .5rem;
`
export default {
    template: `
      <button :class="ButtonClassName" @click="onClickButton" :disabled="loading">
        <slot></slot>
      </button>
    `,
    props: {
        onClick: Function,
    },
    setup(props) {
        const loading = ref(false)

        async function onClickButton() {
            try {
                loading.value = true
                await props.onClick?.()
            } finally {
                loading.value = false
            }
        }

        return {
            onClickButton,
            ButtonClassName,
            loading,
        }
    },
}