import { computed, defineComponent, h, onBeforeUnmount, onMounted, ref, shallowRef } from 'vue'
import { css } from '@emotion/css'
import { useAdminVvStore } from '../../store/adminVv.js'
import { NButton, NDataTable, NPagination, NSpin, useModal } from 'naive-ui'
import NewModal from './NewModal.jsx'

const ClassName = css`
    margin: 0 auto;
    padding: 1rem;
    min-height: 100vh;
    max-width: 800px;
    box-sizing: border-box;

    .title {
        font-size: 20px;
    }

    .table-toolbar {
        margin-top: 1rem;
    }

    .data-table {
        margin-top: 1rem;
    }

    .data-table-pagination {
        margin-top: .5rem;
        justify-content: flex-end;
    }
`


export default defineComponent({
    setup() {
        const adminVvStore = useAdminVvStore()

        function onClickNew() {
            adminVvStore.showNewModal = true
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
            { key: 'id', title: 'ID', },
            { key: 'name', title: 'Name', },
            { key: 'url', title: 'URL', },
        ]

        const dataTablePagination = computed(() => {
            return {
                class: 'data-table-pagination',
                pageSize: adminVvStore.pageSize,
                page: adminVvStore.pageIndex,
                pageCount: Math.ceil(adminVvStore.totalCount / adminVvStore.pageSize),
                showQuickJumper: true,
                'onUpdate:page': async (ev) => {
                    await adminVvStore.changePage(ev)
                },
            }
        })

        return {
            adminVvStore,
            onClickNew,
            dataTableColumns,
            dataTablePagination,
        }
    },
    render({ adminVvStore, onClickNew, dataTableColumns, dataTablePagination }) {
        return (
            <div class={ClassName}>
                <div class="title">{adminVvStore.name}</div>
                <div class="table-toolbar">
                    <NButton onClick={onClickNew} type="primary">New</NButton>
                </div>
                <div class="data-table">
                    <NSpin show={adminVvStore.loading}>
                        <NDataTable
                            key={(it) => it.id}
                            columns={dataTableColumns}
                            data={adminVvStore.list}/>
                        <NPagination {...dataTablePagination}/>
                    </NSpin>
                </div>
                <NewModal/>
            </div>
        )
    }
})
