<script setup lang="ts">
import type { Camera } from '@/resources/types';

import Cameras from './components/Cameras.vue';
import Filter from './components/Filter.vue';
import { ref, type Ref } from 'vue';

defineProps<{
  cameras: Camera[]
  cameraId: number
}>()

const filter: Ref<string> = ref("");
function updateFilter(selected: string) { filter.value = selected; }
</script>

<template>
  <div class="sideBar">
    <a href="https://github.com/psikoo/DGTCweb" target="_blank" class="header">DGTC web</a>
    <Filter @updateFilter="(selected) => updateFilter(selected)"/>
    <Cameras @setCamera="(cameraId) => $emit('setCamera', cameraId)" :cameras :filter :cameraId/>
  </div>
</template>

<style scoped>
.sideBar {
  padding: 1rem;
  height: 100%;
  background-color: var(--bg-color);
  border-radius: 25px;
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,1);
  display: flex;
  flex-flow: column;
}

.header {
  padding: .5rem;
  text-align: center;
  border-radius: calc(25px - 1rem);
  background: var(--primary-color);
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,1);
  flex: 0 1 auto;
}
</style>