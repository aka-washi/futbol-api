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

import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.CompetitionType;

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
@Table(name = "competition")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "cp_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cp_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "cp_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "cp_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "cp_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Competition extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "season_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Season season;

  @NotBlank
  @Column(name = "cp_name", nullable = false)
  private String name; // e.g., "Apertura 2025", "La Liga 2025-2026"

  @NotBlank
  @Column(name = "cp_display_name", length = 100, nullable = false)
  private String displayName;

  @Enumerated(EnumType.STRING)
  @Column(name = "cp_type", nullable = false, length = 50)
  @NotNull
  private CompetitionType type;

  @NotNull
  @Column(name = "cp_start_date", nullable = false)
  private LocalDate startDate;

  @NotNull
  @Column(name = "cp_end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "co_active", nullable = false)
  @Builder.Default
  private Boolean active = false;

  @Column(name = "cp_total_matchdays", nullable = false)
  private Integer totalMatchdays;

  @Column(name = "cp_description", columnDefinition = "TEXT")
  private String description;

  @Override
  public int hashCode() {
    return Objects.hash(
        season,
        name,
        displayName,
        type,
        startDate,
        endDate,
        active,
        totalMatchdays,
        description);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Competition))
      return false;
    Competition other = (Competition) obj;
    return Objects.equals(season, other.season)
        && Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(type, other.type)
        && Objects.equals(startDate, other.startDate)
        && Objects.equals(endDate, other.endDate)
        && Objects.equals(active, other.active)
        && Objects.equals(totalMatchdays, other.totalMatchdays)
        && Objects.equals(description, other.description);
  }

}
