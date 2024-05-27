import { defineComponent } from 'vue'
import { NInputGroup, NInput, NButton, NList, NListItem, NImage, NSpin, NEmpty } from 'naive-ui'
import { css } from '@emotion/css'
import { useVvStore } from './store/vv.js'

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
            margin: 0 auto;
            max-width: 400px;

            .item-title {
                text-align: center;
                font-size: 16px;
            }
        }

        .empty {
            min-height: 200px;
            display: grid;
            place-items: center;
        }
    }

`

export default defineComponent({
    setup() {
        const vvStore = useVvStore()
        return {
            vvStore,
            ClassName,
        }
    },
    render({ vvStore, ClassName }) {
        const onInputKeyup = (ev) => {
            if (ev.key === 'Enter') {
                vvStore.onSearchVv()
            }
        }
        return (
            <div class={ClassName}>
                <div class="title">{vvStore.name}</div>
                <div class="search-form">
                    <NInputGroup>
                        <NInput value={vvStore.keyword} onInput={(ev) => vvStore.keyword = ev}
                                onKeyup={onInputKeyup}/>
                        <NButton onClick={vvStore.onSearchVv} type="primary" ghost>搜索</NButton>
                    </NInputGroup>
                </div>
                <div class="image-list">
                    <NSpin show={vvStore.loading}>
                        {vvStore.list.length > 0 ? (
                            <NList hoverable>
                                {vvStore.list.map((it) => (
                                    <NListItem key={it.id}>
                                        <div class="image-item">
                                            <NImage width="100%" src={it.url}/>
                                            <div class="item-title">{it.name}</div>
                                        </div>
                                    </NListItem>
                                ))}
                            </NList>
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
