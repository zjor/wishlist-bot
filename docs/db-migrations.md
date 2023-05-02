# Database migrations

## Overview

The database schema is changed via migrations. 
A language-agnostic tool [golang-migrate](https://github.com/golang-migrate/migrate) is used.

## Scenarios

1. Create a new migration

```bash
$> migrate create -ext sql -dir db/migrations -seq <migration_name>
```

2. Apply migration

```bash
migrate -database ${MIGRATE_DATABASE_URL} -path db/migrations up
```

3. Force migration version

```bash
migrate -database ${MIGRATE_DATABASE_URL} -path db/migrations force N
```

4. Downgrade 1 migration

```bash
migrate -database ${MIGRATE_DATABASE_URL} -path db/migrations down 1
```