#!/bin/bash

source .env
cat .env

NAMESPACE=app-wishlist-bot
APP=wishlist-bot

kubectl delete secret ${APP}-secrets -n ${NAMESPACE}

kubectl create secret generic ${APP}-secrets \
  --from-literal=TELEGRAM_BOTUSERNAME=${TELEGRAM_BOTUSERNAME} \
  --from-literal=TELEGRAM_BOTTOKEN=${TELEGRAM_BOTTOKEN} \
  --from-literal=TELEGRAM_CREATORID=${TELEGRAM_CREATORID} \
  -n ${NAMESPACE}