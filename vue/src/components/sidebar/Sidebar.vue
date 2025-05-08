<script setup lang="ts">
import Header from './components/Head.vue';
import Cameras from './components/cameras/Cameras.vue';
import type { Camera } from '@/types';
defineProps<{
  cameras: Camera[]
}>()
</script>

<template>
  <div class="sideBar">
    <Header class="header"/>
    <Cameras @setCamera="(cameraId) => $emit('setCamera', cameraId)" class="cameras" :cameras/>
  </div>
</template>

<style scoped>
.sideBar {
  display: flex;
  flex-flow: column;
  height: 100%;
}
.header {
  flex: 0 1 auto;
}
.watch {
  flex: 0 1 auto;
}
.cameras {
  flex: 1 1 auto;
}

</style>