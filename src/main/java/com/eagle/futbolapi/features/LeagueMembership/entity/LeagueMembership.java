package com.eagle.futbolapi.features.LeagueMembership.entity;

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
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.MembershipStatus;
import com.eagle.futbolapi.features.organization.entity.Organization;

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
@Table(name = "league_memberships")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "lmp_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "lmp_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "lmp_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "lmp_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "lmp_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueMembership extends BaseEntity {

  @NotNull
  @UniqueField(fieldPath = "league.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lmp_league_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Organization league;
  @NotNull
  @UniqueField(fieldPath = "member.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lmp_member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Organization member;
  @NotNull
  @UniqueField
  @Column(name = "lmp_membership_status", nullable = false)
  private MembershipStatus membershipStatus;
  @Column(name = "lmp_join_date", nullable = false)
  private LocalDate joinDate;
  @Column(name = "lmp_left_date")
  private LocalDate leftDate;

  @Override
  public int hashCode() {
    return Objects.hash(
        league,
        member,
        membershipStatus,
        joinDate,
        leftDate);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof LeagueMembership))
      return false;
    LeagueMembership other = (LeagueMembership) obj;
    return Objects.equals(league, other.league)
        && Objects.equals(member, other.member)
        && Objects.equals(membershipStatus, other.membershipStatus)
        && Objects.equals(joinDate, other.joinDate)
        && Objects.equals(leftDate, other.leftDate);
  }
}
