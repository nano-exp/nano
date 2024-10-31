import { defineComponent } from 'vue'
import { NImage, NListItem } from 'naive-ui'

export default defineComponent({
    props: {
        item: {
            type: Object,
        },
    },
    render({ item }) {
        return (
            <NListItem key={item.id}>
                <div class="image-item">
                    <NImage width="100%"
                            height="400px"
                            objectFit="cover"
                            showToolbar={false}
                            src={item.url}/>
                    <div class="item-title">{item.name}</div>
                </div>
            </NListItem>
        )
    }
})
