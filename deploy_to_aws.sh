#!/bin/sh
set -e

# Build both the backend and front end code.

cd "$(dirname "$0")"

./backend/scripts/deploy_to_aws.sh
./frontend/scripts/deploy_to_aws.sh