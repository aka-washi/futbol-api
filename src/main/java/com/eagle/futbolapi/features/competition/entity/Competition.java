package com.eagle.futbolapi.features.competition.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.CompetitionStatus;
import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.tournamentSeason.entity.TournamentSeason;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Competition in the football database.
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "competition")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "cmp_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cmp_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "cmp_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "cmp_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "cmp_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Competition extends BaseEntity {

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tournament_season_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @UniqueField(fieldPath = "tournamentSeason.id")
  private TournamentSeason tournamentSeason;

  @NotBlank
  @Column(name = "cmp_display_name", nullable = false, length = 100)
  @UniqueField
  private String displayName;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "cmp_type", nullable = false, length = 20)
  @UniqueField
  private CompetitionType type;

  @NotNull
  @Column(name = "cmp_start_date", nullable = false)
  private LocalDate startDate;

  @NotNull
  @Column(name = "cmp_end_date", nullable = false)
  private LocalDate endDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "cmp_status", length = 20)
  private CompetitionStatus status;

  @NotNull
  @Column(name = "cmp_total_matches", nullable = false)
  private Integer totalMatches;

  @Column(name = "cmp_description", columnDefinition = "TEXT")
  private String description;

  @Override
  public int hashCode() {
    return Objects.hash(displayName, tournamentSeason, type);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Competition other = (Competition) obj;
    return Objects.equals(displayName, other.displayName)
        && Objects.equals(tournamentSeason, other.tournamentSeason)
        && Objects.equals(type, other.type);
  }
}
