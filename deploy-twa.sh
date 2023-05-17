#!/bin/bash

cd twa

set -x
pnpm build
surge dist twa-wishlist-bot.surge.sh