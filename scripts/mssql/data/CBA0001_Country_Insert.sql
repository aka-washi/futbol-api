-- =============================================
-- Data Insert Script: Country
-- Description: Insert sample country data
-- Author: System Generated
-- Created: 2026-02-11
-- Database: MSSQL
-- =============================================

SET IDENTITY_INSERT dbo.Country ON;

-- Insert sample countries
IF NOT EXISTS (SELECT 1 FROM dbo.Country WHERE cty_iso_code = 'USA')
BEGIN
    INSERT INTO dbo.Country (cty_id, cty_name, cty_display_name, cty_code, cty_iso_code, cty_flag_url, cty_created_at, cty_created_by)
    VALUES (1, 'United States', 'United States', 'US', 'USA', 'https://flagcdn.com/us.svg', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Country WHERE cty_iso_code = 'GBR')
BEGIN
    INSERT INTO dbo.Country (cty_id, cty_name, cty_display_name, cty_code, cty_iso_code, cty_flag_url, cty_created_at, cty_created_by)
    VALUES (2, 'United Kingdom', 'United Kingdom', 'GB', 'GBR', 'https://flagcdn.com/gb.svg', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Country WHERE cty_iso_code = 'ESP')
BEGIN
    INSERT INTO dbo.Country (cty_id, cty_name, cty_display_name, cty_code, cty_iso_code, cty_flag_url, cty_created_at, cty_created_by)
    VALUES (3, 'Spain', 'Spain', 'ES', 'ESP', 'https://flagcdn.com/es.svg', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Country WHERE cty_iso_code = 'DEU')
BEGIN
    INSERT INTO dbo.Country (cty_id, cty_name, cty_display_name, cty_code, cty_iso_code, cty_flag_url, cty_created_at, cty_created_by)
    VALUES (4, 'Germany', 'Germany', 'DE', 'DEU', 'https://flagcdn.com/de.svg', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Country WHERE cty_iso_code = 'ITA')
BEGIN
    INSERT INTO dbo.Country (cty_id, cty_name, cty_display_name, cty_code, cty_iso_code, cty_flag_url, cty_created_at, cty_created_by)
    VALUES (5, 'Italy', 'Italy', 'IT', 'ITA', 'https://flagcdn.com/it.svg', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Country WHERE cty_iso_code = 'FRA')
BEGIN
    INSERT INTO dbo.Country (cty_id, cty_name, cty_display_name, cty_code, cty_iso_code, cty_flag_url, cty_created_at, cty_created_by)
    VALUES (6, 'France', 'France', 'FR', 'FRA', 'https://flagcdn.com/fr.svg', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Country WHERE cty_iso_code = 'BRA')
BEGIN
    INSERT INTO dbo.Country (cty_id, cty_name, cty_display_name, cty_code, cty_iso_code, cty_flag_url, cty_created_at, cty_created_by)
    VALUES (7, 'Brazil', 'Brazil', 'BR', 'BRA', 'https://flagcdn.com/br.svg', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Country WHERE cty_iso_code = 'ARG')
BEGIN
    INSERT INTO dbo.Country (cty_id, cty_name, cty_display_name, cty_code, cty_iso_code, cty_flag_url, cty_created_at, cty_created_by)
    VALUES (8, 'Argentina', 'Argentina', 'AR', 'ARG', 'https://flagcdn.com/ar.svg', GETDATE(), 'system');
END;

SET IDENTITY_INSERT dbo.Country OFF;
GO
