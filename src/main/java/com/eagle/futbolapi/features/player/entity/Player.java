package com.eagle.futbolapi.features.player.entity;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.PlayerPosition;
import com.eagle.futbolapi.features.base.enums.PreferredFoot;
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
@Table(name = "player")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ply_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "ply_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "ply_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "ply_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "ply_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Player extends BaseEntity {

  @NotNull
  @UniqueField
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "person_id", nullable = false, unique = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Person person;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "ply_position", nullable = false, length = 20)
  private PlayerPosition position;

  @Enumerated(EnumType.STRING)
  @Column(name = "ply_preferred_foot", length = 10)
  private PreferredFoot preferredFoot;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Team currentTeam;

  @lombok.Builder.Default
  @Column(name = "ply_active", nullable = false)
  private Boolean active = true;

  @Override
  public int hashCode() {
    return Objects.hash(
        person,
        position,
        preferredFoot,
        currentTeam,
        active);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Player))
      return false;
    Player other = (Player) obj;
    return Objects.equals(person, other.person)
        && Objects.equals(position, other.position)
        && Objects.equals(preferredFoot, other.preferredFoot)
        && Objects.equals(currentTeam, other.currentTeam)
        && Objects.equals(active, other.active);
  }
}
