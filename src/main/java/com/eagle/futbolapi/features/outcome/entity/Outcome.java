package com.eagle.futbolapi.features.outcome.entity;

import java.math.BigDecimal;
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
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.OutcomeType;
import com.eagle.futbolapi.features.staff.entity.Staff;
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
@Table(name = "competition_outcome")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "oc_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "oc_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "oc_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "oc_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "oc_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Outcome extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "competition_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Competition competition;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Team team; // Either team or player or staff can be associated

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Player player;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Staff staff;

  @Enumerated(EnumType.STRING)
  @Column(name = "oc_type", nullable = false, length = 50)
  @NotNull
  private OutcomeType outcomeType;

  @Column(name = "oc_position")
  private Integer position;

  @Column(name = "oc_value", precision = 10, scale = 2)
  private BigDecimal value; // For stats like goals scored, points, etc.

  @Column(name = "oc_notes", columnDefinition = "TEXT")
  private String notes;

  @Override
  public int hashCode() {
    return Objects.hash(
        competition,
        team,
        player,
        staff,
        outcomeType,
        position,
        value,
        notes);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Outcome))
      return false;
    Outcome other = (Outcome) obj;
    return Objects.equals(competition, other.competition)
        && Objects.equals(team, other.team)
        && Objects.equals(player, other.player)
        && Objects.equals(staff, other.staff)
        && Objects.equals(outcomeType, other.outcomeType)
        && Objects.equals(position, other.position)
        && Objects.equals(value, other.value)
        && Objects.equals(notes, other.notes);
  }
}
