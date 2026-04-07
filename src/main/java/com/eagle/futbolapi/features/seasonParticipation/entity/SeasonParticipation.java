package com.eagle.futbolapi.features.seasonParticipation.entity;

import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "season_participation", uniqueConstraints = {
    @UniqueConstraint(name = "uk_season_participation_natural", columnNames = {
        "spt_season_id", "spt_team_id"
    })
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "spt_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "spt_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "spt_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "spt_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "spt_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonParticipation extends BaseEntity {

  @NotNull
  @UniqueField(fieldPath = "season.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "spt_season_id", nullable = false)
  private Season season;

  @NotNull
  @UniqueField(fieldPath = "team.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "spt_team_id", nullable = false)
  private Team team;

  @Override
  public int hashCode() {
    return Objects.hash(
        season != null ? season.getId() : null,
        team != null ? team.getId() : null);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof SeasonParticipation))
      return false;
    SeasonParticipation other = (SeasonParticipation) obj;
    return Objects.equals(season, other.season)
        && Objects.equals(team, other.team);
  }

}
