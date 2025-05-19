<script setup lang="ts">
import { ref, type Ref } from 'vue';
import Arrow from './Arrow.vue';
import type { Camera } from '@/resources/types';

defineProps<{
  photoUrl: string;
  cameraId: number;
  liveUpdate: boolean;
  camera: Camera
}>()
</script>

<template>
  <div class="feature">
    <Arrow :updateAmount="-1" @updatePhotoArrayPos="(updateAmount) => $emit('updatePhotoArrayPos', updateAmount)" class="LArrow"><</Arrow>
    <img 
      @click="$emit('setZoom', true)"
      :src="photoUrl" :alt="photoUrl" 
      draggable="false"
    />
    <Arrow :updateAmount="1" @updatePhotoArrayPos="(updateAmount) => $emit('updatePhotoArrayPos', updateAmount)" class="RArrow">></Arrow>
    
    <div class="buttons grow">
      <a :href="photoUrl" target="_blank" draggable="false" class="button">
        <div class="ButtonContent">Download</div>
        <div class="icon">⬇️</div>
      </a>
      <div @click="$emit('toggleLiveUpdate')" :class="{ active: liveUpdate, button: true }">
        <div class="ButtonContent">Live Update</div>
        <div class="icon">🔴</div>
      </div>
      <div @click="$emit('updateCameras', cameraId, camera.watch ? false : true)" class="button">
        <div class="ButtonContent">{{ camera.watch ? "Remove" : "Add" }}</div>
        <div class="icon">👁️</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.feature {
  flex-grow: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
}
.feature>img {
  width: 100%;
  max-height: calc(100vh - 15rem);
  max-width: calc((100vh - 15rem) * 1.777777); /* 1.7 is for 16/9 aspect ratio */
  border-radius: 25px;
  box-shadow: 0px 0px 10px -2px rgba(0,0,0,1);
  user-select: none;
  cursor: zoom-in;
}

.LArrow { margin-left: 1rem; }
.RArrow { margin-right: 1rem; }

.buttons {
  margin-right: 1rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
.button {
  padding: 0.75rem;
  border-radius: 10px;
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,1);
  user-select: none;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
} 
.button:hover { box-shadow: 0px 0px 10px -5px rgba(150,150,150,1); } 
.button:active { box-shadow: 0px 0px 10px -5px rgba(255,255,255,1); }

.ButtonContent {
  margin-right: 1rem;
  text-wrap: nowrap;
}

.active {
  background-color: rgba(0,128,0,0.2);
}

.grow {
  flex-grow: 1;
}

.smallFeature {
  flex-direction: column !important;
  flex-grow: 0 !important;
}
.smallButtons{
  flex-direction: row !important;
  align-items: end !important;
  flex-grow: 1 !important;
}
</style>