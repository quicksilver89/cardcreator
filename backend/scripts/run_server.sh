#!/bin/sh
set -e

# Run the SAM server.

cd "$(dirname "$0")/.."

sam local start-api --docker-network dynamodb