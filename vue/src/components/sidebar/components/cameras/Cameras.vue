<script setup lang="ts">
import type { Camera } from '@/types';
import Cam from './Cam.vue';
import Separator from './Separator.vue';
defineProps<{
  cameras: Camera[]
}>()
</script>

<template>
  <div class="sideCameras">
    <div class="cameras">
      <Cam v-for="camera in cameras" 
        @setCamera="(cameraId) => $emit('setCamera', cameraId)" 
        :class="{ hidden: !camera.watch }" 
        :location="camera.location" :name="camera.name" :watch="camera.watch"
      />
    </div>
    <Separator/>
    <div class="cameras">
      <Cam v-for="camera in cameras" 
        @setCamera="(cameraId) => $emit('setCamera', cameraId)"
        :class="{ hidden: camera.watch }" 
        :location="camera.location" :name="camera.name" :watch="camera.watch"
      />
    </div>
  </div>
</template>

<style scoped>
.cameras {
  margin: 0 1rem 1rem 1rem;
}
.hidden {
  display: none;
}
</style>