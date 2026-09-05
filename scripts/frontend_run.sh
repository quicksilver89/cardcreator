#!/bin/sh
set -e

# Run the web server. Can take one optional command line argument of a port number, otherwise it defaults to 8080.

PORT="${1:-8080}"
SERVICE_URL="http://localhost:3000"
FRONTEND_DIR="$(dirname "$0")/../frontend"

npx http-server "$FRONTEND_DIR" -p "$PORT" -P "$SERVICE_URL" --cors -c-1