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
 * Stores configuration for how points are awarded to teams based on
 * match outcomes in football competitions.
 *
 * <p>
 * Different competitions may use different point allocation rules
 * (e.g., 3 points for a win vs 2 points for a win). This entity captures
 * all possible point allocation scenarios including regular outcomes,
 * penalty shootouts, and forfeits.
 *
 * <p>
 * This entity enforces uniqueness constraints on the combination of all
 * point values to prevent duplicate point system configurations.
 * The {@link UniqueField} annotation is used to enable automatic duplicate
 * detection.
 *
 * <p>
 * Column names follow the naming convention with 'pts_' prefix.
 * All base entity fields (id, timestamps, audit fields) are mapped with
 * attribute overrides.
 *
 * @see BaseEntity
 * @see com.eagle.futbolapi.features.pointsystem.dto.PointSystemDto
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

  /**
   * The unique identifier name of the point system.
   * This field is required and must be unique across all point systems.
   * Examples: "standard", "three_points", "two_point_system"
   */
  @NotBlank
  @UniqueField
  @Column(name = "pts_name", nullable = false, length = 100)
  private String name;

  /**
   * The display name used for UI presentation.
   * This field is required and must be unique across all point systems.
   */
  @NotBlank
  @UniqueField
  @Column(name = "pts_display_name", length = 100, nullable = false)
  private String displayName;

  /**
   * Optional description of the point system usage and context.
   * Can explain when this point system should be applied.
   */
  @Column(name = "pts_description", length = 255)
  private String description;

  /**
   * Points awarded for a standard regulation win.
   * This field is required and contributes to the system's uniqueness.
   */
  @UniqueField
  @Column(name = "pts_points_for_win", nullable = false)
  private Integer pointsForWin;

  /**
   * Points awarded for a draw match.
   * This field is required and contributes to the system's uniqueness.
   */
  @UniqueField
  @Column(name = "pts_points_for_draw", nullable = false)
  private Integer pointsForDraw;

  /**
   * Points awarded for a regulation loss.
   * This field is required and contributes to the system's uniqueness.
   */
  @UniqueField
  @Column(name = "pts_points_for_loss", nullable = false)
  private Integer pointsForLoss;

  /**
   * Points awarded for winning after penalty shootout.
   * This field is optional but contributes to the system's uniqueness.
   */
  @UniqueField
  @Column(name = "pts_points_for_win_on_penalties")
  private Integer pointsForWinOnPenalties;

  /**
   * Points awarded for losing after penalty shootout.
   * This field is optional but contributes to the system's uniqueness.
   */
  @UniqueField
  @Column(name = "pts_points_for_loss_on_penalties")
  private Integer pointsForLossOnPenalties;

  /**
   * Points awarded when opponent forfeits and team wins by default.
   * This field is optional but contributes to the system's uniqueness.
   */
  @UniqueField
  @Column(name = "pts_points_for_forfeit_win")
  private Integer pointsForForfeitWin;

  /**
   * Points awarded when team forfeits the match.
   * This field is optional but contributes to the system's uniqueness.
   */
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
    if (obj == null || getClass() != obj.getClass())
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
