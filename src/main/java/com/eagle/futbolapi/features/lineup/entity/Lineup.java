package com.eagle.futbolapi.features.lineup.entity;

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

import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.shared.BaseEntity;
import com.eagle.futbolapi.features.team.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Lineup Entity
 * Represents the starting lineup and substitutes for a match
 */
@Entity
@Table(name = "lineup")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "lu_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "lu_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "lu_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "lu_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "lu_updated_by", length = 100))
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lineup extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(name = "lu_type", nullable = false, length = 20)
    @NotNull
    private LineupType type;

    @Column(name = "lu_position", length = 50)
    private String position; // Tactical position in the formation

    @Column(name = "lu_jersey_number", nullable = false)
    private Integer jerseyNumber;

    @Column(name = "lu_captain", nullable = false)
    @Builder.Default
    private Boolean captain = false;

    @Column(name = "lu_order_num")
    private Integer orderNum; // Position in the lineup
}
