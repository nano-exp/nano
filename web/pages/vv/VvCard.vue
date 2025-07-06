<template>
  <NListItem :key="props.item.id">
    <div class="image-item">
      <NImage width="100%"
              height="400px"
              object-fit="cover"
              :show-toolbar="false"
              :src="props.item.url"/>
      <div class="item-title">{{ props.item.name }}</div>
    </div>
  </NListItem>
</template>

<script setup>
const props = defineProps({
  item: {
    type: Object,
    required: true
  }
})
</script> 