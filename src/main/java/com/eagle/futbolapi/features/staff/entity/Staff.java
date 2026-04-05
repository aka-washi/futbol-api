package com.eagle.futbolapi.features.staff.entity;

import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.StaffRole;
import com.eagle.futbolapi.features.person.entity.Person;
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
@Table(name = "staff")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "stf_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "stf_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "stf_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "stf_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "stf_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Staff extends BaseEntity {

  @NotNull
  @UniqueField
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stf_person_id", nullable = false, unique = true)
  private Person person;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "stf_role", nullable = false, length = 30)
  private StaffRole role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stf_team_id")
  private Team currentTeam;

  @lombok.Builder.Default
  @Column(name = "stf_active", nullable = false)
  private Boolean active = true;

  @Override
  public int hashCode() {
    return Objects.hash(
        person,
        role,
        currentTeam,
        active);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Staff))
      return false;
    Staff other = (Staff) obj;
    return Objects.equals(person, other.person)
        && Objects.equals(role, other.role)
        && Objects.equals(currentTeam, other.currentTeam)
        && Objects.equals(active, other.active);
  }
}
