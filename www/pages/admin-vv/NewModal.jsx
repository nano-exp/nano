import { defineComponent, ref } from 'vue'
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

        return {
            adminVvStore,
            onClickSave,
            saveLoading,
            onSelectFile,
            onCleanData,
        }
    },
    render({ adminVvStore, onClickSave, saveLoading, onSelectFile, onCleanData }) {
        return (
            <NModal show={adminVvStore.showNewModal}
                    onUpdate:show={(ev) => adminVvStore.showNewModal = ev}
                    preset="card"
                    title={'New'}
                    style={{ width: '600px' }}
                    bordered={false}
                    onAfterLeave={onCleanData}
                    segmented="segmented">
                {{
                    default: () => (
                        <div style="display: flex;flex-direction: column; gap: 8px;">
                            <div><strong>File</strong></div>
                            <NUpload defaultUpload={false}
                                     max={1}
                                     onChange={onSelectFile}>
                                <NUploadDragger>
                                    <div style="min-height: 50px;display: grid;place-items: center;">
                                        Click or drag a file to this area to upload
                                    </div>
                                </NUploadDragger>
                            </NUpload>
                            <div><strong>Filename</strong></div>
                            <NInput value={adminVvStore.newData.filename}
                                    clearable
                                    onInput={(ev) => adminVvStore.newData.filename = ev}
                                    placeholder="Enter filename"/>
                        </div>
                    ),
                    footer: () => (
                        <div style="text-align: right;">
                            <NButton loading={saveLoading}
                                     onClick={onClickSave}
                                     type="primary">Save</NButton>
                        </div>
                    )
                }}
            </NModal>
        )
    }
})
