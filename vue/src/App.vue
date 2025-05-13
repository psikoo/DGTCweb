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

// Add or remove camera from watchList
const apiPassword: Ref<string> = ref("");
async function updateWatchList(cameraId: number, state: boolean) {
  apiPassword.value = prompt("Enter an api password", "")?? "";
  await postUpdate("http://localhost:3000/api/cameras/"+cameraId, apiPassword.value, state);
  await getCameras('http://localhost:3000/api/cameras/');
}

// Patch camera's watch to the given state
const resStatus: Ref<number> = ref(0)
async function postUpdate(url: string, apiPassword: string, state: boolean) {
  try {
    const headersList = {
      "Accept": "*/*",
      "Access-Control-Allow-Origin": "*",
      "apikey": apiPassword,
      "Content-Type": "application/json"
    }
    const res = await fetch(url, { 
      method: "PATCH",
      headers: headersList,
      body: JSON.stringify({"watch": state})
    });
    resStatus.value = res.status;
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

// Set the current camera to the url
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

const phone: Ref<boolean> = ref(!!navigator.userAgent.match(/iPad|iPhone|iPod|BlackBerry|Android|Windows Pone|webOS|Nintendo Switch|Nintendo WiiU|Nintendo 3DS/i));
const isInstagram = ref(!!navigator.userAgent.match(/Instagram/i));
const isAndroid = ref(!!navigator.userAgent.match(/Android/i));
const isIOS = ref(!!navigator.userAgent.match(/iPad|iPhone|iPod/i));
</script>

<template>
  <div v-if="isInstagram">
    <a v-if="isAndroid" href="intent://dgt.cait.moe#Intent;scheme=https;end" target="_blank" class="openIn">Open your default browser</a>
    <a v-else-if="isIOS" href="x-safari-https://dgt.cait.moe" target="_blank" class="openIn">Open in Safari</a>
    <h1 v-else>Please manually open the site in your native browser of choice :D</h1>
  </div>
  <Sidebar @setCamera="(cameraId) => setCamera(cameraId)" :cameras :cameraId="camera" />
  <Main @updateCameras="(cameraId, state) => updateWatchList(cameraId, state)"  :cameraId="camera"/>
</template>
