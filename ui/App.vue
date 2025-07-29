<template>
  <WithProvider>
    <RouterView />
  </WithProvider>
</template>

<script setup>
import { RouterView } from 'vue-router'
import WithProvider from '#@/common/WithProvider.vue'
</script>
