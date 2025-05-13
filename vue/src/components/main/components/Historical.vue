<script setup lang="ts">
import type { Photo } from '@/resources/types';

import Separator from './Separator.vue';

defineProps<{
  cameraUrl: string;
  photos: Photo[];
}>()
</script>

<template>
  <div class="historical">
    <img 
      @click="$emit('setImage', 0)"
      :src="cameraUrl" :alt="cameraUrl"
      draggable="false"
    />
    <Separator/>
    <img 
      @click="$emit('setImage', index+1)"
      v-for="(photo, index) in photos" :src="photo.url" :alt="photo.url" 
      class="otherImg"
      draggable="false"
    />
  </div>
</template>

<style scoped>
.historical {
  display: flex;
  gap: 0.5rem;
  overflow-x: scroll;
  scrollbar-width: thin;
}
.historical>img {
  height: 10rem;
  border-radius: 25px;
  user-select: none;
}
</style>