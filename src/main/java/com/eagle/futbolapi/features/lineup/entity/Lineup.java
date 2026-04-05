package com.eagle.futbolapi.features.lineup.entity;

import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Lineup for a team in a match.
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "lineup")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "lup_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "lup_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "lup_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "lup_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "lup_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Lineup extends BaseEntity {

  @NotBlank
  @Column(name = "lup_display_name", nullable = false, length = 100)
  private String displayName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lup_match_id", nullable = false)
  @NotNull
  private Match match;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lup_team_id", nullable = false)
  @NotNull
  private Team team;

  @Column(name = "lup_formation", length = 50)
  private String formation;

  @Override
  public int hashCode() {
    return Objects.hash(displayName, match, team, formation);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Lineup))
      return false;
    Lineup other = (Lineup) obj;
    return Objects.equals(displayName, other.displayName)
        && Objects.equals(match, other.match)
        && Objects.equals(team, other.team)
        && Objects.equals(formation, other.formation);
  }
}
