-- =============================================
-- Table: Season
-- Description: Stores information about football seasons
-- Author: System Generated
-- Created: 2026-02-11
-- Database: PostgreSQL
-- =============================================

CREATE TABLE IF NOT EXISTS Season (
    ssn_id BIGSERIAL NOT NULL,
    ssn_name VARCHAR(100) NOT NULL,
    ssn_display_name VARCHAR(100) NOT NULL,
    ssn_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ssn_created_by VARCHAR(100) NOT NULL DEFAULT 'admin',
    ssn_updated_at TIMESTAMP NULL,
    ssn_updated_by VARCHAR(100) NULL,
    CONSTRAINT PK_Season_K1 PRIMARY KEY (ssn_id),
    CONSTRAINT UK_Season_K2 UNIQUE (ssn_name),
    CONSTRAINT UK_Season_K3 UNIQUE (ssn_display_name)
);

-- Comments for table and columns
COMMENT ON TABLE Season IS 'Stores information about football seasons, which are time periods during which competitions are organized.';
COMMENT ON COLUMN Season.ssn_id IS 'Primary key identity';
COMMENT ON COLUMN Season.ssn_name IS 'Official name of the season (e.g., 2023-2024, 2024/25)';
COMMENT ON COLUMN Season.ssn_display_name IS 'Display name used for UI presentation';
COMMENT ON COLUMN Season.ssn_created_at IS 'Record creation timestamp';
COMMENT ON COLUMN Season.ssn_created_by IS 'User who created the record';
COMMENT ON COLUMN Season.ssn_updated_at IS 'Record last update timestamp';
COMMENT ON COLUMN Season.ssn_updated_by IS 'User who last updated the record';
