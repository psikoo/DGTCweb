<script setup lang="ts">
import type { Camera, Photo } from '@/types';
import { onMounted, ref, watchEffect, type Ref } from 'vue';

const props = defineProps<{
  cameraId: number;
}>()
const cameraUrl: Ref<string> = ref("Loading");
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
  await getPhoto("http://localhost:3000/api/photos/camera/"+camera.value.id);
}
const photoUrl: Ref<string> = ref("Loading");
const photos: Ref<Photo[]> = ref([{} as Photo]);
async function getPhoto(url: string) {
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
    photoUrl.value = photos.value[0].url;
    console.log("Main photo > "+photos.value[0].url)
  } catch(e) {
    console.log(e);
  }
}
watchEffect(() => {
  if(props.cameraId != 0) getCamera("http://localhost:3000/api/cameras/name/"+props.cameraId);
})
</script>

<template>
    <div>
        main {{ cameraId }} - {{ camera }}
        <br>
        <p>Live:</p>
        <img :src="cameraUrl" :alt="cameraUrl">
        <br>
        <p>DB:</p>
        <img :src="photoUrl" :alt="photoUrl">
    </div>
</template>

<style scoped>

</style>