<script setup lang="ts">
import { onMounted, ref, watchEffect, type Ref } from 'vue';
import { type Camera, type Photo } from '@/resources/types';

import Zoom from './components/Zoom.vue';
import Feature from './components/Feature.vue';
import Historical from './components/Historical.vue';

const props = defineProps<{
  cameraId: number;
}>()

// https://infocar.dgt.es/etraffic/data/camaras/***.jpg
const cameraUrl: Ref<string> = ref("Loading");
// Get camera from name (to get the id)
const camera: Ref<Camera> = ref({} as Camera);
async function getCamera(url: string) {
  try {
    const headersList = {
      "Accept": "*/*",
      "Access-Control-Allow-Origin": "*"
    }
    const res = await fetch(url, { 
      method: "GET",
      headers: headersList 
    });
    camera.value = await res.json();
    cameraUrl.value = camera.value.url;
    console.log("Camera > "+camera.value.name)
  } catch(e) {
    console.log(e);
  }
  // Fetch photos for the camera
  await getPhotos("http://localhost:3000/api/photos/camera/"+camera.value.id);
}
// Get photos from the camera
const photos: Ref<Photo[]> = ref([{} as Photo]);
async function getPhotos(url: string) {
  try {
    const headersList = {
      "Accept": "*/*",
      "Access-Control-Allow-Origin": "*"
    }
    const res = await fetch(url, { 
      method: "GET",
      headers: headersList 
    });
    photos.value = await res.json();
  } catch(e) {
    console.log(e);
  }
}

// Show zoomed image
const zoom: Ref<boolean> = ref(false);
function setZoom(state: boolean) { zoom.value = state; }

// Url used for feature image
const photoUrl: Ref<string> = ref("Loading");
function setImage(Url: string) { photoUrl.value = Url; }

// Update the live image
const liveUpdate: Ref<boolean> = ref(false);
function toggleLiveUpdate(state: boolean) { 
  setPhotoArrayIndex(0);
  liveUpdate.value = liveUpdate.value? false : true; 
  photoUrl.value = cameraUrl.value+"?t="+new Date().getTime();
}

// 
const photoArrayPos: Ref<number> = ref(0);
function updatePhotoArrayPos(int: number) { 
  // Next photo
  if (int>0 && (photoArrayPos.value+int)<=photos.value.length) {
    photoArrayPos.value++;
    setImage(photos.value[photoArrayPos.value-1].url);
  } 
  // Previous photo
  else if(int<0 && (photoArrayPos.value+int)>=0) {
    if((photoArrayPos.value+int)==0) {
      photoArrayPos.value--;
      setImage(cameraUrl.value);
    }
    else {
      photoArrayPos.value--;
      setImage(photos.value[photoArrayPos.value-1].url);
    }
  }
  else console.log("Photo cant change");  
}
function setPhotoArrayIndex(index: number) {
  photoArrayPos.value = index;
  if(index == 0) setImage(cameraUrl.value);
  else setImage(photos.value[photoArrayPos.value-1].url);
}

onMounted(() => {
  setInterval(function() {
    setPhotoArrayIndex(0);
    if(liveUpdate) photoUrl.value=photoUrl.value+"?"+new Date().getTime();
  }, 60*1000);
})

watchEffect(() => {
  // Get camera from name
  if(props.cameraId != 0 && props.cameraId != parseInt(camera.value.name)) getCamera("http://localhost:3000/api/cameras/name/"+props.cameraId);
  // Set feature photo to cameraUrl
  photoUrl.value = cameraUrl.value;
  // Reset photoArrayPos when changing camera
  photoArrayPos.value = 0;
})
</script>

<template>
  <div class="main">
    <Zoom @click="zoom = false" :class="{ hidden: !zoom }" :photoUrl/>

    <Feature 
      @setZoom="(state) => setZoom(state)" 
      @updatePhotoArrayPos="(int) => updatePhotoArrayPos(int)" 
      @toggleLiveUpdate="(state) => toggleLiveUpdate(state)"
      @updateCameras="(cameraId, state) => $emit('updateCameras', cameraId, state)"
      :photoUrl :cameraId="camera.id" :liveUpdate :camera
    />
    <Historical @setImage="(index) => setPhotoArrayIndex(index)" :cameraUrl :photos/>
  </div>
</template>

<style scoped>
.main {
  flex-grow: 1;
  padding: 0.5rem 1rem 0.5rem 1rem;
  height: calc(100% + 1rem);
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  overflow-x: hidden;
}
</style>