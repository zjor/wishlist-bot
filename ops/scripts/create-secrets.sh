#!/bin/bash

source .env
cat .env

NAMESPACE=app-wishlist-bot
APP=wishlist-bot

kubectl delete secret ${APP}-secrets -n ${NAMESPACE}

kubectl create secret generic ${APP}-secrets \
  --from-literal=SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL} \
  --from-literal=SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME} \
  --from-literal=SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD} \
  --from-literal=TELEGRAM_BOTUSERNAME=${TELEGRAM_BOTUSERNAME} \
  --from-literal=TELEGRAM_BOTTOKEN=${TELEGRAM_BOTTOKEN} \
  --from-literal=TELEGRAM_CREATORID=${TELEGRAM_CREATORID} \
  --from-literal=OPENGRAPH_APIKEY=${OPENGRAPH_APIKEY} \
  --from-literal=CLOUDINARY_URL=${CLOUDINARY_URL} \
  --from-literal=AUTH_BASIC_LOGIN=${AUTH_BASIC_LOGIN} \
  --from-literal=AUTH_BASIC_PASSWORD=${AUTH_BASIC_PASSWORD} \
  -n ${NAMESPACE}