import { onMounted, computed, ref, defineComponent } from 'vue'
import { useRoute } from 'vue-router'
import { css } from '@emotion/css'
import { NH4, NButton, NCode } from 'naive-ui'
import getMessage from '../apis/getMessage.js'
import applyAckMessage from '../apis/applyAckMessage.js'
import { getCSTString } from '../../common/utils.js'

const AppClassName = css`
    padding: 1rem;

    .data {
        white-space: pre;

        div {
            white-space: pre-wrap;
        }

        .message-payload {
            overflow: auto;
            border: 1px solid #111;
            box-sizing: border-box;
            padding: .125rem;
        }
    }

    .btn-ack {
        margin-top: 1rem;
    }
`

export default defineComponent({
    setup(props) {
        const route = useRoute()
        const id = route.query.id
        const message = ref(null)
        const ackLoading = ref(false)

        onMounted(async () => {
            if (id) {
                message.value = await getMessage(id)
            }
        })

        async function fetchAckMessage() {
            try {
                ackLoading.value = true
                await applyAckMessage(id)
                message.value = await getMessage(id)
            } finally {
                ackLoading.value = false
            }
        }

        const showAckButton = computed(() => {
            return id && message.value && !message.value.ackTime
        })

        const messagePayloadJson = computed(() => {
            if (message.value) {
                try {
                    const po = JSON.parse(message.value.payload)
                    return JSON.stringify(po, null, 2)
                } catch (err) {
                    return message.value.payload
                }
            }
            return ''
        })

        return {
            message,
            messagePayloadJson,
            showAckButton,
            fetchAckMessage,
            ackLoading,
        }
    },
    render(context) {
        const {
            showAckButton,
            fetchAckMessage,
            message,
            messagePayloadJson,
            ackLoading,
        } = context

        const formattedCreateTime = getCSTString(new Date(message?.createTime))
        const formattedAckTime = message?.ackTime ? getCSTString(new Date(message?.ackTime)) : 'Not acked'

        return (
            <div class={AppClassName}>
                <NH4>Bark Message</NH4>
                <div class="data">
                    {message && (
                        <>
                            <div><strong>ID:</strong> {message.id}</div>
                            <div><strong>Domain:</strong> {message.domain || '-'}</div>
                            <div><strong>Create Time:</strong> {formattedCreateTime}</div>
                            <div><strong>Ack Time:</strong> {formattedAckTime}</div>
                            <div><strong>Comment:</strong></div>
                            <div>{message.comment}</div>
                            <div><strong>Payload:</strong></div>
                            <div class="message-payload"><NCode code={messagePayloadJson} language="json"/></div>
                        </>
                    )}
                </div>
                {showAckButton && <div class="btn-ack">
                    <NButton loading={ackLoading} onClick={fetchAckMessage}>
                        Ack Message
                    </NButton>
                </div>}
            </div>
        )
    },
})