#!/bin/sh
set -e

# Build and run the test web server.

cd "$(dirname "$0")"

./build.sh

cd "../../frontend/dist" 

caddy run --config - --adapter caddyfile <<EOF
:8080 {
    reverse_proxy /service/* localhost:3000
    reverse_proxy /realms/* localhost:42618
    file_server {
        root .
    }
}
EOF

