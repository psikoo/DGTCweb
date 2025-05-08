<script setup lang="ts">
import Sidebar from './components/sidebar/Sidebar.vue';
import Main from './components/main/Main.vue';
import { onMounted, ref, type Ref } from 'vue';
import { type Camera } from '@/types';

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

const camera = ref(0);
function setCamera(cameraId: number) {
  console.log("Camera set "+cameraId)
  const urlParams = new URLSearchParams(window.location.search);
  if(!urlParams.has("camera")) {
    urlParams.set("camera", cameraId.toString());
    window.location.href = location.protocol+"//"+location.host+location.pathname+"?"+urlParams;
  } else if(parseInt(urlParams.get("camera")!) != cameraId) {
    urlParams.set("camera", cameraId.toString());
    window.location.href = location.protocol+"//"+location.host+location.pathname+"?"+urlParams;
  } else {
    camera.value = cameraId;
  }

}

onMounted(() => {
  // Fetch cameras
  getCameras("http://localhost:3000/api/cameras/");
  // Set current camera
  const urlParams = new URLSearchParams(window.location.search);
  if(!urlParams.has("camera")) setCamera(598);
  else setCamera(parseInt(urlParams.get("camera")!));
})
</script>

<template>
  <Sidebar @setCamera="(cameraId) => setCamera(cameraId)"  class="sidebar" :cameras/>
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
