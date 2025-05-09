<script setup lang="ts">
import { onMounted, ref, type Ref } from 'vue';
import { type Camera } from '@/resources/types';

import Sidebar from '@/components/sidebar/Sidebar.vue';
import Main from '@/components/main/Main.vue';

// Get camera list
const cameras: Ref<Camera[]> = ref([{} as Camera]);
async function getCameras(url: string) {
  try {
    const headersList = {
      "Accept": "*/*",
      "Access-Control-Allow-Origin": "*"
    }
    const res = await fetch(url, { 
      method: "GET",
      headers: headersList 
    });
    cameras.value = await res.json();
    console.log(cameras.value)
  } catch(e) {
    console.log(e);
  }
}

// Internal state for witch camera to display
const camera = ref(0);
function setCamera(cameraId: number) {
  console.log("Camera set "+cameraId)
  const urlParams = new URLSearchParams(window.location.search);
  // If the url has no camera param add it
  if(!urlParams.has("camera")) {
    // Update url
    urlParams.set("camera", cameraId.toString());
    window.history.pushState({}, "", "?"+urlParams.toString());
    // Update state
    camera.value = cameraId;
  } 
  // If the url has a different camera param update it to the correct one
  else if(parseInt(urlParams.get("camera")!) != cameraId) {
    // Update url
    urlParams.set("camera", cameraId.toString());
    window.history.pushState({}, "", "?"+urlParams.toString());
    // Update state
    camera.value = cameraId;
  } 
  // If the url has the same camera param update the internal state;
  else { camera.value = cameraId; }
}
function setupCamera() {
  const urlParams = new URLSearchParams(window.location.search);
  if(!urlParams.has("camera")) setCamera(598);
  else setCamera(parseInt(urlParams.get("camera")!));
}

onMounted(() => {
  // Fetch camera list
  getCameras("http://localhost:3000/api/cameras/");
  // Set camera according to the url or to the default (598)
  setupCamera();
})
</script>

<template>
  <Sidebar 
    @setCamera="(cameraId) => setCamera(cameraId)"  
    class="sidebar" 
    :cameras :cameraId="camera"
  />
  <Main class="main" :cameraId="camera"/>
</template>

<style scoped>
.sidebar {
  min-width: 15vw;
  border-radius: 25px;
  background-color: var(--bg-color);
}
.main {
  flex-grow: 1;
}
</style>
