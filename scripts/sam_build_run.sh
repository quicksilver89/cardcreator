#!/bin/sh
set -e

# Build the code for the SAM server and then runs the server. Needs to be done for changes to java code to take effect.

./sam_build.sh
./sam_run.sh