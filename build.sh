#!/bin/sh
set -e

# Build both the backend and front end code.

cd "$(dirname "$0")"

./backend/scripts/sam_build.sh
./frontend/scripts/build.sh