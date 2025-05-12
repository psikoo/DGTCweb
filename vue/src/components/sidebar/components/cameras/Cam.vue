<script setup lang="ts">
defineProps<{
  road: string
  location: string
  name: string
  watch: boolean
  cameraId: number
}>()
</script>

<template>
  <div @click="$emit('setCamera',parseInt(name))" :class="{ selected: cameraId==parseInt(name), cam: true }">
    <div>📷</div>
    <div class="name">
      <div class="location">{{ road }} {{ location }}</div>
      <div>-</div>
      <div class="id">{{ name }}</div>
    </div>
    <div>{{ watch ? "👁️" : " " }}</div>
  </div>
</template>

<style scoped>
.cam {
  text-align: start;
  margin-block:0.5rem;
  padding: 0.5rem 1rem;
  display: flex;
  gap: 1rem;
  cursor: pointer;
  user-select: none;
  background-color: var(--bg-color);
} .cam:hover {
  filter: brightness(90%);
  border-radius: calc(25px - 1rem);
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,1);
}
.selected {
  border-radius: calc(25px - 1rem);
  background-color: var(--hover-color);
  box-shadow: 0px 0px 10px -5px rgba(0,0,0,1);
}
.name {
  flex-grow: 1;
  display: flex;
  gap: 0.5rem;
}
.location {
  min-width: 5rem;
}
.id {
  flex-grow: 1;
}
</style>