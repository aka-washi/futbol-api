-- =============================================
-- Table: Country
-- Description: Stores information about countries including their names, codes, and flag URLs.
-- Author: System Generated
-- Created: 2026-02-11
-- =============================================

IF OBJECT_ID('dbo.Country') IS NULL
BEGIN
    CREATE TABLE dbo.Country (
        cty_id BIGINT IDENTITY(1, 1) NOT NULL,
        cty_name VARCHAR(100) NOT NULL,
        cty_display_name VARCHAR(100) NOT NULL,
        cty_code VARCHAR(10) NULL,
        cty_iso_code CHAR(3) NOT NULL,
        cty_flag_url VARCHAR(500) NULL,
        cty_created_at DATETIME2(7) NOT NULL CONSTRAINT DF_Country_cty_created_at DEFAULT (GETDATE()),
        cty_created_by VARCHAR(100) NOT NULL CONSTRAINT DF_Country_cty_created_by DEFAULT ('admin'),
        cty_updated_at DATETIME2(7) NULL,
        cty_updated_by VARCHAR(100) NULL,
        CONSTRAINT PK_Country_K1 PRIMARY KEY CLUSTERED (cty_id),
        CONSTRAINT UK_Country_K2 UNIQUE NONCLUSTERED (cty_name),
        CONSTRAINT UK_Country_K3 UNIQUE NONCLUSTERED (cty_display_name),
        CONSTRAINT UK_Country_K4 UNIQUE NONCLUSTERED (cty_code),
        CONSTRAINT UK_Country_K5 UNIQUE NONCLUSTERED (cty_iso_code)
    );

    -- Table extended properties
    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Stores information about countries including their names, codes, and flag URLs.',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country';

    -- Column extended properties
    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Primary key identity',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_id';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'The official name of the country',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_name';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'The display name used for UI presentation',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_display_name';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Short country code (e.g., US, GB)',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_code';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'ISO 3166-1 alpha-3 country code (e.g., USA, GBR)',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_iso_code';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'URL to the country flag image',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_flag_url';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Record creation timestamp',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_created_at';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'User who created the record',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_created_by';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Record last update timestamp',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_updated_at';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'User who last updated the record',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Country',
        @level2type = N'COLUMN', @level2name = N'cty_updated_by';
END;
GO
