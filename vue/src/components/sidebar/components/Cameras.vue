<script setup lang="ts">
import type { Camera } from '@/resources/types';

import Cam from './cameras/Cam.vue';
import Separator from './cameras/Separator.vue';

defineProps<{
  cameras: Camera[];
  filter: string;
  cameraId: number;
}>()
</script>

<template>
  <div class="sideCameras">
    <div class="cameras">
      <Cam v-for="camera in cameras" 
        @setCamera="(cameraId) => $emit('setCamera', cameraId)" 
        :class="{ hidden: !camera.watch }" 
        :road="camera.road" :location="camera.location" :name="camera.name" :watch="camera.watch" :cameraId
      />
    </div>
    <Separator/>
    <div class="cameras">
      <Cam v-for="camera in cameras" 
        @setCamera="(cameraId) => $emit('setCamera', cameraId)"
        :class="{ hidden: camera.watch || (filter != '' && camera.road != filter)}" 
        :road="camera.road" :location="camera.location" :name="camera.name" :watch="camera.watch" :cameraId
      />
    </div>
  </div>
</template>

<style scoped>
.cameras {
  margin: 0.5rem;
}
.sideCameras { 
  flex: 1 1 auto; 
  overflow-y: scroll;
  scrollbar-width: thin;
}
</style>