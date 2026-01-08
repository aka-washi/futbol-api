package com.eagle.futbolapi.features.standing.entity;

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

import com.eagle.futbolapi.features.shared.BaseEntity;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "standing", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "stage_id", "team_id" })
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "sd_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "sd_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "sd_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "sd_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "sd_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Standing extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stage_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Stage stage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Team team;

  @Column(name = "sd_position", nullable = false)
  private Integer position;

  @Column(name = "sd_played", nullable = false)
  @Builder.Default
  private Integer played = 0;

  @Column(name = "sd_won", nullable = false)
  @Builder.Default
  private Integer won = 0;

  @Column(name = "sd_drawn", nullable = false)
  @Builder.Default
  private Integer drawn = 0;

  @Column(name = "sd_lost", nullable = false)
  @Builder.Default
  private Integer lost = 0;

  @Column(name = "sd_goals_for", nullable = false)
  @Builder.Default
  private Integer goalsFor = 0;

  @Column(name = "sd_goals_against", nullable = false)
  @Builder.Default
  private Integer goalsAgainst = 0;

  @Column(name = "sd_goal_difference", nullable = false)
  @Builder.Default
  private Integer goalDifference = 0;

  @Column(name = "sd_points", nullable = false)
  @Builder.Default
  private Integer points = 0;

  @Column(name = "sd_form", length = 10)
  private String form; // Last 5 matches (e.g., "WWDLW")

  @Column(name = "sd_home_won")
  @Builder.Default
  private Integer homeWon = 0;

  @Column(name = "sd_home_drawn")
  @Builder.Default
  private Integer homeDrawn = 0;

  @Column(name = "sd_home_lost")
  @Builder.Default
  private Integer homeLost = 0;

  @Column(name = "sd_away_won")
  @Builder.Default
  private Integer awayWon = 0;

  @Column(name = "sd_away_drawn")
  @Builder.Default
  private Integer awayDrawn = 0;

  @Column(name = "sd_away_lost")
  @Builder.Default
  private Integer awayLost = 0;
}
