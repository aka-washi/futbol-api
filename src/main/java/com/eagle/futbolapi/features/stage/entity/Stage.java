package com.eagle.futbolapi.features.stage.entity;

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

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.StageStatus;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.structure.entity.Structure;

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
@Table(name = "stage")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "sg_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "sg_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "sg_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "sg_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "sg_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Stage extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "competition_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Competition competition;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "structure_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Structure structure;

  @NotBlank
  @Column(name = "sg_name", nullable = false)
  private String name; // e.g., "Regular Season", "Playoffs", "Play-in"

  @NotBlank
  @Column(name = "sg_display_name", length = 100, nullable = false)
  private String displayName;

  @Column(name = "sg_order_num", nullable = false)
  private Integer order; // Order of stages in the competition (1, 2, 3, etc.)

  @NotNull
  @Column(name = "sg_start_date", nullable = false)
  private LocalDate startDate;

  @NotNull
  @Column(name = "sg_end_date", nullable = false)
  private LocalDate endDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "sg_status", nullable = false, length = 50)
  @Builder.Default
  private StageStatus status = StageStatus.NOT_STARTED;

  @Override
  public int hashCode() {
    return Objects.hash(
        competition,
        structure,
        name,
        displayName,
        order,
        startDate,
        endDate,
        status);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Stage))
      return false;
    Stage other = (Stage) obj;
    return Objects.equals(competition, other.competition)
        && Objects.equals(structure, other.structure)
        && Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(order, other.order)
        && Objects.equals(startDate, other.startDate)
        && Objects.equals(endDate, other.endDate)
        && Objects.equals(status, other.status);
  }

}
