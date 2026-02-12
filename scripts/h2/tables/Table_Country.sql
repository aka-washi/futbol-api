-- =============================================
-- Table: Country
-- Description: Stores information about countries including their names, codes, and flag URLs.
-- Author: System Generated
-- Created: 2026-02-11
-- Database: H2
-- =============================================

CREATE TABLE IF NOT EXISTS Country (
    cty_id BIGINT AUTO_INCREMENT NOT NULL,
    cty_name VARCHAR(100) NOT NULL,
    cty_display_name VARCHAR(100) NOT NULL,
    cty_code VARCHAR(10) NULL,
    cty_iso_code CHAR(3) NOT NULL,
    cty_flag_url VARCHAR(500) NULL,
    cty_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cty_created_by VARCHAR(100) NOT NULL DEFAULT 'admin',
    cty_updated_at TIMESTAMP NULL,
    cty_updated_by VARCHAR(100) NULL,
    CONSTRAINT PK_Country_K1 PRIMARY KEY (cty_id),
    CONSTRAINT UK_Country_K2 UNIQUE (cty_name),
    CONSTRAINT UK_Country_K3 UNIQUE (cty_display_name),
    CONSTRAINT UK_Country_K4 UNIQUE (cty_code),
    CONSTRAINT UK_Country_K5 UNIQUE (cty_iso_code)
);

-- Comments for table and columns
COMMENT ON TABLE Country IS 'Stores information about countries including their names, codes, and flag URLs.';
COMMENT ON COLUMN Country.cty_id IS 'Primary key identity';
COMMENT ON COLUMN Country.cty_name IS 'The official name of the country';
COMMENT ON COLUMN Country.cty_display_name IS 'The display name used for UI presentation';
COMMENT ON COLUMN Country.cty_code IS 'Short country code (e.g., US, GB)';
COMMENT ON COLUMN Country.cty_iso_code IS 'ISO 3166-1 alpha-3 country code (e.g., USA, GBR)';
COMMENT ON COLUMN Country.cty_flag_url IS 'URL to the country flag image';
COMMENT ON COLUMN Country.cty_created_at IS 'Record creation timestamp';
COMMENT ON COLUMN Country.cty_created_by IS 'User who created the record';
COMMENT ON COLUMN Country.cty_updated_at IS 'Record last update timestamp';
COMMENT ON COLUMN Country.cty_updated_by IS 'User who last updated the record';
