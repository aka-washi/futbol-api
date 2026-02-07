package com.eagle.futbolapi.features.rosterentry.entity;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.team.entity.Team;

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
@Table(name = "roster_entry")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "re_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "re_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "re_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "re_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "re_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RosterEntry extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "season_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Season season;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Player player;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Staff staff;

  @Column(name = "re_jersey_number")
  private Integer jerseyNumber;

  @NotNull
  @Column(name = "re_joined_date", nullable = false)
  private LocalDate joinedDate;

  @Column(name = "re_left_date")
  private LocalDate leftDate;

  @Column(name = "re_active", nullable = false)
  @Builder.Default
  private Boolean active = true;

  @PrePersist
  @PreUpdate
  private void validatePlayerOrStaff() {
    if ((player == null && staff == null) || (player != null && staff != null)) {
      throw new IllegalStateException("RosterEntry must have either a player or staff, but not both");
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        season,
        team,
        player,
        staff,
        jerseyNumber,
        joinedDate,
        leftDate,
        active);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof RosterEntry))
      return false;
    RosterEntry other = (RosterEntry) obj;
    return Objects.equals(season, other.season)
        && Objects.equals(team, other.team)
        && Objects.equals(player, other.player)
        && Objects.equals(staff, other.staff)
        && Objects.equals(jerseyNumber, other.jerseyNumber)
        && Objects.equals(joinedDate, other.joinedDate)
        && Objects.equals(leftDate, other.leftDate)
        && Objects.equals(active, other.active);
  }
}
