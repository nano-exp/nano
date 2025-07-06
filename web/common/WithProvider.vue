<template>
  <NConfigProvider :theme="lightTheme" :locale="zhCN" :dateLocale="dateZhCN">
    <NMessageProvider>
      <slot></slot>
    </NMessageProvider>
  </NConfigProvider>
</template>

<script setup>
import { dateZhCN, lightTheme, NConfigProvider, NMessageProvider, zhCN } from 'naive-ui'
</script>
