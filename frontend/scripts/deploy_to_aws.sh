#!/bin/sh
set -e

# Build the frontend and deploy it to the AWS cloud

FRONTEND_STACK_NAME="s3://cardcreator-frontendbucket-yiqbfxgx2ypt/"

cd "$(dirname "$0")"
./build.sh

cd ".."
aws s3 sync dist/ "$FRONTEND_STACK_NAME"