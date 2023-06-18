#!/bin/bash

if [[ $# != 1 ]] ; then
  echo 'USAGE: db/scripts/create.sh <migration name>'
  exit -1
fi

MIGRATIONS_DIR=db/migrations

set -x
migrate create -ext sql -dir ${MIGRATIONS_DIR} -seq $1