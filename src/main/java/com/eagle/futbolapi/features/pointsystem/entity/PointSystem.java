package com.eagle.futbolapi.features.pointsystem.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

import com.eagle.futbolapi.features.shared.BaseEntity;

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
@Table(name = "point_system")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "pt_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "pt_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "pt_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "pt_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "pt_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointSystem extends BaseEntity {

    @NotBlank
    @Column(name = "pt_name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "pt_display_name", length = 100, nullable = false)
    private String displayName;

    @Column(name = "pt_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "pt_points_for_win", nullable = false)
    @Builder.Default
    private Integer pointsForWin = 3;

    @Column(name = "pt_points_for_draw", nullable = false)
    @Builder.Default
    private Integer pointsForDraw = 1;

    @Column(name = "pt_points_for_loss", nullable = false)
    @Builder.Default
    private Integer pointsForLoss = 0;

    @Column(name = "pt_points_for_win_on_penalties")
    private Integer pointsForWinOnPenalties; // Some competitions give 2 points for penalty shootout wins

    @Column(name = "pt_points_for_loss_on_penalties")
    private Integer pointsForLossOnPenalties; // Some competitions give 1 point for penalty shootout losses

    @Column(name = "pt_is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Override
        public int hashCode() {
            return Objects.hash(
                pointsForWin,
                pointsForDraw,
                pointsForLoss,
                pointsForWinOnPenalties,
                pointsForLossOnPenalties
            );
        }

    @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof PointSystem))
                return false;
            PointSystem other = (PointSystem) obj;
            return Objects.equals(pointsForWin, other.pointsForWin)
                && Objects.equals(pointsForDraw, other.pointsForDraw)
                && Objects.equals(pointsForLoss, other.pointsForLoss)
                && Objects.equals(pointsForWinOnPenalties, other.pointsForWinOnPenalties)
                && Objects.equals(pointsForLossOnPenalties, other.pointsForLossOnPenalties);
        }

}
