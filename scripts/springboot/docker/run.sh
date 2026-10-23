#!/bin/sh
set -e

# Run the spring boot docker container

docker run -p 42611:8080 --network dynamodb -e DYNAMODB_ENDPOINT=http://dynamodb:8000 cardcreator_service
