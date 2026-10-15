#!/bin/sh
set -e

PORT=42618
NAME=keycloak

docker stop "$NAME" > /dev/null 2>&1 || true
docker rm "$NAME" > /dev/null 2>&1 || true

# Create a new keycloak docker image.
docker run --name "$NAME" -p "$PORT:8080" -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
