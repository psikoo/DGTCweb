import { MiddlewareConsumer, Module, RequestMethod } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { PhotosModule } from './module/photos/photos.module';
import { CamerasModule } from './module/cameras/cameras.module';
import { DatabaseModule } from './database/database.module';
import { BasicPasswordMiddleware, LoggerMiddleware } from './middleware';

@Module({
  imports: [ConfigModule.forRoot({ isGlobal: true }), DatabaseModule,
            PhotosModule, CamerasModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {
  static port: number;
  constructor(private readonly configService: ConfigService) {
    AppModule.port = this.configService.get("PORT") ?? 3000;
  }
  // Middleware
  configure(consumer: MiddlewareConsumer) {
    consumer.apply(LoggerMiddleware, BasicPasswordMiddleware)
      .exclude({ path: "favicon.ico", method: RequestMethod.GET })
      .forRoutes({ path:"/", method:RequestMethod.ALL});
  }
}
