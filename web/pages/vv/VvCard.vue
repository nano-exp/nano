<template>
  <NListItem :key="item.id">
    <div class="image-item">
      <NImage width="100%" height="400px" object-fit="cover" :show-toolbar="false" :src="item.url" />
      <div class="item-title">{{ item.name }}</div>
    </div>
  </NListItem>
</template>

<script setup>
import { NImage, NListItem } from 'naive-ui'
import { VVItem } from '#@/pages/vv-model.js'

defineProps({
  item: {
    type: VVItem,
    required: true,
  },
})
</script>

<style scoped>
.image-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.item-title {
  text-align: center;
  margin-top: 0.5rem;
  font-size: 16px;
}
</style>
