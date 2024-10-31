#!/usr/bin/env bash
cd "$(dirname "$0")" || exit 1

if [ -f .env ]; then
    . .env
fi

exec java -Xms1024m -Xmx1024m -jar server/build/libs/server.jar "$@"
