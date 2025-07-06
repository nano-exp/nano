<template>
  <div class="adminvv-root">
    <div class="title">{{ adminVvStore.name }}</div>
    <div class="search-form">
      <NInputGroup>
        <NInput v-model:value="adminVvStore.keyword"
                clearable
                @keyup="onInputKeyup"/>
        <NButton @click="() => adminVvStore.changePage(1)" type="primary" ghost>搜索</NButton>
      </NInputGroup>
    </div>
    <div class="table-toolbar">
      <NButton @click="onClickNew" secondary type="primary">新增</NButton>
      <NButton @click="onClickDelete" secondary>删除</NButton>
    </div>
    <div class="data-table">
      <NSpin :show="adminVvStore.loading">
        <NDataTable :columns="dataTableColumns" :data="adminVvStore.list" :row-key="it => it.id">
          <template #url="{ row }">
            <NPopover trigger="hover">
              <template #trigger>
                <span>{{ row.url }}</span>
              </template>
              <NImage width="150px" :src="row.url" preview-disabled />
            </NPopover>
          </template>
        </NDataTable>
        <div>
          <NPagination v-bind="dataTablePagination">
            <template #prefix="{ itemCount }">
              <span>共 {{ itemCount }} 项</span>
            </template>
          </NPagination>
        </div>
      </NSpin>
    </div>
    <NewModal />
    <DeleteModal />
  </div>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted } from 'vue'
import { useAdminVvStore } from '../../store/adminVv.js'
import { NButton, NDataTable, NImage, NInput, NInputGroup, NPagination, NPopover, NSpin } from 'naive-ui'
import NewModal from './NewModal.vue'
import DeleteModal from './DeleteModal.vue'
import { isMobile } from '../../common/utils.js'

const adminVvStore = useAdminVvStore()

function onClickNew() {
  adminVvStore.showNewModal = true
}

function onClickDelete() {
  adminVvStore.showDeleteModal = true
}

onMounted(async () => {
  adminVvStore.resurrectToken()
  adminVvStore.createGui()
  await adminVvStore.searchVvList()
})

onBeforeUnmount(() => {
  adminVvStore.destroyGui()
})

const dataTableColumns = [
  { key: 'id', title: 'ID' },
  { key: 'name', title: 'Name' },
  { key: 'url', title: 'URL' },
]

const dataTablePagination = computed(() => {
  return {
    class: 'data-table-pagination',
    pageSize: adminVvStore.pageSize,
    page: adminVvStore.pageIndex,
    pageCount: Math.ceil(adminVvStore.totalCount / adminVvStore.pageSize),
    simple: isMobile(),
    itemCount: adminVvStore.totalCount,
    showQuickJumper: true,
    'onUpdate:page': async (ev) => {
      await adminVvStore.changePage(ev)
    },
  }
})

async function onInputKeyup(ev) {
  if (ev.key === 'Enter') {
    await adminVvStore.changePage(1)
  }
}
</script>

<style scoped>
.adminvv-root {
  margin: 0 auto;
  padding: 1rem;
  min-height: 100vh;
  max-width: 800px;
  box-sizing: border-box;
}
.title {
  font-size: 20px;
}
.search-form {
  margin-top: 1rem;
}
.table-toolbar {
  display: flex;
  gap: .5rem;
  margin-top: 1rem;
}
.data-table {
  margin-top: 1rem;
}
.data-table-pagination {
  margin-top: .5rem;
  justify-content: flex-end;
}
</style> 