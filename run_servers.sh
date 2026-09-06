#!/bin/sh
set -e

# Start the database docker image server, the sam backend server, and the frontend web server.

cd "$(dirname "$0")"

cleanup()
{
    ./backend/scripts/dynamodb_stop.sh
    kill "$SERVE_PID" 2>/dev/null
    exit 0
}

trap cleanup INT

./backend/scripts/dynamodb_start.sh
./frontend/scripts/serve.sh&
SERVE_PID=$!
./backend/scripts/sam_run.sh

