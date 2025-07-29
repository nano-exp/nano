<template>
  <div class="notfound">4️⃣0️⃣4️⃣{{ emo }}</div>
</template>

<script setup>
const emoji = ['😭', '🤔', '🤕', '😫', '😵', '🙏', '🫣', '🤪', '👀']
const emo = emoji[Math.floor(Math.random() * emoji.length)]
</script>

<style scoped>
.notfound {
  min-height: 100vh;
  display: grid;
  place-items: center;
  font-size: 4rem;
  font-weight: bold;
}
</style>
