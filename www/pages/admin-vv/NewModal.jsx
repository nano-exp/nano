import { computed, defineComponent, ref } from 'vue'
import { NButton, NInput, NModal, NUpload, NUploadDragger, useMessage } from 'naive-ui'
import { useAdminVvStore } from '../../store/adminVv.js'

export default defineComponent({
    setup() {
        const message = useMessage()
        const adminVvStore = useAdminVvStore()
        const saveLoading = ref(false)

        async function onClickSave() {
            try {
                saveLoading.value = true
                await adminVvStore.saveNewData()
                message.success('新增成功')
            } catch (err) {
                message.error(err.message)
            } finally {
                saveLoading.value = false
            }
        }

        function onSelectFile(ev) {
            const file = ev.fileList[0]
            if (!adminVvStore.newData.filename) {
                adminVvStore.newData.filename = file.name
            }
            adminVvStore.newData.getFile = () => file.file
        }

        function onCleanData(ev) {
            adminVvStore.newData = {
                filename: '',
                getFile: null,
            }
        }

        const enteredCorrectly = computed(() => {
            return adminVvStore.newData.filename && adminVvStore.newData.getFile
        })

        return {
            adminVvStore,
            onClickSave,
            saveLoading,
            onSelectFile,
            onCleanData,
            enteredCorrectly,
        }
    },
    render({ adminVvStore, onClickSave, saveLoading, onSelectFile, onCleanData, enteredCorrectly }) {
        return (
            <NModal show={adminVvStore.showNewModal}
                    onUpdate:show={(ev) => adminVvStore.showNewModal = ev}
                    preset="card"
                    title={'新增'}
                    style={{ width: '600px' }}
                    bordered={false}
                    onAfterLeave={onCleanData}
                    segmented="segmented">
                {{
                    default: () => (
                        <div style="display: flex;flex-direction: column; gap: 8px;">
                            <div><strong>文件</strong></div>
                            <NUpload defaultUpload={false}
                                     max={1}
                                     onChange={onSelectFile}>
                                <NUploadDragger>
                                    <div style="min-height: 50px;display: grid;place-items: center;">
                                        单击或将文件拖到此区域进行上传
                                    </div>
                                </NUploadDragger>
                            </NUpload>
                            <div><strong>文件名</strong></div>
                            <NInput value={adminVvStore.newData.filename}
                                    clearable
                                    onInput={(ev) => adminVvStore.newData.filename = ev}
                                    placeholder="输入文件名"/>
                        </div>
                    ),
                    footer: () => (
                        <div style="text-align: right;">
                            <NButton loading={saveLoading}
                                     size="large"
                                     disabled={!enteredCorrectly}
                                     onClick={onClickSave}
                                     type="primary">保存</NButton>
                        </div>
                    )
                }}
            </NModal>
        )
    }
})
