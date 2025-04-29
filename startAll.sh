#!/bin/bash
homeRepo=$(pwd)

echo "-- Build backend --"
cd $homeRepo/nest
sudo docker buildx build -t nest-dgt:1 . >/dev/null

echo "-- Starting docker --"

cd $homeRepo/docker
sudo docker compose down >/dev/null
sudo docker compose up -d >/dev/null

echo "-- Script End --"
