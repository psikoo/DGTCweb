<script setup lang="ts">
defineProps<{
  photoUrl: string;
}>()
</script>

<template>
  <div class="zoom">
    <img class="zoomImg" :src="photoUrl" :alt="photoUrl"/>
  </div>
</template>

<style scoped>
.zoom {
  z-index: 999;
  height: 100vh;
  width: 100vw;
  position: absolute;
  top: 0;
  left: 0;
  background-color: rgba(0,0,0,0.5);
}
.zoom>img {
  display: block;
  height: calc(100vh - 2rem);
  margin: 1rem auto 1rem auto;
  cursor: zoom-out;
}
</style>