#!/usr/bin/env bash
cd "$(dirname "$0")" || exit 1

git pull
pnpm install
pnpm run build
./gradlew clean build
docker compose restart