import { Module } from '@nestjs/common';
import { CamerasService } from './cameras.service';
import { CamerasController } from './cameras.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Camera } from './entities';

@Module({
  imports: [TypeOrmModule.forFeature([Camera])],
  controllers: [CamerasController],
  providers: [CamerasService],
})
export class CamerasModule {}
