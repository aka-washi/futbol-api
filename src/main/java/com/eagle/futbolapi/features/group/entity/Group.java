package com.eagle.futbolapi.features.group.entity;

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
import com.eagle.futbolapi.features.stage.entity.Stage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Group in a stage.
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "team_group")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "grp_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "grp_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "grp_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "grp_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "grp_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Group extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grp_stage_id", nullable = false)
  @NotNull
  private Stage stage;

  @NotBlank
  @Column(name = "grp_name", nullable = false, length = 50)
  private String name;

  @NotBlank
  @Column(name = "grp_display_name", nullable = false, length = 100)
  private String displayName;

  @NotNull
  @Column(name = "grp_order", nullable = false)
  private Integer order;

  @Column(name = "grp_number_of_teams")
  private Integer numberOfTeams;

  @Override
  public int hashCode() {
    return Objects.hash(stage, name, displayName, order);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Group))
      return false;
    Group other = (Group) obj;
    return Objects.equals(stage, other.stage)
        && Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(order, other.order);
  }
}
