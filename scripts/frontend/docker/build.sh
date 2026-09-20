#!/bin/sh
set -e

# Build the frontend and package it in a docker container

cd "$(dirname "$0")"

../build.sh

docker image build -t cardcreator_frontend -f Dockerfile ../../../frontend
