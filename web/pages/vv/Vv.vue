<template>
  <div class="vv-root">
    <div class="title">{{ vvStore.name }}</div>
    <div class="search-form">
      <NInputGroup>
        <NInput v-model:value="vvStore.keyword" clearable @input="onInputKeyword" @keyup="onInputKeyup" />
        <NButton v-if="vvStore.keyword" @click="vvStore.onSearchVv" type="primary" ghost> 搜索结果 </NButton>
        <NButton v-else @click="vvStore.randomVv" type="primary" ghost> 手气不错 </NButton>
      </NInputGroup>
    </div>
    <div class="image-list">
      <NSpin :show="vvStore.loading">
        <template v-if="vvStore.list.length > 0">
          <NList hoverable>
            <VvCard v-for="it in vvStore.list" :key="it.id" :item="it" />
          </NList>
          <div v-if="vvStore.showLoadMore" class="load-more">
            <NButton @click="() => vvStore.onSearchVv(true)">显示更多</NButton>
          </div>
        </template>
        <template v-else>
          <div class="empty">
            <NEmpty description="空" size="large" />
          </div>
        </template>
      </NSpin>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { NInputGroup, NInput, NButton, NList, NSpin, NEmpty } from 'naive-ui'
import { useVvStore } from '../../store/vv.js'
import VvCard from './VvCard.vue'
import { useRoute, useRouter } from 'vue-router'

const vvStore = useVvStore()
const router = useRouter()
const route = useRoute()

async function onInputKeyword(ev) {
  vvStore.keyword = ev
  await router.replace({ query: { q: ev } })
}

async function onInputKeyup(ev) {
  if (ev.key === 'Enter') {
    if (vvStore.keyword) {
      await vvStore.onSearchVv()
    } else {
      await vvStore.randomVv()
    }
  }
}

onMounted(async () => {
  if (route.query.q) {
    vvStore.keyword = route.query.q
    await vvStore.onSearchVv()
  } else {
    await vvStore.randomVv()
  }
})
</script>

<style scoped>
.vv-root {
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
  margin-top: 0.5rem;
}
.image-list {
  margin-top: 16px;
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
</style>
