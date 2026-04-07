package com.eagle.futbolapi.features.tournamentSeason.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.CompetitionStatus;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.tournament.entity.Tournament;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Tournament Season in the football database.
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "tournament_season", uniqueConstraints = {
    @UniqueConstraint(name = "uk_tournament_season_natural", columnNames = {
        "tsn_tournament_id",
        "tsn_season_id"
    })
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "tsn_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "tsn_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "tsn_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "tsn_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "tsn_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentSeason extends BaseEntity {

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tsn_season_id", nullable = false)
  private Season season;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tsn_tournament_id", nullable = false)
  private Tournament tournament;

  @NotBlank
  @Column(name = "tsn_display_name", nullable = false, length = 100)
  private String displayName;

  @NotNull
  @Column(name = "tsn_start_date", nullable = false)
  private LocalDate startDate;

  @NotNull
  @Column(name = "tsn_end_date", nullable = false)
  private LocalDate endDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "tsn_status", length = 20)
  private CompetitionStatus status;

  @Column(name = "tsn_has_relegation")
  private Boolean hasRelegation;

  @Column(name = "tsn_has_promotion")
  private Boolean hasPromotion;

  @Column(name = "tsn_number_of_teams")
  private Integer numberOfTeams;

  @Column(name = "tsn_description", columnDefinition = "TEXT")
  private String description;

  @Override
  public int hashCode() {
    return Objects.hash(
        season != null ? season.getId() : null,
        tournament != null ? tournament.getId() : null);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof TournamentSeason))
      return false;
    TournamentSeason other = (TournamentSeason) obj;
    return Objects.equals(season, other.season)
        && Objects.equals(tournament, other.tournament);
  }
}
