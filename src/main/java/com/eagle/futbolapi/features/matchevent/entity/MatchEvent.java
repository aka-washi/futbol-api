package com.eagle.futbolapi.features.matchevent.entity;

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
 * MatchEvent Entity
 * Represents events that occur during a match (goals, cards, substitutions, etc.)
 */
@Entity
@Table(name = "match_event")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "me_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "me_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "me_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "me_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "me_updated_by", length = 100))
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEvent extends BaseEntity {

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
    @Column(name = "me_type", nullable = false, length = 50)
    @NotNull
    private EventType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "me_period", nullable = false, length = 50)
    @NotNull
    private Period period;

    @Column(name = "me_minute", nullable = false)
    private Integer minute;

    @Column(name = "me_extra_minute")
    private Integer extraMinute; // Injury time

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assist_player_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Player assistPlayer; // For goals

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "substitute_player_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Player substitutePlayer; // For substitutions (player coming in)

    @Column(name = "me_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "me_video_url")
    private String videoUrl;
}
