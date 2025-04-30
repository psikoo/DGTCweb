import { Column, Entity, ManyToOne, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { Camera } from "src/module/cameras/entities";

@Entity()
export class Photo {
  @PrimaryGeneratedColumn("increment")
  id: number;
  @Column({nullable:false})
  url: string;
  @Column({nullable:false})
  time: number;
  @ManyToOne(() => Camera, (camera) => camera.photos)
  camera: Camera
}
