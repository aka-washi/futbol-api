-- =============================================
-- Table: Organization
-- Description: Stores information about football organizations
-- Author: System Generated
-- Created: 2026-02-11
-- Database: PostgreSQL
-- =============================================

CREATE TABLE IF NOT EXISTS Organization (
    org_id BIGSERIAL NOT NULL,
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
    org_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    org_created_by VARCHAR(100) NOT NULL DEFAULT 'admin',
    org_updated_at TIMESTAMP NULL,
    org_updated_by VARCHAR(100) NULL,
    CONSTRAINT PK_Organization_K1 PRIMARY KEY (org_id),
    CONSTRAINT UK_Organization_K2 UNIQUE (org_name),
    CONSTRAINT UK_Organization_K3 UNIQUE (org_display_name),
    CONSTRAINT UK_Organization_K4 UNIQUE (org_abbreviation),
    CONSTRAINT CK_Organization_org_type CHECK (org_type IN ('CLUB', 'FEDERATION', 'CONFEDERATION', 'GOVERNING_BODY')),
    CONSTRAINT FK_Organization_K6_Country_K1 FOREIGN KEY (country_id) REFERENCES Country(cty_id),
    CONSTRAINT FK_Organization_K8_Organization_K1 FOREIGN KEY (parent_organization_id) REFERENCES Organization(org_id)
);

-- Comments for table and columns
COMMENT ON TABLE Organization IS 'Stores information about football organizations including clubs, federations, confederations, and governing bodies.';
COMMENT ON COLUMN Organization.org_id IS 'Primary key identity';
COMMENT ON COLUMN Organization.org_name IS 'Official name of the organization';
COMMENT ON COLUMN Organization.org_display_name IS 'Display name used for UI presentation';
COMMENT ON COLUMN Organization.org_type IS 'Type of organization (CLUB, FEDERATION, CONFEDERATION, GOVERNING_BODY)';
COMMENT ON COLUMN Organization.org_abbreviation IS 'Abbreviation or acronym of the organization';
COMMENT ON COLUMN Organization.country_id IS 'Foreign key to Country table - country where organization is based';
COMMENT ON COLUMN Organization.org_founded IS 'Date when the organization was founded';
COMMENT ON COLUMN Organization.parent_organization_id IS 'Self-referencing FK to parent organization (e.g., confederation for a federation)';
COMMENT ON COLUMN Organization.org_logo IS 'URL to organization logo image';
COMMENT ON COLUMN Organization.org_website IS 'Official website URL';
COMMENT ON COLUMN Organization.org_headquarters IS 'Location of organization headquarters';
COMMENT ON COLUMN Organization.org_description IS 'Description of the organization';
