<template>
  <div class="nano-root">
    <div class="title">
      <span>{{ appStore.name }}</span>
      <a href="https://github.com/nano-exp" target="_blank">🪄</a>
    </div>
  </div>
</template>

<script setup>
import { useAppStore } from '../store/app.js'
const appStore = useAppStore()
</script>

<style scoped>
.nano-root {
}
.title {
  position: relative;
  text-align: center;
  top: 25vh;
  font-weight: 600;
  letter-spacing: -0.5px;
  font-size: 72px;
}
.title > span {
  font-size: 72px;
  background: linear-gradient(90deg, #f87171, #4ade80, #60a5fa);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}
.title > a {
  text-decoration: none;
}
</style>
