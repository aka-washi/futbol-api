package com.eagle.futbolapi.features.match.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.shared.BaseEntity;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.venue.entity.Venue;

import lombok.*;

/**
 * Match Entity
 * Represents a single football match
 */
@Entity
@Table(name = "[match]")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "mt_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "mt_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "mt_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "mt_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "mt_updated_by", length = 100))
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matchday_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Matchday matchday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Team awayTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Venue venue;

    @NotNull
    @Column(name = "mt_scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    @Column(name = "mt_kickoff_time")
    private LocalDateTime kickoffTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "mt_status", nullable = false, length = 50)
    @Builder.Default
    private MatchStatus status = MatchStatus.SCHEDULED;

    @Column(name = "mt_home_score")
    private Integer homeScore;

    @Column(name = "mt_away_score")
    private Integer awayScore;

    @Column(name = "mt_home_halftime_score")
    private Integer homeHalfTimeScore;

    @Column(name = "mt_away_halftime_score")
    private Integer awayHalfTimeScore;

    @Builder.Default
    @Column(name = "mt_extra_time_allowed", nullable = false)
    private Boolean extraTimeAllowed = false;

    @Builder.Default
    @Column(name = "mt_penalty_shootout_allowed", nullable = false)
    private Boolean penaltyShootoutAllowed = false;

    @Column(name = "mt_home_extratime_score")
    private Integer homeExtraTimeScore;

    @Column(name = "mt_away_extratime_score")
    private Integer awayExtraTimeScore;

    @Column(name = "mt_home_penalty_score")
    private Integer homePenaltyScore;

    @Column(name = "mt_away_penalty_score")
    private Integer awayPenaltyScore;

    @Column(name = "mt_attendance")
    private Integer attendance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referee_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Person referee;

    @Column(name = "mt_weather_conditions")
    private String weatherConditions;

    @Column(name = "mt_notes", columnDefinition = "TEXT")
    private String notes;

}
