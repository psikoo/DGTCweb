#!/bin/bash

imagePath=$1
cameraName=$2
api=$3
apiKey=$4

unix=$(date +%s)
#* Post to imgur
curl -s --location 'https://api.imgur.com/3/image' \
     --header 'Authorization: Client-ID 546c25a59c58ad7' \
     --form 'image=@"'$imagePath'"' \
     --form 'type="image"' \
     --form 'title="'$cameraName'"' \
     --form 'description="'$unix'"' > ./request/res$cameraName.json &&
sed -i 's/,/,\n/g' ./request/res$cameraName.json &&
grep "link" ./request/res$cameraName.json > ./request/link$cameraName.txt &&
sed -i 's/"link":"//g' ./request/link$cameraName.txt &&
sed -i 's/",//g' ./request/link$cameraName.txt &&
link=$(cat ./request/link$cameraName.txt) &&

#* Get cameraId
curl -k -s --location $api'/cameras/name/'$cameraName > ./request/res$cameraName.json &&
sed -i 's/,/,\n/g' ./request/res$cameraName.json &&
grep "id" ./request/res$cameraName.json > ./request/link$cameraName.txt &&
sed -i 's/{"id"://g' ./request/link$cameraName.txt &&
sed -i 's/,//g' ./request/link$cameraName.txt &&
cameraId=$(cat ./request/link$cameraName.txt) &&

#* Post to nest
data='{"url":"'$link'","time":'$unix',"cameraId":'$cameraId'}'
curl -k -s --location $api'/photos' \
     --header 'apikey: '$apiKey \
     --header 'Content-Type: application/json' \
     --data $data

rm ./request/res$cameraName.json
rm ./request/link$cameraName.txt