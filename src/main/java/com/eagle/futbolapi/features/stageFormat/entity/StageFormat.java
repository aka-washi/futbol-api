package com.eagle.futbolapi.features.stageFormat.entity;

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
import com.eagle.futbolapi.features.base.enums.StageFormatType;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Stage Format in the football database.
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "stage_format")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "sgf_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "sgf_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "sgf_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "sgf_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "sgf_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StageFormat extends BaseEntity {

  @NotBlank
  @UniqueField
  @Column(name = "sgf_display_name", nullable = false, length = 100)
  private String displayName;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "sgf_type", nullable = false, length = 20)
  private StageFormatType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "point_system_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private PointSystem pointSystem;

  @Column(name = "sgf_number_of_teams")
  private Integer numberOfTeams;

  @Column(name = "sgf_number_of_groups")
  private Integer numberOfGroups;

  @Column(name = "sgf_teams_per_group")
  private Integer teamsPerGroup;

  @Column(name = "sgf_has_home_and_away")
  private Boolean hasHomeAndAway;

  @Column(name = "sgf_teams_qualifying_for_next_stage")
  private Integer teamsQualifyingForNextStage;

  @Column(name = "sgf_description", columnDefinition = "TEXT")
  private String description;

  @Override
  public int hashCode() {
    return Objects.hash(displayName, type);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    StageFormat other = (StageFormat) obj;
    return Objects.equals(displayName, other.displayName)
        && Objects.equals(type, other.type);
  }
}
