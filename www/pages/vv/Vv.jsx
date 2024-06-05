import { defineComponent, onMounted } from 'vue'
import { NInputGroup, NInput, NButton, NList, NSpin, NEmpty } from 'naive-ui'
import { css } from '@emotion/css'
import { useVvStore } from '../../store/vv.js'
import VvCard from './VvCard.jsx'
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
        margin-top: .5rem;
    }

    .image-list {
        margin-top: 16px;

        .image-item {
            display: flex;
            flex-direction: column;
            align-items: center;

            .item-title {
                text-align: center;
                margin-top: .5rem;
                font-size: 16px;
            }
        }

        .load-more {
            margin-top: 8px;
            text-align: center;
        }

        .empty {
            min-height: 200px;
            display: grid;
            place-items: center;
        }
    }

`

const Vv = defineComponent({
    setup() {
        const vvStore = useVvStore()

        onMounted(async () => {
            await vvStore.randomVv()
        })

        async function onInputKeyup(ev) {
            if (ev.key === 'Enter') {
                await vvStore.onSearchVv()
            }
        }

        return {
            vvStore,
            onInputKeyup,
        }
    },
    render({ vvStore, onInputKeyup }) {
        return (
            <div class={ClassName}>
                <div class="title">{vvStore.name}</div>
                <div class="search-form">
                    <NInputGroup>
                        <NInput value={vvStore.keyword}
                                onInput={(ev) => vvStore.keyword = ev}
                                clearable
                                onKeyup={onInputKeyup}/>
                        {vvStore.keyword ? (
                            <NButton onClick={() => vvStore.onSearchVv()} type="primary" ghost>
                                搜索结果
                            </NButton>
                        ) : (
                            <NButton onClick={() => vvStore.randomVv()} type="primary" ghost>
                                手气不错
                            </NButton>
                        )}
                    </NInputGroup>
                </div>
                <div class="image-list">
                    <NSpin show={vvStore.loading}>
                        {vvStore.list.length > 0 ? (
                            <>
                                <NList hoverable>
                                    {vvStore.list.map((it) => (<VvCard key={it.id} item={it}/>))}
                                </NList>
                                {vvStore.showLoadMore && (
                                    <div class="load-more">
                                        <NButton onClick={() => vvStore.onSearchVv(true)}>
                                            显示更多
                                        </NButton>
                                    </div>
                                )}
                            </>
                        ) : (
                            <div class="empty">
                                <NEmpty description="空" size="large"/>
                            </div>
                        )}
                    </NSpin>
                </div>
            </div>
        )
    },
})

export default withProvider(Vv)
