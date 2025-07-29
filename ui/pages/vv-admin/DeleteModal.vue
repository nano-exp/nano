<template>
  <NModal
    :show="vvAdminStore.showDeleteModal"
    @update:show="(ev) => (vvAdminStore.showDeleteModal = ev)"
    preset="card"
    title="删除"
    style="width: 400px"
    :bordered="false"
    @after-leave="onCleanData"
    segmented="segmented"
  >
    <template #default>
      <div style="display: flex; flex-direction: column; gap: 8px">
        <div><strong>ID</strong></div>
        <NInput v-model:value="vvAdminStore.toDeleteId" clearable placeholder="输入ID" />
      </div>
    </template>
    <template #footer>
      <div style="text-align: right">
        <NButton :loading="deleteLoading" size="large" :disabled="!enteredCorrectly" @click="onClickDelete" type="error"
          >保存</NButton
        >
      </div>
    </template>
  </NModal>
</template>

<script setup>
import { ref, computed } from 'vue'
import { NButton, NInput, NModal, useMessage } from 'naive-ui'
import { useVvAdminStore } from '#@/store/vv-admin.js'

const message = useMessage()
const vvAdminStore = useVvAdminStore()
const deleteLoading = ref(false)

async function onClickDelete() {
  try {
    deleteLoading.value = true
    await vvAdminStore.deleteVv()
    message.success('删除成功')
  } catch (err) {
    message.error(err.message)
  } finally {
    deleteLoading.value = false
  }
}

function onCleanData() {
  vvAdminStore.toDeleteId = null
}

const enteredCorrectly = computed(() => {
  return !!vvAdminStore.toDeleteId
})
</script>
