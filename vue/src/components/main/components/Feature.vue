<script setup lang="ts">
defineProps<{
  photoUrl: string;
}>()
</script>

<template>
  <div class="feature">
    <div 
      @click="$emit('updatePhotoArrayPos', -1)"
      class="arrow"
    ><</div>
    <img 
      @click="$emit('setZoom', true)"
      :src="photoUrl" :alt="photoUrl" 
      draggable="false"
    />
    <div 
      @click="$emit('updatePhotoArrayPos', 1)"
      class="arrow"
    >></div>
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
  height: 100%;
  border-radius: 25px;
  box-shadow: 0px 0px 10px -2px rgba(0,0,0,1);
  user-select: none;
  cursor: zoom-in;
}
.arrow {
  padding: 1rem 0.65rem 1rem 0.65rem;
  border-radius: 25px;
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,1);

  user-select: none;
  cursor: pointer;
} .arrow:hover {
  box-shadow: 0px 0px 10px -5px rgba(150,150,150,1);
} .arrow:active {
  box-shadow: 0px 0px 10px -5px rgba(255,255,255,1);
}
</style>