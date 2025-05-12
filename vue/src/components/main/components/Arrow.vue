<script setup lang="ts">
defineProps<{
  updateAmount: number;
}>()
</script>

<template>
  <div @click="$emit('updatePhotoArrayPos', updateAmount)" class="arrow">
    <slot></slot>
  </div>
</template>

<style scoped>
.arrow {
  padding: 1rem 0.65rem 1rem 0.65rem;
  border-radius: 25px;
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,1);
  user-select: none;
  cursor: pointer;
} 
.arrow:hover { box-shadow: 0px 0px 10px -5px rgba(150,150,150,1); } 
.arrow:active { box-shadow: 0px 0px 10px -5px rgba(255,255,255,1); }
</style>