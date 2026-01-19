package com.eagle.futbolapi.features.structure.entity;

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
import com.eagle.futbolapi.features.base.entity.StructureType;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;

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
@Table(name = "structure")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "st_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "st_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "st_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "st_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "st_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Structure extends BaseEntity {

  @NotBlank
  @Column(name = "st_name", nullable = false)
  private String name;

  @NotBlank
  @Column(name = "st_display_name", length = 100, nullable = false)
  private String displayName;

  @Enumerated(EnumType.STRING)
  @Column(name = "st_type", nullable = false, length = 50)
  @NotNull
  private StructureType type;

  @Column(name = "st_description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "st_rules", columnDefinition = "TEXT")
  private String rules; // JSON string with specific rules

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "point_system_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private PointSystem pointSystem;

  @Column(name = "st_number_of_teams")
  private Integer numberOfTeams;

  @Column(name = "st_number_of_groups")
  private Integer numberOfGroups;

  @Column(name = "st_teams_per_group")
  private Integer teamsPerGroup;

  @Column(name = "st_has_home_and_away", nullable = false)
  @Builder.Default
  private Boolean hasHomeAndAway = true;

  @Column(name = "st_teams_qualifying_for_next_stage")
  private Integer teamsQualifyingForNextStage;

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        displayName,
        type,
        rules,
        pointSystem,
        numberOfTeams,
        numberOfGroups,
        teamsPerGroup,
        hasHomeAndAway,
        teamsQualifyingForNextStage
    );
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Structure))
      return false;
    Structure other = (Structure) obj;
    return Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(type, other.type)
        && Objects.equals(rules, other.rules)
        && Objects.equals(pointSystem, other.pointSystem)
        && Objects.equals(numberOfTeams, other.numberOfTeams)
        && Objects.equals(numberOfGroups, other.numberOfGroups)
        && Objects.equals(teamsPerGroup, other.teamsPerGroup)
        && Objects.equals(hasHomeAndAway, other.hasHomeAndAway)
        && Objects.equals(teamsQualifyingForNextStage, other.teamsQualifyingForNextStage);
  }

}
