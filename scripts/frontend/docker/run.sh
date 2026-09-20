#!/bin/sh
set -e

# Run the front end docker container

docker run -p 80:80 cardcreator_frontend
