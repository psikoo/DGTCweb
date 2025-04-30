import { Column, Entity, JoinColumn, ManyToOne, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { Camera } from "src/module/cameras/entities";

@Entity()
export class Photo {
  @PrimaryGeneratedColumn("increment")
  id: number;
  @Column({nullable:false})
  url: string;
  @Column({nullable:false, type:"bigint"})
  time: string;
  @ManyToOne(() => Camera, (camera) => camera.photos, {cascade: true, nullable:false})
  @JoinColumn({name: "cameraId"})
  cameraId: Camera
}
