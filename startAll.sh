#!/bin/bash
homeRepo=$(pwd)

if [ "$1" == "--buildJava"  ] || [ "$1" = "-j" ]; then
  echo "-- Build backend --"
  cd $homeRepo/java
  sudo mvn clean validate compile assembly:assembly -DdescriptorId=jar-with-dependencies
  cp $homeRepo/java/target/autodownload-1-jar-with-dependencies.jar $homeRepo/java/autodownload.jar
fi

echo "-- Build backend --"
cd $homeRepo/nest
sudo docker buildx build -t nest-dgt:1 . >/dev/null

echo "-- Starting docker --"
cd $homeRepo/docker
sudo docker compose down >/dev/null
sudo docker compose up -d >/dev/null

echo "-- Script End --"
