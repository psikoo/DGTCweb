import { Column, Entity, JoinColumn, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { Photo } from "src/module/photos/entities";

@Entity()
export class Camera {
  @PrimaryGeneratedColumn("increment")
  id: number;
  @Column({nullable:false})
  url: string;
  @Column({nullable:false})
  name: string;
  @Column({nullable:false})
  road: string;
  @Column({nullable:false})
  location: string;
  @Column({nullable:false})
  watch: boolean;
  @OneToMany(() => Photo, (photo) => photo.cameraId)
  photos: Photo[]
}
