#!/bin/bash
prefix="https://infocar.dgt.es/etraffic/data/camaras/"
name=$1
name=${name#"$prefix"}
name=${name::-4}

data='{"url":"'$1'","name":"'$name'","location":"'$3'","road":"'$2'","watch":false}'
echo $data
curl -k --location 'https://192.168.0.40:3000/api/dgt/cameras' \
     --header 'apikey: '$4'' \
     --header 'Content-Type: application/json' \
     --data $data