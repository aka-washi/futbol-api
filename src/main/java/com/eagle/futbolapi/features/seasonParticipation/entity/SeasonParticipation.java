package com.eagle.futbolapi.features.seasonParticipation.entity;

import java.time.LocalDate;
import java.util.Objects;

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

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Season Team in the football database.
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "season_team", uniqueConstraints = {
    @UniqueConstraint(name = "uk_season_team_natural", columnNames = {
        "season_id",
        "team_id"
    })
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "stm_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "stm_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "stm_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "stm_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "stm_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonParticipation extends BaseEntity {

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "season_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Season season;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Team team;

  @NotNull
  @Column(name = "stm_joined_date", nullable = false)
  private LocalDate joinedDate;

  @Column(name = "stm_left_date")
  private LocalDate leftDate;

  @Override
  public int hashCode() {
    return Objects.hash(
        season,
        team,
        joinedDate,
        leftDate);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof SeasonParticipation))
      return false;
    SeasonParticipation other = (SeasonParticipation) obj;
    return Objects.equals(season, other.season)
        && Objects.equals(team, other.team)
        && Objects.equals(joinedDate, other.joinedDate)
        && Objects.equals(leftDate, other.leftDate);
  }
}
