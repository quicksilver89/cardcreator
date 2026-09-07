#!/bin/sh
set -e

# Build the backend and deploy it to the AWS cloud

cd "$(dirname "$0")"
./build.sh

cd ".."
sam deploy #--guided Can be added to update config
