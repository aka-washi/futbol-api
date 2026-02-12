# H2 Database Scripts

This directory contains H2 Database scripts following CBA standards.

## Contents

- **tables/** - DDL scripts for table creation
- **data/** - DML scripts for data insertion
- **entities/** - Java entity files for reference

## Usage

### Prerequisites
- H2 Database version 2.0 or later
- Appropriate database permissions

### Execution Steps

1. Connect to your H2 database
2. Run table creation scripts in order:
   ```sql
   RUNSCRIPT FROM 'tables/Table_Country.sql';
   RUNSCRIPT FROM 'tables/Table_Organization.sql';
   RUNSCRIPT FROM 'tables/Table_PointSystem.sql';
   RUNSCRIPT FROM 'tables/Table_Season.sql';
   ```

3. Run data insertion scripts:
   ```sql
   RUNSCRIPT FROM 'data/CBA0001_Country_Insert.sql';
   RUNSCRIPT FROM 'data/CBA0002_Organization_Insert.sql';
   RUNSCRIPT FROM 'data/CBA0003_PointSystem_Insert.sql';
   RUNSCRIPT FROM 'data/CBA0004_Season_Insert.sql';
   ```

### Spring Boot Integration

If using Spring Boot with H2, you can configure these scripts to run automatically:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:futboldb
    driverClassName: org.h2.Driver
  h2:
    console:
      enabled: true
  jpa:
    defer-datasource-initialization: true
  sql:
    init:
      mode: always
      schema-locations: 
        - classpath:scripts/h2/tables/Table_Country.sql
        - classpath:scripts/h2/tables/Table_Organization.sql
        - classpath:scripts/h2/tables/Table_PointSystem.sql
        - classpath:scripts/h2/tables/Table_Season.sql
      data-locations:
        - classpath:scripts/h2/data/CBA0001_Country_Insert.sql
        - classpath:scripts/h2/data/CBA0002_Organization_Insert.sql
        - classpath:scripts/h2/data/CBA0003_PointSystem_Insert.sql
        - classpath:scripts/h2/data/CBA0004_Season_Insert.sql
```

## H2 Specific Features

- **AUTO_INCREMENT**: Primary keys use AUTO_INCREMENT for auto-increment
- **TIMESTAMP**: Timestamp fields use TIMESTAMP type
- **COMMENT ON**: Uses COMMENT ON syntax for documentation
- **MERGE INTO**: Data scripts use MERGE INTO for upsert operations
- **CREATE IF NOT EXISTS**: All CREATE statements check for table existence

## Notes

- Scripts are idempotent - safe to run multiple times
- MERGE INTO ensures data is not duplicated on repeated runs
- All foreign key constraints are defined inline with table creation
- Compatible with both file-based and in-memory H2 databases
- Suitable for development and testing environments
