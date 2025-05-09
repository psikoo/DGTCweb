<script setup lang="ts">
import type { Camera } from '@/resources/types';

import Header from './components/Head.vue';
import Cameras from './components/Cameras.vue';

defineProps<{
  cameras: Camera[]
  cameraId: number
}>()
</script>

<template>
  <div class="sideBar">
    <Header class="header"/>
    <Cameras @setCamera="(cameraId) => $emit('setCamera', cameraId)" class="cameras" :cameras :cameraId/>
  </div>
</template>

<style scoped>
.header {
  z-index: 10;
  position: sticky;
  top: 0;
  left: 0;
}
.cameras{
  margin-top: 1rem;
  overflow-y: scroll;
}
.sideBar {
  padding: 1rem;
  display: flex;
  flex-flow: column;
  height: 100%;
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,1);
}
</style>