import { PartialType } from '@nestjs/mapped-types';
import { CreateCameraDto } from './create-camera.dto';
import { IsBoolean, IsString } from 'class-validator';

export class UpdateCameraDto extends PartialType(CreateCameraDto) {
    @IsString()
    url: string;
    @IsString()
    name: string;
    @IsString()
    road: string;
    @IsString()
    location: string;
    @IsBoolean()
    watch: boolean;
}
