-- =============================================
-- Data Insert Script: PointSystem
-- Description: Insert sample point system data
-- Author: System Generated
-- Created: 2026-02-11
-- Database: MSSQL
-- =============================================

SET IDENTITY_INSERT dbo.PointSystem ON;

-- Standard 3-point system (most common)
IF NOT EXISTS (SELECT 1 FROM dbo.PointSystem WHERE pts_name = 'standard_3_point')
BEGIN
    INSERT INTO dbo.PointSystem (pts_id, pts_name, pts_display_name, pts_description, pts_points_for_win, pts_points_for_draw, pts_points_for_loss, pts_points_for_win_on_penalties, pts_points_for_loss_on_penalties, pts_points_for_forfeit_win, pts_points_for_forfeit_loss, pts_created_at, pts_created_by)
    VALUES (1, 'standard_3_point', 'Standard 3-Point System', 'Standard football point system: 3 points for win, 1 for draw, 0 for loss', 3, 1, 0, NULL, NULL, NULL, NULL, GETDATE(), 'system');
END;

-- 2-point system (historical)
IF NOT EXISTS (SELECT 1 FROM dbo.PointSystem WHERE pts_name = 'classic_2_point')
BEGIN
    INSERT INTO dbo.PointSystem (pts_id, pts_name, pts_display_name, pts_description, pts_points_for_win, pts_points_for_draw, pts_points_for_loss, pts_points_for_win_on_penalties, pts_points_for_loss_on_penalties, pts_points_for_forfeit_win, pts_points_for_forfeit_loss, pts_created_at, pts_created_by)
    VALUES (2, 'classic_2_point', 'Classic 2-Point System', 'Historical football point system: 2 points for win, 1 for draw, 0 for loss', 2, 1, 0, NULL, NULL, NULL, NULL, GETDATE(), 'system');
END;

-- Knockout with penalty points
IF NOT EXISTS (SELECT 1 FROM dbo.PointSystem WHERE pts_name = 'knockout_with_penalties')
BEGIN
    INSERT INTO dbo.PointSystem (pts_id, pts_name, pts_display_name, pts_description, pts_points_for_win, pts_points_for_draw, pts_points_for_loss, pts_points_for_win_on_penalties, pts_points_for_loss_on_penalties, pts_points_for_forfeit_win, pts_points_for_forfeit_loss, pts_created_at, pts_created_by)
    VALUES (3, 'knockout_with_penalties', 'Knockout with Penalties', 'Point system for knockout stages: 3 for regulation win, 2 for penalty win, 1 for penalty loss, 0 for regulation loss', 3, 1, 0, 2, 1, NULL, NULL, GETDATE(), 'system');
END;

-- Complete system with forfeits
IF NOT EXISTS (SELECT 1 FROM dbo.PointSystem WHERE pts_name = 'complete_system')
BEGIN
    INSERT INTO dbo.PointSystem (pts_id, pts_name, pts_display_name, pts_description, pts_points_for_win, pts_points_for_draw, pts_points_for_loss, pts_points_for_win_on_penalties, pts_points_for_loss_on_penalties, pts_points_for_forfeit_win, pts_points_for_forfeit_loss, pts_created_at, pts_created_by)
    VALUES (4, 'complete_system', 'Complete Point System', 'Comprehensive point system including forfeits: standard 3-1-0, with 3 for forfeit win and -3 for forfeit loss', 3, 1, 0, 2, 1, 3, -3, GETDATE(), 'system');
END;

SET IDENTITY_INSERT dbo.PointSystem OFF;
GO
