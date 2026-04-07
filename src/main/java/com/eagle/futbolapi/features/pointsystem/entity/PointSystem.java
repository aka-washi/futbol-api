package com.eagle.futbolapi.features.pointsystem.entity;

import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Point System in the football database.
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "point_system")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "pts_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "pts_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "pts_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "pts_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "pts_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointSystem extends BaseEntity {
  @NotBlank
  @UniqueField
  @Column(name = "pts_name", nullable = false, length = 100)
  private String name;
  @NotBlank
  @UniqueField
  @Column(name = "pts_display_name", length = 100, nullable = false)
  private String displayName;
  @Column(name = "pts_description", length = 255)
  private String description;
  @UniqueField
  @Column(name = "pts_points_for_win", nullable = false)
  private Integer pointsForWin;
  @UniqueField
  @Column(name = "pts_points_for_draw", nullable = false)
  private Integer pointsForDraw;
  @UniqueField
  @Column(name = "pts_points_for_loss", nullable = false)
  private Integer pointsForLoss;
  @UniqueField
  @Column(name = "pts_points_for_win_on_penalties")
  private Integer pointsForWinOnPenalties;
  @UniqueField
  @Column(name = "pts_points_for_loss_on_penalties")
  private Integer pointsForLossOnPenalties;
  @UniqueField
  @Column(name = "pts_points_for_forfeit_win")
  private Integer pointsForForfeitWin;
  @UniqueField
  @Column(name = "pts_points_for_forfeit_loss")
  private Integer pointsForForfeitLoss;

  @Override
  public int hashCode() {
    return Objects.hash(name, displayName, description, pointsForWin, pointsForDraw, pointsForLoss,
        pointsForWinOnPenalties, pointsForLossOnPenalties, pointsForForfeitWin,
        pointsForForfeitLoss);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof PointSystem))
      return false;
    PointSystem other = (PointSystem) obj;
    return Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(description, other.description)
        && Objects.equals(pointsForWin, other.pointsForWin)
        && Objects.equals(pointsForDraw, other.pointsForDraw)
        && Objects.equals(pointsForLoss, other.pointsForLoss)
        && Objects.equals(pointsForWinOnPenalties, other.pointsForWinOnPenalties)
        && Objects.equals(pointsForLossOnPenalties, other.pointsForLossOnPenalties)
        && Objects.equals(pointsForForfeitWin, other.pointsForForfeitWin)
        && Objects.equals(pointsForForfeitLoss, other.pointsForForfeitLoss);
  }

}
