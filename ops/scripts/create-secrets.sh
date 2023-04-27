#!/bin/bash

source ../../.env

NAMESPACE=app-wishlist-bot
APP=wishlist-bot

kubectl delete secret ${APP}-secrets -n ${NAMESPACE}

kubectl create secret generic ${APP}-secrets \
  --from-literal=TELEGRAM_BOTUSERNAME=${TELEGRAM_USERNAME} \
  --from-literal=TELEGRAM_BOTTOKEN=${TELEGRAM_TOKEN} \
  --from-literal=TELEGRAM_CREATORID=${TELEGRAM_CREATORID} \
  -n ${NAMESPACE}