<script setup lang="ts">
import { ref, type Ref } from 'vue';

const selected: Ref<string> = ref("");
</script>

<template>
  <select v-model="selected" @change="$emit('updateFilter', selected)" class="filter">
    <option value="" selected="true">Road Filter - All</option>
    <option value="A-1">A-1</option>
    <option value="A-2">A-2</option>
    <option value="A-3">A-3</option>
    <option value="A-4">A-4</option>
    <option value="A-42">A-42</option>
    <option value="A-5">A-5</option>
    <option value="A-5R">A-5R</option>
    <option value="A-6">A-6</option>
    <option value="AP-6">AP-6</option>
    <option value="M-11">M-11</option>
    <option value="M-111">M-111</option>
    <option value="M-12">M-12</option>
    <option value="M-137">M-137</option>
    <option value="M-14">M-14</option>
    <option value="M-21">M-21</option>
    <option value="M-31">M-31</option>
    <option value="M-40">M-40</option>
    <option value="M-45">M-45</option>
    <option value="M-50">M-50</option>
    <option value="M-501">M-501</option>
    <option value="M-503">M-503</option>
    <option value="M-505">M-505</option>
    <option value="M-506">M-506</option>
    <option value="M-600">M-600</option>
    <option value="M-601">M-601</option>
    <option value="M-607">M-607</option>
    <option value="M-608">M-608</option>
    <option value="M-614">M-614</option>
    <option value="M-966">M-966</option>
  </select>
</template>

<style scoped>
.filter {
  margin: 1rem 1rem 0rem 1rem;
  padding: 0.5rem;
  background-color: var(--hover-color);
  color: var(--text-color);
  border-radius: calc(25px - 1rem);
  border: 0px;
}
</style>