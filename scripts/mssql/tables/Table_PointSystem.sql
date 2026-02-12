-- =============================================
-- Table: PointSystem
-- Description: Stores configuration for how points are awarded to teams based on match outcomes
-- Author: System Generated
-- Created: 2026-02-11
-- =============================================

IF OBJECT_ID('dbo.PointSystem') IS NULL
BEGIN
    CREATE TABLE dbo.PointSystem (
        pts_id BIGINT IDENTITY(1, 1) NOT NULL,
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
        pts_created_at DATETIME2(7) NOT NULL CONSTRAINT DF_PointSystem_pts_created_at DEFAULT (GETDATE()),
        pts_created_by VARCHAR(100) NOT NULL CONSTRAINT DF_PointSystem_pts_created_by DEFAULT ('admin'),
        pts_updated_at DATETIME2(7) NULL,
        pts_updated_by VARCHAR(100) NULL,
        CONSTRAINT PK_PointSystem_K1 PRIMARY KEY CLUSTERED (pts_id),
        CONSTRAINT UK_PointSystem_K2 UNIQUE NONCLUSTERED (pts_name),
        CONSTRAINT UK_PointSystem_K3 UNIQUE NONCLUSTERED (pts_display_name)
    );

    -- Table extended properties
    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Stores configuration for how points are awarded to teams based on match outcomes in football competitions.',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem';

    -- Column extended properties
    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Primary key identity',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_id';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Unique identifier name of the point system',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_name';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Display name used for UI presentation',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_display_name';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Optional description of the point system usage and context',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_description';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Points awarded for a standard regulation win',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_points_for_win';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Points awarded for a draw match',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_points_for_draw';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Points awarded for a regulation loss',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_points_for_loss';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Points awarded for winning after penalty shootout',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_points_for_win_on_penalties';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Points awarded for losing after penalty shootout',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_points_for_loss_on_penalties';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Points awarded when opponent forfeits and team wins by default',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_points_for_forfeit_win';

    EXEC sp_addextendedproperty 
        @name = N'MS_Description', 
        @value = N'Points awarded when team forfeits the match',
        @level0type = N'SCHEMA', @level0name = N'dbo',
        @level1type = N'TABLE', @level1name = N'PointSystem',
        @level2type = N'COLUMN', @level2name = N'pts_points_for_forfeit_loss';
END;
GO
