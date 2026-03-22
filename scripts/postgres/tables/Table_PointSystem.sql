-- =============================================
-- Table: PointSystem
-- Description: Stores configuration for how points are awarded to teams based on match outcomes
-- Author: System Generated
-- Created: 2026-02-11
-- Database: PostgreSQL
-- =============================================

CREATE TABLE IF NOT EXISTS PointSystem (
    pts_id BIGSERIAL NOT NULL,
    pts_name VARCHAR(100) NOT NULL,
    pts_display_name VARCHAR(100) NOT NULL,
    pts_description VARCHAR(255) NULL,
    pts_points_for_win INT NOT NULL,
    pts_points_for_draw INT NOT NULL,
    pts_points_for_loss INT NOT NULL,
    pts_points_for_win_on_penalties INT NULL,
    pts_points_for_loss_on_penalties INT NULL,
    pts_points_for_forfeit_win INT NULL,
    pts_points_for_forfeit_loss INT NULL,
    pts_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pts_created_by VARCHAR(100) NOT NULL DEFAULT 'admin',
    pts_updated_at TIMESTAMP NULL,
    pts_updated_by VARCHAR(100) NULL,
    CONSTRAINT PK_PointSystem_K1 PRIMARY KEY (pts_id),
    CONSTRAINT UK_PointSystem_K2 UNIQUE (pts_name),
    CONSTRAINT UK_PointSystem_K3 UNIQUE (pts_display_name)
);

-- Comments for table and columns
COMMENT ON TABLE PointSystem IS 'Stores configuration for how points are awarded to teams based on match outcomes in football competitions.';
COMMENT ON COLUMN PointSystem.pts_id IS 'Primary key identity';
COMMENT ON COLUMN PointSystem.pts_name IS 'Unique identifier name of the point system';
COMMENT ON COLUMN PointSystem.pts_display_name IS 'Display name used for UI presentation';
COMMENT ON COLUMN PointSystem.pts_description IS 'Optional description of the point system usage and context';
COMMENT ON COLUMN PointSystem.pts_points_for_win IS 'Points awarded for a standard regulation win';
COMMENT ON COLUMN PointSystem.pts_points_for_draw IS 'Points awarded for a draw match';
COMMENT ON COLUMN PointSystem.pts_points_for_loss IS 'Points awarded for a regulation loss';
COMMENT ON COLUMN PointSystem.pts_points_for_win_on_penalties IS 'Points awarded for winning after penalty shootout';
COMMENT ON COLUMN PointSystem.pts_points_for_loss_on_penalties IS 'Points awarded for losing after penalty shootout';
COMMENT ON COLUMN PointSystem.pts_points_for_forfeit_win IS 'Points awarded when opponent forfeits and team wins by default';
COMMENT ON COLUMN PointSystem.pts_points_for_forfeit_loss IS 'Points awarded when team forfeits the match';
