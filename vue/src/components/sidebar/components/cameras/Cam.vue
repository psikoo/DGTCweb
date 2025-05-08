<script setup lang="ts">
defineProps<{
  location: string
  name: string
  watch: boolean
}>()
</script>

<template>
  <div class="sideWatch">
    <div class="cam">
      <div @click="$emit('setCamera',parseInt(name))">📷 {{ location }} - {{ name }} {{ watch ? "👁️" : "" }}</div>
    </div>
  </div>
</template>

<style scoped>
.cam {
  text-align: start;
  padding: .5rem;
}
</style>