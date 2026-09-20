#!/bin/sh
set -e

# Build the spring boot and package it in a docker container

cd "$(dirname "$0")"

(
	cd ../../../backend
	./gradlew bootWar
)

docker image build -t cardcreator_service -f Dockerfile ../../../backend
