# Database Scripts for Futbol API

This directory contains database scripts following CBA (Commonwealth Bank of Australia) SQL standards for the Futbol API project.

## Directory Structure

```
scripts/
├── mssql/          # Microsoft SQL Server scripts
├── h2/             # H2 Database scripts  
└── postgres/       # PostgreSQL scripts
```

Each database directory contains:
- **tables/** - Table creation scripts (DDL)
- **data/** - Data insertion scripts (DML)
- **entities/** - Java entity files for reference

## Entities

The following entities are implemented:

1. **Country** - Stores country information including names, codes, and flag URLs
2. **Organization** - Stores football organizations (clubs, federations, confederations, governing bodies)
3. **PointSystem** - Stores point allocation rules for match outcomes
4. **Season** - Stores football season information

## CBA Standards Applied

### File Naming Conventions
- Table creation scripts: `Table_<TableName>.sql`
- Data insertion scripts: `CBA####_<Description>.sql`

### Database Object Naming
- Primary Keys: `PK_<TableName>_K#`
- Foreign Keys: `FK_<TblA>_K#_<TblB>_K#`
- Unique Constraints: `UK_<TableName>_K#`
- Check Constraints: `CK_<TableName>_<colName|desc>`
- Default Constraints: `DF_<TableName>_<colName>`

### Best Practices
- All tables include audit fields (created_at, created_by, updated_at, updated_by)
- Extended properties/comments for table and column descriptions
- IF EXISTS/IF NOT EXISTS checks before creating objects
- Proper constraint naming following CBA conventions
- Identity/Auto-increment primary keys

## Execution Order

For each database type, execute scripts in this order:

1. Execute all table creation scripts:
   - Table_Country.sql
   - Table_Organization.sql (depends on Country)
   - Table_PointSystem.sql
   - Table_Season.sql

2. Execute data insertion scripts:
   - CBA0001_Country_Insert.sql
   - CBA0002_Organization_Insert.sql
   - CBA0003_PointSystem_Insert.sql
   - CBA0004_Season_Insert.sql

## Database-Specific Notes

### MSSQL
- Uses IDENTITY for auto-increment
- Uses DATETIME2(7) for timestamp fields
- Supports extended properties for documentation
- Uses SET IDENTITY_INSERT for explicit ID insertion

### H2
- Uses AUTO_INCREMENT for auto-increment
- Uses TIMESTAMP for timestamp fields
- Supports COMMENT ON for documentation
- Uses MERGE INTO for upsert operations

### PostgreSQL
- Uses BIGSERIAL for auto-increment
- Uses TIMESTAMP for timestamp fields
- Supports COMMENT ON for documentation
- Uses INSERT ... ON CONFLICT for upsert operations
- Requires sequence reset after explicit ID insertion

## Author

System Generated - 2026-02-11

## Additional Information

For more information about CBA SQL Standards, refer to the internal documentation or contact the database architecture team.
