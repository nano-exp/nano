<template>
  <NListItem :key="item.id">
    <div class="image-item">
      <NImage width="100%"
              height="400px"
              object-fit="cover"
              :show-toolbar="false"
              :src="item.url"/>
      <div class="item-title">{{ item.name }}</div>
    </div>
  </NListItem>
</template>

<script setup>
import { NImage, NListItem } from 'naive-ui'

defineProps({
  item: {
    type: Object,
    required: true
  }
})
</script> 