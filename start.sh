#!/bin/bash
repo=$(pwd)
varRepo="/var/www/aboutme" #! .../aboutme so it works with y other sites

echo "-- Pulling repo --"
cd $repo
sudo git stash >/dev/null
sudo git pull >/dev/null

echo "-- Building the backend --"
cd $repo/nest
sudo docker build -t dgt-nest:1 . >/dev/null
cd $repo/java
sudo docker build -t dgt-java:1 . >/dev/null

echo "-- Building the frontend --"
cp $repo/vue $varRepo/aboutme-frontend/site/vue/dgt/vue
cd $varRepo/aboutme-frontend/site/vue/dgt/vue
echo "> dgt"
sudo npm install >/dev/null
sudo npm run build >/dev/null

cd $repo/docker
sudo docker compose down >/dev/null
sudo docker compose up -d >/dev/null

echo "-- Script End --"
