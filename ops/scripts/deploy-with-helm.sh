#!/bin/bash

NS=app-wishlist-bot
APP=wishlist-bot
VERSION=$(git rev-parse --short HEAD)

set -x

helm upgrade --namespace ${NS} --create-namespace --install ${APP} --set version=${VERSION} ./ops/wishlist-bot