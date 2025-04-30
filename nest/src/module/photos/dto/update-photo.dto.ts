import { PartialType } from '@nestjs/mapped-types';
import { CreatePhotoDto } from './create-photo.dto';
import { IsNumber, IsString } from 'class-validator';
import { Camera } from 'src/module/cameras/entities';

export class UpdatePhotoDto extends PartialType(CreatePhotoDto) {
  @IsString()
  url: string;
  @IsNumber()
  time: number;
  @IsNumber()
  cameraId: Camera;
}
