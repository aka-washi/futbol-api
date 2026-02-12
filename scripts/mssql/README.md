# MSSQL Database Scripts

This directory contains Microsoft SQL Server database scripts following CBA standards.

## Contents

- **tables/** - DDL scripts for table creation
- **data/** - DML scripts for data insertion
- **entities/** - Java entity files for reference

## Usage

### Prerequisites
- SQL Server 2016 or later
- Appropriate database permissions (CREATE TABLE, INSERT, etc.)

### Execution Steps

1. Connect to your SQL Server instance
2. Select or create your target database
3. Run table creation scripts in order:
   ```sql
   -- Execute in SQL Server Management Studio or sqlcmd
   :r tables/Table_Country.sql
   :r tables/Table_Organization.sql
   :r tables/Table_PointSystem.sql
   :r tables/Table_Season.sql
   ```

4. Run data insertion scripts:
   ```sql
   :r data/CBA0001_Country_Insert.sql
   :r data/CBA0002_Organization_Insert.sql
   :r data/CBA0003_PointSystem_Insert.sql
   :r data/CBA0004_Season_Insert.sql
   ```

### Command Line Execution

```bash
# Using sqlcmd
sqlcmd -S <server> -d <database> -i tables/Table_Country.sql
sqlcmd -S <server> -d <database> -i tables/Table_Organization.sql
sqlcmd -S <server> -d <database> -i tables/Table_PointSystem.sql
sqlcmd -S <server> -d <database> -i tables/Table_Season.sql

sqlcmd -S <server> -d <database> -i data/CBA0001_Country_Insert.sql
sqlcmd -S <server> -d <database> -i data/CBA0002_Organization_Insert.sql
sqlcmd -S <server> -d <database> -i data/CBA0003_PointSystem_Insert.sql
sqlcmd -S <server> -d <database> -i data/CBA0004_Season_Insert.sql
```

## MSSQL Specific Features

- **Extended Properties**: All tables and columns have MS_Description properties for documentation
- **IDENTITY Columns**: Primary keys use IDENTITY(1,1) for auto-increment
- **DATETIME2**: Timestamp fields use DATETIME2(7) for precision
- **GO Batches**: Scripts use GO for batch separation
- **IF OBJECT_ID**: All CREATE statements check for object existence

## Notes

- Scripts are idempotent - safe to run multiple times
- Data insertion scripts use SET IDENTITY_INSERT for explicit ID values
- Foreign key constraints are created after tables exist
- All scripts include error handling and existence checks
