#!/bin/bash
while read p; do
  ./post.sh $p $1
done < cam.txt