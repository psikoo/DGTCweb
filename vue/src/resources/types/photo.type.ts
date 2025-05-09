import { type Camera } from "./camera.type";

export type Photo = {
  id: number;
  url: string;
  time: string;
  cameraId: Camera;
}