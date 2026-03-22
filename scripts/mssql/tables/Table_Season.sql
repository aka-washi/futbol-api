-- =============================================
-- Table: Season
-- Description: Stores information about football seasons
-- Author: System Generated
-- Created: 2026-02-11
-- =============================================

IF OBJECT_ID('dbo.Season') IS NULL
BEGIN
    CREATE TABLE dbo.Season (
        ssn_id BIGINT IDENTITY(1, 1) NOT NULL,
        ssn_name VARCHAR(100) NOT NULL,
        ssn_display_name VARCHAR(100) NOT NULL,
        ssn_created_at DATETIME2(7) NOT NULL CONSTRAINT DF_Season_ssn_created_at DEFAULT (GETDATE()),
        ssn_created_by VARCHAR(100) NOT NULL CONSTRAINT DF_Season_ssn_created_by DEFAULT ('admin'),
        ssn_updated_at DATETIME2(7) NULL,
        ssn_updated_by VARCHAR(100) NULL,
        CONSTRAINT PK_Season_K1 PRIMARY KEY CLUSTERED (ssn_id),
        CONSTRAINT UK_Season_K2 UNIQUE NONCLUSTERED (ssn_name),
        CONSTRAINT UK_Season_K3 UNIQUE NONCLUSTERED (ssn_display_name)
    );

    -- Table extended properties
    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Stores information about football seasons, which are time periods during which competitions are organized.',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Season';

    -- Column extended properties
    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Primary key identity',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Season',
        @level2type = N'COLUMN', @level2name = N'ssn_id';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Official name of the season (e.g., 2023-2024, 2024/25)',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Season',
        @level2type = N'COLUMN', @level2name = N'ssn_name';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Display name used for UI presentation',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Season',
        @level2type = N'COLUMN', @level2name = N'ssn_display_name';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Record creation timestamp',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Season',
        @level2type = N'COLUMN', @level2name = N'ssn_created_at';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'User who created the record',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Season',
        @level2type = N'COLUMN', @level2name = N'ssn_created_by';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Record last update timestamp',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Season',
        @level2type = N'COLUMN', @level2name = N'ssn_updated_at';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'User who last updated the record',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Season',
        @level2type = N'COLUMN', @level2name = N'ssn_updated_by';
END;
GO
