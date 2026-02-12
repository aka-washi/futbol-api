-- =============================================
-- Data Insert Script: Season
-- Description: Insert sample season data
-- Author: System Generated
-- Created: 2026-02-11
-- Database: MSSQL
-- =============================================

SET IDENTITY_INSERT dbo.Season ON;

-- Recent seasons
IF NOT EXISTS (SELECT 1 FROM dbo.Season WHERE ssn_name = '2020-2021')
BEGIN
    INSERT INTO dbo.Season (ssn_id, ssn_name, ssn_display_name, ssn_created_at, ssn_created_by)
    VALUES (1, '2020-2021', '2020/21', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Season WHERE ssn_name = '2021-2022')
BEGIN
    INSERT INTO dbo.Season (ssn_id, ssn_name, ssn_display_name, ssn_created_at, ssn_created_by)
    VALUES (2, '2021-2022', '2021/22', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Season WHERE ssn_name = '2022-2023')
BEGIN
    INSERT INTO dbo.Season (ssn_id, ssn_name, ssn_display_name, ssn_created_at, ssn_created_by)
    VALUES (3, '2022-2023', '2022/23', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Season WHERE ssn_name = '2023-2024')
BEGIN
    INSERT INTO dbo.Season (ssn_id, ssn_name, ssn_display_name, ssn_created_at, ssn_created_by)
    VALUES (4, '2023-2024', '2023/24', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Season WHERE ssn_name = '2024-2025')
BEGIN
    INSERT INTO dbo.Season (ssn_id, ssn_name, ssn_display_name, ssn_created_at, ssn_created_by)
    VALUES (5, '2024-2025', '2024/25', GETDATE(), 'system');
END;

IF NOT EXISTS (SELECT 1 FROM dbo.Season WHERE ssn_name = '2025-2026')
BEGIN
    INSERT INTO dbo.Season (ssn_id, ssn_name, ssn_display_name, ssn_created_at, ssn_created_by)
    VALUES (6, '2025-2026', '2025/26', GETDATE(), 'system');
END;

SET IDENTITY_INSERT dbo.Season OFF;
GO
