#!/bin/bash

if [[ $# != 1 ]] ; then
  echo 'USAGE: db/scripts/downgrade.sh <n - number of migrations to rollback>'
  exit -1
fi

MIGRATIONS_DIR=db/migrations

set -x

migrate -database ${MIGRATE_DATABASE_URL} -path ${MIGRATIONS_DIR} down $1