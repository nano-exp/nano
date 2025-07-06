<template>
  <NModal
    :show="adminVvStore.showNewModal"
    @update:show="(ev) => (adminVvStore.showNewModal = ev)"
    preset="card"
    title="新增"
    style="width: 600px"
    :bordered="false"
    @after-leave="onCleanData"
    segmented="segmented"
  >
    <template #default>
      <div style="display: flex; flex-direction: column; gap: 8px">
        <div><strong>文件</strong></div>
        <NUpload :default-upload="false" :max="1" @change="onSelectFile">
          <NUploadDragger>
            <div style="min-height: 50px; display: grid; place-items: center">单击或将文件拖到此区域进行上传</div>
          </NUploadDragger>
        </NUpload>
        <div><strong>文件名</strong></div>
        <NInput v-model:value="adminVvStore.newData.filename" clearable placeholder="输入文件名" />
      </div>
    </template>
    <template #footer>
      <div style="text-align: right">
        <NButton :loading="saveLoading" size="large" :disabled="!enteredCorrectly" @click="onClickSave" type="primary"
          >保存</NButton
        >
      </div>
    </template>
  </NModal>
</template>

<script setup>
import { ref, computed } from 'vue'
import { NButton, NInput, NModal, NUpload, NUploadDragger, useMessage } from 'naive-ui'
import { useAdminVvStore } from '../../store/adminVv.js'

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

function onCleanData() {
  adminVvStore.newData = {
    filename: '',
    getFile: null,
  }
}

const enteredCorrectly = computed(() => {
  return adminVvStore.newData.filename && adminVvStore.newData.getFile
})
</script>
