# PostgreSQL Database Scripts

This directory contains PostgreSQL database scripts following CBA standards.

## Contents

- **tables/** - DDL scripts for table creation
- **data/** - DML scripts for data insertion
- **entities/** - Java entity files for reference

## Usage

### Prerequisites
- PostgreSQL 12 or later
- Appropriate database permissions (CREATE TABLE, INSERT, etc.)

### Execution Steps

1. Connect to your PostgreSQL instance
2. Select or create your target database
3. Run table creation scripts in order:
   ```bash
   psql -d <database> -f tables/Table_Country.sql
   psql -d <database> -f tables/Table_Organization.sql
   psql -d <database> -f tables/Table_PointSystem.sql
   psql -d <database> -f tables/Table_Season.sql
   ```

4. Run data insertion scripts:
   ```bash
   psql -d <database> -f data/CBA0001_Country_Insert.sql
   psql -d <database> -f data/CBA0002_Organization_Insert.sql
   psql -d <database> -f data/CBA0003_PointSystem_Insert.sql
   psql -d <database> -f data/CBA0004_Season_Insert.sql
   ```

### Command Line All-in-One

```bash
# Execute all scripts in sequence
for file in tables/*.sql; do psql -d futboldb -f "$file"; done
for file in data/*.sql; do psql -d futboldb -f "$file"; done
```

### Docker PostgreSQL

```bash
# Using Docker container
docker exec -i postgres_container psql -U postgres -d futboldb < tables/Table_Country.sql
docker exec -i postgres_container psql -U postgres -d futboldb < tables/Table_Organization.sql
docker exec -i postgres_container psql -U postgres -d futboldb < tables/Table_PointSystem.sql
docker exec -i postgres_container psql -U postgres -d futboldb < tables/Table_Season.sql

docker exec -i postgres_container psql -U postgres -d futboldb < data/CBA0001_Country_Insert.sql
docker exec -i postgres_container psql -U postgres -d futboldb < data/CBA0002_Organization_Insert.sql
docker exec -i postgres_container psql -U postgres -d futboldb < data/CBA0003_PointSystem_Insert.sql
docker exec -i postgres_container psql -U postgres -d futboldb < data/CBA0004_Season_Insert.sql
```

## PostgreSQL Specific Features

- **BIGSERIAL**: Primary keys use BIGSERIAL for auto-increment with sequences
- **TIMESTAMP**: Timestamp fields use TIMESTAMP type
- **COMMENT ON**: Uses COMMENT ON syntax for documentation
- **ON CONFLICT**: Data scripts use INSERT ... ON CONFLICT DO NOTHING for upserts
- **Sequence Management**: Scripts reset sequences after explicit ID insertion
- **CREATE IF NOT EXISTS**: All CREATE statements check for table existence

## Notes

- Scripts are idempotent - safe to run multiple times
- ON CONFLICT ensures data is not duplicated on repeated runs
- Sequence reset commands ensure auto-increment continues correctly
- All foreign key constraints are defined inline with table creation
- Compatible with PostgreSQL cloud services (AWS RDS, Azure Database, etc.)
- Scripts follow PostgreSQL best practices for production environments

## Spring Boot Integration

For Spring Boot configuration:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/futboldb
    username: postgres
    password: password
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
    defer-datasource-initialization: true
  sql:
    init:
      mode: always
```
