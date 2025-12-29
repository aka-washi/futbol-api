package com.eagle.futbolapi.features.player.entity;

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

import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.shared.BaseEntity;
import com.eagle.futbolapi.features.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Player Entity
 * Represents a football player (extends Person)
 */
@Entity
@Table(name = "player")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "pl_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "pl_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "pl_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "pl_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "pl_updated_by", length = 100))
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, unique = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(name = "pl_position", nullable = false, length = 50)
    @NotNull
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(name = "pl_preferred_foot", length = 10)
    private PreferredFoot preferredFoot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Team currentTeam;

    @Column(name = "pl_is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

}
