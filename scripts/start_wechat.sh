#!/usr/bin/env bash
cd "$(dirname "$0")" || exit 1

cd ..

if [ -f .env ]; then
    . .env
fi

exec node wechat/main.js "$@"