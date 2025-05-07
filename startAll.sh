#!/bin/bash
homeRepo=$(pwd)

if [ "$1" == "--buildJava"  ] || [ "$1" = "-j" ]; then
  echo "-- Build backend --"
  cd $homeRepo/java
  ./build.sh
fi

echo "-- Build backend --"
cd $homeRepo/nest
sudo docker buildx build -t nest-dgt:1 . >/dev/null
cd $homeRepo/java
sudo docker buildx build -t java-dgt:1 . >/dev/null

echo "-- Starting docker --"
cd $homeRepo/docker
sudo docker compose down >/dev/null
sudo docker compose up -d >/dev/null

echo "-- Script End --"
