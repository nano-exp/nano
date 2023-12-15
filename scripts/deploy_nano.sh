#!/usr/bin/env bash
cd "$(dirname "$0")" || exit 1

cd ..

docker run -d \
  -v "$(pwd):/app" \
  -p 9000:9000 \
  --name nano \
  openjdk:21 \
  "/app/scripts/start_nano.sh"
