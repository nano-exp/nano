import { computed, defineComponent, onBeforeUnmount, onMounted } from 'vue'
import { css } from '@emotion/css'
import { useAdminVvStore } from '../../store/adminVv.js'
import { NButton, NDataTable, NImage, NInput, NInputGroup, NPagination, NPopover, NSpin } from 'naive-ui'
import NewModal from './NewModal.jsx'
import DeleteModal from './DeleteModal.jsx'
import { isMobile } from '../../common/utils.js'
import withProvider from '../../common/withProvider.jsx'

const ClassName = css`
    margin: 0 auto;
    padding: 1rem;
    min-height: 100vh;
    max-width: 800px;
    box-sizing: border-box;

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
`


const AdminVv = defineComponent({
    setup() {
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
            { key: 'id', title: 'ID', },
            { key: 'name', title: 'Name', },
            {
                key: 'url',
                title: 'URL',
                render: (item) => {
                    return (
                        <NPopover trigger="hover">
                            {{
                                trigger: () => (
                                    <span>{item.url}</span>
                                ),
                                default: () => (
                                    <NImage width="150px" src={item.url} previewDisabled/>
                                ),
                            }}
                        </NPopover>
                    )
                }
            },
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

        return {
            adminVvStore,
            onClickNew,
            onClickDelete,
            dataTableColumns,
            dataTablePagination,
            onInputKeyup,
        }
    },
    render({ adminVvStore, onClickNew, onClickDelete, dataTableColumns, dataTablePagination, onInputKeyup, }) {
        const total = new Intl.NumberFormat().format(adminVvStore.totalCount)
        return (
            <div class={ClassName}>
                <div class="title">{adminVvStore.name}</div>
                <div class="search-form">
                    <NInputGroup>
                        <NInput value={adminVvStore.keyword}
                                clearable
                                onInput={(ev) => adminVvStore.keyword = ev}
                                onKeyup={onInputKeyup}/>
                        <NButton onClick={() => adminVvStore.changePage(1)} type="primary" ghost>搜索</NButton>
                    </NInputGroup>
                </div>
                <div class="table-toolbar">
                    <NButton onClick={onClickNew} secondary type="primary">新增</NButton>
                    <NButton onClick={onClickDelete} secondary>删除</NButton>
                </div>
                <div class="data-table">
                    <NSpin show={adminVvStore.loading}>
                        <NDataTable
                            key={(it) => it.id}
                            columns={dataTableColumns}
                            data={adminVvStore.list}/>
                        <div>
                            <NPagination {...dataTablePagination}>
                                {{
                                    prefix: ({ itemCount }) => (
                                        <span>共 {itemCount} 项</span>
                                    )
                                }}
                            </NPagination>
                        </div>
                    </NSpin>
                </div>
                <NewModal/>
                <DeleteModal/>
            </div>
        )
    }
})

export default withProvider(AdminVv)
