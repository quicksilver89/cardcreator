#!/bin/sh
set -e

# Build the angular frontend and package it in a docker container

cd "$(dirname "$0")"

docker image build -t cardcreator_angular -f Dockerfile ../../../frontend/angular
