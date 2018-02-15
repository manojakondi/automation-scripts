#!/bin/bash

echo "Removing exited containers";
docker rm -f $(docker ps -qa --no-trunc --filter "status=exited");

echo "Removing dangling images";
docker rmi -f $(docker images -f dangling=true -q);
docker rmi -f $(docker images --no-trunc | grep "none" | awk '/ / { print $3 }');

echo "Removing dangling volumes";
docker volume rm $(docker volume ls -f dangling=true -q);

images="$(docker images | awk '{imageid=$1":"$2;print imageid, $4, $5}')";
greaterThanTwoDays="$(echo -e "$images" | grep -e "[3-9]\sday[s]\|\d\d\|day[s]\|\d\d\|week[s]\|month[s]\|year[s]")";

toBeDeleted="$(echo -e "$greaterThanTwoDays" | awk '{ print $1 }')";

echo "Removing images older than 2 days";
if [ "$toBeDeleted" != "" ]; then
  for i in $toBeDeleted
  do
    echo "deleting $i";
    docker rmi -f $i;
  done
else
  echo "...none found.";
fi
echo "completed cleanup";
