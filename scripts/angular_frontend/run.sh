#!/bin/sh
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

cd "$SCRIPT_DIR/../../frontend/angular"

ng serve --proxy-config "$SCRIPT_DIR/proxy.conf.json" --port 8080
