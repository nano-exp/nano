import { css, onMounted, reactive, computed } from '../../deps/deps.js'
import Button from '../../common/Button.js'
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
    template: `
      <div :class="AppClassName">
        <div>Ack Message</div>
        <div class="data">{{ state.message }}</div>
        <div class="btn-ack">
          <Button v-if="showAckButton" :on-click="fetchAckMessage">
            Ack Message
          </Button>
        </div>
      </div>
    `,
    components: { Button, },
    setup(props) {
        const state = reactive({
            message: '',
            id: new URLSearchParams(location.search).get('id'),
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

        return {
            state,
            fetchAckMessage,
            AppClassName,
            showAckButton,
        }
    }
}