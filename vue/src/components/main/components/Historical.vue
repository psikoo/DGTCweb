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
      @click="$emit('setImage', cameraUrl)"
      :src="cameraUrl" :alt="cameraUrl"
      draggable="false"
    />
    <Separator/>
    <img 
      @click="$emit('setImage', photo.url)"
      v-for="photo in photos" :src="photo.url" :alt="photo.url" 
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
}
.historical>img {
  height: 10rem;
  border-radius: 25px;
  user-select: none;
}
</style>