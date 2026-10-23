#!/bin/sh
set -e

# Build and run the test web server.

cd "$(dirname "$0")"

./build.sh

cd "../../frontend/dist"

caddy run --config - --adapter caddyfile <<EOF
:8080 {
    reverse_proxy /service/* localhost:42611
    reverse_proxy /auth/* localhost:42612
    file_server {
        root .
    }
}
EOF

