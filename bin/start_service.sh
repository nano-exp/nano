#!/usr/bin/env bash
cd "$(dirname "$0")" || exit 1

cd ..

if [ -f .env ]; then
    . .env
fi

exec java -Xms512m -Xmx512m -jar service/build/libs/service.jar "$@"
