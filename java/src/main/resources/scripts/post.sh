#!/bin/bash
unix=$(date +%s)
#* Post to imgur
curl -s --location 'https://api.imgur.com/3/image' \
     --header 'Authorization: Client-ID 546c25a59c58ad7' \
     --form 'image=@"'$1'"' \
     --form 'type="image"' \
     --form 'title="'$2'"' \
     --form 'description="'$unix'"' > res.json &&
sed -i 's/,/,\n/g' res.json &&
grep "link" res.json > link.txt &&
sed -i 's/"link":"//g' link.txt &&
sed -i 's/",//g' link.txt &&
link=$(cat link.txt) &&

#* Get cameraId
curl -s --location 'http://localhost:3000/api/cameras/name/'$2 > res.json
sed -i 's/,/,\n/g' res.json &&
grep "id" res.json > link.txt &&
sed -i 's/{"id"://g' link.txt &&
sed -i 's/,//g' link.txt &&
cameraId=$(cat link.txt) &&

#* Post to nest
data='{"url":"'$link'","time":'$unix',"cameraId":'$cameraId'}'
curl -s --location 'http://localhost:3000/api/photos' \
     --header 'apikey: Hernandez1/' \
     --header 'Content-Type: application/json' \
     --data $data

rm ./res.json 2> /dev/null
rm ./link.txt 2> /dev/null