import { onMounted, reactive, computed } from 'vue'
import { useRoute } from 'vue-router'
import { css } from '@emotion/css'
import Button from '../../common/Button.jsx'
import getMessage from '../apis/getMessage.js'
import applyAckMessage from '../apis/applyAckMessage.js'

const AppClassName = css`
    .data {
        white-space: pre;
    }

    .btn-ack {
        margin-top: 1rem;
    }
`
export default {
    components: { Button, },
    setup(props) {
        const route = useRoute()

        const state = reactive({
            message: '',
            id: route.query.id,
        })

        onMounted(async () => {
            if (state.id) {
                state.message = await getMessage(state.id)
            }
        })

        async function fetchAckMessage() {
            await applyAckMessage(state.id)
            state.message = await getMessage(state.id)
        }

        const showAckButton = computed(() => {
            return state.id && state.message && !state.message.ackTime
        })

        return () => {
            const m = state.message ? JSON.stringify(state.message, null, 2) : ''
            return (
                <div class={AppClassName}>
                    <div>Bark Message</div>
                    <div class="data">{m}</div>
                    {showAckButton.value && <div class="btn-ack">
                        <Button onClick={fetchAckMessage}>
                            Ack Message
                        </Button>
                    </div>}
                </div>
            )
        }
    }
}