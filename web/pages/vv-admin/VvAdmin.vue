<template>
  <div class="admin-vv-root">
    <div class="title">{{ vvAdminStore.name }}</div>
    <div class="search-form">
      <NInputGroup>
        <NInput v-model:value="vvAdminStore.keyword" clearable @keyup="onInputKeyup" />
        <NButton @click="() => vvAdminStore.changePage(1)" type="primary" ghost>搜索</NButton>
      </NInputGroup>
    </div>
    <div class="table-toolbar">
      <NButton @click="onClickNew" secondary type="primary">新增</NButton>
      <NButton @click="onClickDelete" secondary>删除</NButton>
    </div>
    <div class="data-table">
      <NSpin :show="vvAdminStore.loading">
        <NDataTable :columns="dataTableColumns" :data="vvAdminStore.list" :row-key="(it) => it.id" />
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

<script setup lang="jsx">
import { computed, onBeforeUnmount, onMounted } from 'vue'
import { NButton, NDataTable, NImage, NInput, NInputGroup, NPagination, NPopover, NSpin } from 'naive-ui'
import { useVvAdminStore } from '#@/store/vv-admin.js'
import NewModal from '#@/pages/vv-admin/NewModal.vue'
import DeleteModal from '#@/pages/vv-admin/DeleteModal.vue'
import { isMobile } from '#@/common/utils.js'

const vvAdminStore = useVvAdminStore()

function onClickNew() {
  vvAdminStore.showNewModal = true
}

function onClickDelete() {
  vvAdminStore.showDeleteModal = true
}

onMounted(async () => {
  vvAdminStore.resurrectToken()
  vvAdminStore.createGui()
  await vvAdminStore.searchVvList()
})

onBeforeUnmount(() => {
  vvAdminStore.destroyGui()
})

const dataTableColumns = [
  {
    key: 'id',
    title: 'ID',
  },
  {
    key: 'name',
    title: 'Name',
  },
  {
    key: 'url',
    title: 'URL',
    render({ url }) {
      return (
        <NPopover trigger="hover">
          {{
            trigger: () => url,
            default: () => <NImage width="150px" src={url} preview-disabled />,
          }}
        </NPopover>
      )
    },
  },
]

const dataTablePagination = computed(() => {
  return {
    class: 'data-table-pagination',
    pageSize: vvAdminStore.pageSize,
    page: vvAdminStore.pageIndex,
    pageCount: Math.ceil(vvAdminStore.totalCount / vvAdminStore.pageSize),
    simple: isMobile(),
    itemCount: vvAdminStore.totalCount,
    showQuickJumper: true,
    'onUpdate:page': async (ev) => {
      await vvAdminStore.changePage(ev)
    },
  }
})

async function onInputKeyup(ev) {
  if (ev.key === 'Enter') {
    await vvAdminStore.changePage(1)
  }
}
</script>

<style scoped>
.admin-vv-root {
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
  gap: 0.5rem;
  margin-top: 1rem;
}

.data-table {
  margin-top: 1rem;
}

.data-table-pagination {
  margin-top: 0.5rem;
  justify-content: flex-end;
}
</style>
