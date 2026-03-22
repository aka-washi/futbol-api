-- =============================================
-- Table: Organization
-- Description: Stores information about football organizations (clubs, federations, confederations, governing bodies)
-- Author: System Generated
-- Created: 2026-02-11
-- =============================================

IF OBJECT_ID('dbo.Organization') IS NULL
BEGIN
    CREATE TABLE dbo.Organization (
        org_id BIGINT IDENTITY(1, 1) NOT NULL,
        org_name VARCHAR(100) NOT NULL,
        org_display_name VARCHAR(100) NOT NULL,
        org_type VARCHAR(25) NOT NULL,
        org_abbreviation VARCHAR(20) NOT NULL,
        country_id BIGINT NULL,
        org_founded DATE NULL,
        parent_organization_id BIGINT NULL,
        org_logo VARCHAR(500) NULL,
        org_website VARCHAR(500) NULL,
        org_headquarters VARCHAR(200) NULL,
        org_description VARCHAR(500) NULL,
        org_created_at DATETIME2(7) NOT NULL CONSTRAINT DF_Organization_org_created_at DEFAULT (GETDATE()),
        org_created_by VARCHAR(100) NOT NULL CONSTRAINT DF_Organization_org_created_by DEFAULT ('admin'),
        org_updated_at DATETIME2(7) NULL,
        org_updated_by VARCHAR(100) NULL,
        CONSTRAINT PK_Organization_K1 PRIMARY KEY CLUSTERED (org_id),
        CONSTRAINT UK_Organization_K2 UNIQUE NONCLUSTERED (org_name),
        CONSTRAINT UK_Organization_K3 UNIQUE NONCLUSTERED (org_display_name),
        CONSTRAINT UK_Organization_K4 UNIQUE NONCLUSTERED (org_abbreviation),
        CONSTRAINT CK_Organization_org_type CHECK (org_type IN ('CLUB', 'FEDERATION', 'CONFEDERATION', 'GOVERNING_BODY'))
    );

    -- Table extended properties
    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Stores information about football organizations including clubs, federations, confederations, and governing bodies.',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization';

    -- Column extended properties
    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Primary key identity',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_id';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Official name of the organization',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_name';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Display name used for UI presentation',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_display_name';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Type of organization (CLUB, FEDERATION, CONFEDERATION, GOVERNING_BODY)',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_type';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Abbreviation or acronym of the organization',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_abbreviation';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Foreign key to Country table - country where organization is based',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'country_id';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Date when the organization was founded',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_founded';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Self-referencing FK to parent organization (e.g., confederation for a federation)',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'parent_organization_id';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'URL to organization logo image',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_logo';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Official website URL',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_website';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Location of organization headquarters',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_headquarters';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Description of the organization',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'Organization',
        @level2type = N'COLUMN', @level2name = N'org_description';
END;
GO

-- Foreign key constraints
IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_Organization_K6_Country_K1')
BEGIN
    ALTER TABLE dbo.Organization
    ADD CONSTRAINT FK_Organization_K6_Country_K1 
    FOREIGN KEY (country_id) 
    REFERENCES dbo.Country (cty_id);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_Organization_K8_Organization_K1')
BEGIN
    ALTER TABLE dbo.Organization
    ADD CONSTRAINT FK_Organization_K8_Organization_K1 
    FOREIGN KEY (parent_organization_id) 
    REFERENCES dbo.Organization (org_id);
END;
GO
