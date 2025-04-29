import * as fs from 'fs'
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { ValidationPipe } from '@nestjs/common/pipes/validation.pipe';
require('dotenv').config();

export let app;
async function bootstrap() {
  app = await NestFactory.create(AppModule);
  app.setGlobalPrefix("api");
  app.enableCors();
  app.useGlobalPipes(new ValidationPipe({
    transform: true,
    whitelist: true
  }))
  await app.listen(AppModule.port);
}
bootstrap();
