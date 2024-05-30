import { computed, defineComponent, ref } from 'vue'
import { NButton, NInput, NModal, NUpload, NUploadDragger, useMessage } from 'naive-ui'
import { useAdminVvStore } from '../../store/adminVv.js'

export default defineComponent({
    setup() {
        const message = useMessage()
        const adminVvStore = useAdminVvStore()
        const deleteLoading = ref(false)

        async function onClickDelete() {
            try {
                deleteLoading.value = true
                await adminVvStore.deleteVv()
                message.success('删除成功')
            } catch (err) {
                message.error(err.message)
            } finally {
                deleteLoading.value = false
            }
        }

        function onCleanData(ev) {
            adminVvStore.toDeleteId = null
        }

        const enteredCorrectly = computed(() => {
            return !!adminVvStore.toDeleteId
        })

        return {
            adminVvStore,
            onClickDelete,
            deleteLoading,
            onCleanData,
            enteredCorrectly,
        }
    },
    render({ adminVvStore, onClickDelete, deleteLoading, onCleanData, enteredCorrectly }) {
        return (
            <NModal show={adminVvStore.showDeleteModal}
                    onUpdate:show={(ev) => adminVvStore.showDeleteModal = ev}
                    preset="card"
                    title={'删除'}
                    style={{ width: '400px' }}
                    bordered={false}
                    onAfterLeave={onCleanData}
                    segmented="segmented">
                {{
                    default: () => (
                        <div style="display: flex;flex-direction: column; gap: 8px;">
                            <div><strong>ID</strong></div>
                            <NInput value={adminVvStore.toDeleteId}
                                    clearable
                                    onInput={(ev) => adminVvStore.toDeleteId = ev}
                                    placeholder="输入ID"/>
                        </div>
                    ),
                    footer: () => (
                        <div style="text-align: right;">
                            <NButton loading={deleteLoading}
                                     size="large"
                                     disabled={!enteredCorrectly}
                                     onClick={onClickDelete}
                                     type="error">保存</NButton>
                        </div>
                    )
                }}
            </NModal>
        )
    }
})
