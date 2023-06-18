#!/bin/bash

if [[ -z ${MIGRATE_DATABASE_URL} ]]; then
  echo "MIGRATE_DATABASE_URL variable is not set"
  exit -1
fi

MIGRATIONS_DIR=db/migrations

set -x

migrate -database ${MIGRATE_DATABASE_URL} -path ${MIGRATIONS_DIR} up