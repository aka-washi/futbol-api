package com.eagle.futbolapi.features.qualificationoutcome.entity;

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
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.QualificationType;
import com.eagle.futbolapi.features.competition.entity.Competition;
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
@Table(name = "qualification_outcome")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "qfo_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "qfo_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "qfo_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "qfo_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "qfo_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QualificationOutcome extends BaseEntity {

  @NotNull
  @UniqueField(fieldPath = "sourceCompetition.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "qfo_source_competition_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Competition sourceCompetition;

  @NotNull
  @UniqueField(fieldPath = "targetCompetition.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "qfo_target_competition_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Competition targetCompetition;

  @NotNull
  @UniqueField(fieldPath = "team.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "qfo_team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Team team;

  @NotNull
  @UniqueField
  @Enumerated(EnumType.STRING)
  @Column(name = "qfo_qualification_type", length = 50)
  private QualificationType qualificationType;

}
