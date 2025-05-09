import { type Photo } from "./photo.type";

export type Camera = {
  id: number;
  url: string;
  name: string;
  road: string;
  location: string;
  watch: boolean;
  photos: Photo[];
}