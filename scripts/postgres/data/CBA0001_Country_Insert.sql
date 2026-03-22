-- =============================================
-- Data Insert Script: Country
-- Description: Insert sample country data
-- Author: System Generated
-- Created: 2026-02-11
-- Database: PostgreSQL
-- =============================================

-- Insert sample countries
INSERT INTO Country (cty_id, cty_name, cty_display_name, cty_code, cty_iso_code, cty_flag_url, cty_created_at, cty_created_by)
VALUES 
    (1, 'United States', 'United States', 'US', 'USA', 'https://flagcdn.com/us.svg', CURRENT_TIMESTAMP, 'system'),
    (2, 'United Kingdom', 'United Kingdom', 'GB', 'GBR', 'https://flagcdn.com/gb.svg', CURRENT_TIMESTAMP, 'system'),
    (3, 'Spain', 'Spain', 'ES', 'ESP', 'https://flagcdn.com/es.svg', CURRENT_TIMESTAMP, 'system'),
    (4, 'Germany', 'Germany', 'DE', 'DEU', 'https://flagcdn.com/de.svg', CURRENT_TIMESTAMP, 'system'),
    (5, 'Italy', 'Italy', 'IT', 'ITA', 'https://flagcdn.com/it.svg', CURRENT_TIMESTAMP, 'system'),
    (6, 'France', 'France', 'FR', 'FRA', 'https://flagcdn.com/fr.svg', CURRENT_TIMESTAMP, 'system'),
    (7, 'Brazil', 'Brazil', 'BR', 'BRA', 'https://flagcdn.com/br.svg', CURRENT_TIMESTAMP, 'system'),
    (8, 'Argentina', 'Argentina', 'AR', 'ARG', 'https://flagcdn.com/ar.svg', CURRENT_TIMESTAMP, 'system')
ON CONFLICT (cty_iso_code) DO NOTHING;

-- Reset sequence to continue from the highest ID
SELECT setval('country_cty_id_seq', (SELECT MAX(cty_id) FROM Country));
