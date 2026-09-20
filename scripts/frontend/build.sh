#!/bin/sh
set -e

cd "$(dirname "$0")/../../frontend"

SRC="src/"
DIST="dist/"

rm -rf "$DIST"
npx tsc --rootDir "$SRC" --outDir "$DIST"
rsync -av --exclude='*.ts' "$SRC" "$DIST" > /dev/null
