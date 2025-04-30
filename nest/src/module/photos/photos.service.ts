import { Injectable, NotFoundException } from '@nestjs/common';
import { CreatePhotoDto } from './dto/create-photo.dto';
import { UpdatePhotoDto } from './dto/update-photo.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Photo } from './entities';
import { Repository } from 'typeorm';
import { Camera } from '../cameras/entities';

@Injectable()
export class PhotosService {
  constructor(@InjectRepository(Photo) private readonly photoRepository: Repository<Photo>) {}

  async create(body: CreatePhotoDto) {
    const photo: Photo = await this.photoRepository.create({
      url: body.url,
      time: body.time,
      cameraId: body.cameraId,
    })
    return this.photoRepository.save(photo);
  }

  async findAll() {
    return await this.photoRepository.find({relations: ["cameraId"]});
  }

  async findOne(id: number) {
    const photo: Photo | null = await this.photoRepository.findOne({where: {id: id}, relations: ["cameraId"]});
    if(!photo) throw new NotFoundException();
    else return photo;
  }

  async update(id: number, body: UpdatePhotoDto) {
    const photo: Photo | undefined = await this.photoRepository.preload({
      id,
      url: body.url,
      time: body.time,
      cameraId: body.cameraId,
    })
    if(!photo) throw new NotFoundException("Resource not found");
    else this.photoRepository.save(photo);
    return photo;
  }

  async remove(id: number) {
    const photo: Photo | null = await this.photoRepository.findOneBy({id});
    if(!photo) throw new NotFoundException("Resource not found");
    else {
      this.photoRepository.remove(photo);
      return JSON.parse(`{"deletedId": "${id}"}`);
    }
  }
}
