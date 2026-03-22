package com.eagle.futbolapi.features.registration.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.player.entity.Player;
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
@Table(name = "registration")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "reg_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "reg_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "reg_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "reg_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "reg_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Registration extends BaseEntity {

  @NotBlank
  @Column(name = "reg_display_name", nullable = false, length = 100)
  private String displayName;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "competition_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Competition competition;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Player player;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Staff staff;

  @Column(name = "reg_jersey_number")
  private Integer jerseyNumber;

  @NotNull
  @Column(name = "reg_joined_date", nullable = false)
  private LocalDate joinedDate;

  @Column(name = "reg_left_date")
  private LocalDate leftDate;

  @Override
  public int hashCode() {
    return Objects.hash(
        competition != null ? competition.getId() : null,
        team != null ? team.getId() : null,
        player != null ? player.getId() : null,
        staff != null ? staff.getId() : null);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Registration other = (Registration) obj;
    return Objects.equals(competition, other.competition)
        && Objects.equals(team, other.team)
        && Objects.equals(player, other.player)
        && Objects.equals(staff, other.staff);
  }

}
