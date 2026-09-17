#!/bin/sh
set -e

# Run the Spring Boot server.

cd "$(dirname "$0")/.."

./gradlew bootRun
