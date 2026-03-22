-- =============================================
-- Data Insert Script: PointSystem
-- Description: Insert sample point system data
-- Author: System Generated
-- Created: 2026-02-11
-- Database: PostgreSQL
-- =============================================

-- Insert sample point systems
INSERT INTO PointSystem (pts_id, pts_name, pts_display_name, pts_description, pts_points_for_win, pts_points_for_draw, pts_points_for_loss, pts_points_for_win_on_penalties, pts_points_for_loss_on_penalties, pts_points_for_forfeit_win, pts_points_for_forfeit_loss, pts_created_at, pts_created_by)
VALUES 
    (1, 'standard_3_point', 'Standard 3-Point System', 'Standard football point system: 3 points for win, 1 for draw, 0 for loss', 3, 1, 0, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'system'),
    (2, 'classic_2_point', 'Classic 2-Point System', 'Historical football point system: 2 points for win, 1 for draw, 0 for loss', 2, 1, 0, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, 'system'),
    (3, 'knockout_with_penalties', 'Knockout with Penalties', 'Point system for knockout stages: 3 for regulation win, 2 for penalty win, 1 for penalty loss, 0 for regulation loss', 3, 1, 0, 2, 1, NULL, NULL, CURRENT_TIMESTAMP, 'system'),
    (4, 'complete_system', 'Complete Point System', 'Comprehensive point system including forfeits: standard 3-1-0, with 3 for forfeit win and -3 for forfeit loss', 3, 1, 0, 2, 1, 3, -3, CURRENT_TIMESTAMP, 'system')
ON CONFLICT (pts_name) DO NOTHING;

-- Reset sequence to continue from the highest ID
SELECT setval('pointsystem_pts_id_seq', (SELECT MAX(pts_id) FROM PointSystem));
