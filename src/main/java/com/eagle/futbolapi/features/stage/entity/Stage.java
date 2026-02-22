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
import com.eagle.futbolapi.features.stageFormat.entity.StageFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Stage in the football database.
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "stage")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "stg_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "stg_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "stg_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "stg_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "stg_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Stage extends BaseEntity {

  @NotBlank
  @Column(name = "stg_display_name", nullable = false, length = 100)
  private String displayName;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "competition_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Competition competition;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stage_format_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private StageFormat stageFormat;

  @NotNull
  @Column(name = "stg_order", nullable = false)
  private Integer order;

  @Enumerated(EnumType.STRING)
  @Column(name = "stg_status", length = 20)
  private StageStatus status;

  @Column(name = "stg_start_date")
  private LocalDate startDate;

  @Column(name = "stg_end_date")
  private LocalDate endDate;

  @Override
  public int hashCode() {
    return Objects.hash(displayName,
        competition != null ? competition.getId() : null,
        order);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Stage other = (Stage) obj;
    return Objects.equals(displayName, other.displayName)
        && Objects.equals(competition, other.competition)
        && Objects.equals(order, other.order);
  }
}
