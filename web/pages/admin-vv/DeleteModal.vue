<template>
  <NModal :show="adminVvStore.showDeleteModal"
          @update:show="ev => adminVvStore.showDeleteModal = ev"
          preset="card"
          title="删除"
          style="width: 400px;"
          :bordered="false"
          @after-leave="onCleanData"
          segmented="segmented">
    <template #default>
      <div style="display: flex;flex-direction: column; gap: 8px;">
        <div><strong>ID</strong></div>
        <NInput v-model:value="adminVvStore.toDeleteId"
                clearable
                placeholder="输入ID"/>
      </div>
    </template>
    <template #footer>
      <div style="text-align: right;">
        <NButton :loading="deleteLoading"
                 size="large"
                 :disabled="!enteredCorrectly"
                 @click="onClickDelete"
                 type="error">保存</NButton>
      </div>
    </template>
  </NModal>
</template>

<script setup>
import { ref, computed } from 'vue'
import { NButton, NInput, NModal, useMessage } from 'naive-ui'
import { useAdminVvStore } from '../../store/adminVv.js'

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

function onCleanData() {
  adminVvStore.toDeleteId = null
}

const enteredCorrectly = computed(() => {
  return !!adminVvStore.toDeleteId
})
</script> 