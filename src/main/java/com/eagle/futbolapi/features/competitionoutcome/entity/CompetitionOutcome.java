package com.eagle.futbolapi.features.competitionoutcome.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.OutcomeType;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.team.entity.Team;

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
@Table(name = "competition_outcome")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "cpo_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cpo_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "cpo_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "cpo_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "cpo_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionOutcome extends BaseEntity {

  @NotNull
  @UniqueField(fieldPath = "competition.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cpo_competition_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Competition competition;

  @NotNull
  @UniqueField(fieldPath = "team.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cpo_team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Team team;

  @UniqueField
  @Column(name = "cpo_final_position")
  private Integer finalPosition;
  @UniqueField
  @Column(name = "cpo_points")
  private Integer points;
  @UniqueField
  @Enumerated(EnumType.STRING)
  @Column(name = "cpo_result_type", length = 50)
  private OutcomeType outcomeType;
  @Column(name = "cpo_notes", length = 500)
  private String notes;
}
