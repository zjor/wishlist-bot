#!/bin/bash

if [[ $# != 1 ]] ; then
  echo 'USAGE: db/scripts/force.sh <n - current version>'
  exit -1
fi

MIGRATIONS_DIR=db/migrations

set -x

migrate -database ${MIGRATE_DATABASE_URL} -path ${MIGRATIONS_DIR} force $1