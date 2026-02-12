-- =============================================
-- Data Insert Script: Season
-- Description: Insert sample season data
-- Author: System Generated
-- Created: 2026-02-11
-- Database: H2
-- =============================================

-- Insert sample seasons
MERGE INTO Season (ssn_id, ssn_name, ssn_display_name, ssn_created_at, ssn_created_by)
KEY (ssn_name)
VALUES 
    (1, '2020-2021', '2020/21', CURRENT_TIMESTAMP, 'system'),
    (2, '2021-2022', '2021/22', CURRENT_TIMESTAMP, 'system'),
    (3, '2022-2023', '2022/23', CURRENT_TIMESTAMP, 'system'),
    (4, '2023-2024', '2023/24', CURRENT_TIMESTAMP, 'system'),
    (5, '2024-2025', '2024/25', CURRENT_TIMESTAMP, 'system'),
    (6, '2025-2026', '2025/26', CURRENT_TIMESTAMP, 'system');
