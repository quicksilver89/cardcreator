#!/bin/sh
set -e

# Build the code for the SAM server. Needs to be done for changes to java code to take effect.

cd "$(dirname "$0")/.."

sam build