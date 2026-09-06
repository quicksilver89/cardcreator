#!/bin/sh

# Deletes the database docker image. Useful for cleaning up resources once completely finished.

docker stop dynamodb > /dev/null 2>&1
docker rm dynamodb > /dev/null 2>&1
docker volume rm dynamodb > /dev/null 2>&1
docker network rm dynamodb > /dev/null 2>&1
docker rmi amazon/dynamodb > /dev/null 2>&1