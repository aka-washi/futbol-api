-- =============================================
-- Data Insert Script: Organization
-- Description: Insert sample organization data
-- Author: System Generated
-- Created: 2026-02-11
-- Database: PostgreSQL
-- =============================================

-- Insert sample organizations
INSERT INTO Organization (org_id, org_name, org_display_name, org_type, org_abbreviation, country_id, org_founded, parent_organization_id, org_logo, org_website, org_headquarters, org_description, org_created_at, org_created_by)
VALUES 
    (1, 'Fédération Internationale de Football Association', 'FIFA', 'GOVERNING_BODY', 'FIFA', NULL, '1904-05-21', NULL, 'https://upload.wikimedia.org/wikipedia/commons/a/aa/FIFA_logo_without_slogan.svg', 'https://www.fifa.com', 'Zürich, Switzerland', 'International governing body of football', CURRENT_TIMESTAMP, 'system'),
    (2, 'Union of European Football Associations', 'UEFA', 'CONFEDERATION', 'UEFA', NULL, '1954-06-15', 1, 'https://upload.wikimedia.org/wikipedia/en/d/d6/UEFA_logo.svg', 'https://www.uefa.com', 'Nyon, Switzerland', 'European football confederation', CURRENT_TIMESTAMP, 'system'),
    (3, 'Confederación Sudamericana de Fútbol', 'CONMEBOL', 'CONFEDERATION', 'CONMEBOL', NULL, '1916-07-09', 1, 'https://upload.wikimedia.org/wikipedia/commons/7/79/CONMEBOL_logo.svg', 'https://www.conmebol.com', 'Luque, Paraguay', 'South American football confederation', CURRENT_TIMESTAMP, 'system'),
    (4, 'Real Federación Española de Fútbol', 'RFEF', 'FEDERATION', 'RFEF', 3, '1909-09-29', 2, 'https://upload.wikimedia.org/wikipedia/en/3/31/Royal_Spanish_Football_Federation.svg', 'https://www.rfef.es', 'Madrid, Spain', 'Spanish football federation', CURRENT_TIMESTAMP, 'system'),
    (5, 'Deutscher Fußball-Bund', 'DFB', 'FEDERATION', 'DFB', 4, '1900-01-28', 2, 'https://upload.wikimedia.org/wikipedia/en/8/8d/DFB_Logo_2017.svg', 'https://www.dfb.de', 'Frankfurt, Germany', 'German football federation', CURRENT_TIMESTAMP, 'system'),
    (6, 'Real Madrid Club de Fútbol', 'Real Madrid', 'CLUB', 'RMA', 3, '1902-03-06', NULL, 'https://upload.wikimedia.org/wikipedia/en/5/56/Real_Madrid_CF.svg', 'https://www.realmadrid.com', 'Madrid, Spain', 'Spanish professional football club', CURRENT_TIMESTAMP, 'system'),
    (7, 'Futbol Club Barcelona', 'FC Barcelona', 'CLUB', 'FCB', 3, '1899-11-29', NULL, 'https://upload.wikimedia.org/wikipedia/en/4/47/FC_Barcelona_%28crest%29.svg', 'https://www.fcbarcelona.com', 'Barcelona, Spain', 'Spanish professional football club', CURRENT_TIMESTAMP, 'system')
ON CONFLICT (org_abbreviation) DO NOTHING;

-- Reset sequence to continue from the highest ID
SELECT setval('organization_org_id_seq', (SELECT MAX(org_id) FROM Organization));
