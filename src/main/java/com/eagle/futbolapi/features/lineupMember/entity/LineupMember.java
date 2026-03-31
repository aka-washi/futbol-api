package com.eagle.futbolapi.features.lineupMember.entity;

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

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.LineupMemberRoleType;
import com.eagle.futbolapi.features.base.enums.PersonType;
import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.staff.entity.Staff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Lineup Member (player or staff in a lineup).
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "lineup_member")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "lm_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "lm_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "lm_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "lm_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "lm_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LineupMember extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lineup_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Lineup lineup;

  @Enumerated(EnumType.STRING)
  @Column(name = "lm_person_type", nullable = false, length = 20)
  @NotNull
  private PersonType personType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Player player;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Staff staff;

  @Enumerated(EnumType.STRING)
  @Column(name = "lm_role_type", nullable = false, length = 30)
  @NotNull
  private LineupMemberRoleType roleType;

  @Column(name = "lm_position", length = 50)
  private String position;

  @Column(name = "lm_jersey_number")
  private Integer jerseyNumber;

  @Column(name = "lm_captain", nullable = false)
  @Builder.Default
  private Boolean captain = false;

  @Column(name = "lm_order_num")
  private Integer orderNum;

  @Override
  public int hashCode() {
    return Objects.hash(lineup, personType, player, staff, roleType);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof LineupMember))
      return false;
    LineupMember other = (LineupMember) obj;
    return Objects.equals(lineup, other.lineup)
        && Objects.equals(personType, other.personType)
        && Objects.equals(player, other.player)
        && Objects.equals(staff, other.staff)
        && Objects.equals(roleType, other.roleType);
  }
}
