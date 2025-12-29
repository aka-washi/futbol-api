package com.eagle.futbolapi.features.seasonteam.entity;

import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.shared.BaseEntity;
import com.eagle.futbolapi.features.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * SeasonTeam Entity
 * Represents the many-to-many relationship between seasons and teams
 * Teams can change between seasons due to sales, relegation, or other factors
 */
@Entity
@Table(name = "season_team", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"season_id", "team_id"})
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "se_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "se_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "se_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "se_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "se_updated_by", length = 100))
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeasonTeam extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Season season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Team team;

    @NotNull
    @Column(name = "se_joined_date", nullable = false)
    private LocalDate joinedDate;

    @Column(name = "se_left_date")
    private LocalDate leftDate;

    @Column(name = "se_is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
