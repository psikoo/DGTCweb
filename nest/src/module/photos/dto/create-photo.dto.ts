import { IsNumber, IsString } from "class-validator";
import { Camera } from "src/module/cameras/entities";

export class CreatePhotoDto {
  @IsString()
  url: string;
  @IsNumber()
  time: number;
  camera: Camera;
}
