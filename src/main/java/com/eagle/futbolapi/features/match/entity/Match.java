package com.eagle.futbolapi.features.match.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.MatchStatus;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.venue.entity.Venue;

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
@Table(name = "match")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "mtc_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "mtc_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "mtc_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "mtc_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "mtc_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Match extends BaseEntity {

  @NotBlank
  @Column(name = "mtc_name", nullable = false)
  private String name;

  @NotBlank
  @Column(name = "mtc_display_name", length = 100, nullable = false)
  private String displayName;

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
  @Column(name = "mtc_scheduled_date", nullable = false)
  private LocalDate scheduledDate;

  @Column(name = "mtc_kickoff_time")
  private LocalDateTime kickoffTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "mtc_status", nullable = false, length = 50)
  @Builder.Default
  private MatchStatus status = MatchStatus.SCHEDULED;

  @Column(name = "mtc_home_score")
  private Integer homeScore;

  @Column(name = "mtc_away_score")
  private Integer awayScore;

  @Column(name = "mtc_home_halftime_score")
  private Integer homeHalfTimeScore;

  @Column(name = "mtc_away_halftime_score")
  private Integer awayHalfTimeScore;

  @Builder.Default
  @Column(name = "mtc_extra_time_allowed", nullable = false)
  private Boolean extraTimeAllowed = false;

  @Builder.Default
  @Column(name = "mtc_penalty_shootout_allowed", nullable = false)
  private Boolean penaltyShootoutAllowed = false;

  @Column(name = "mtc_home_extratime_score")
  private Integer homeExtraTimeScore;

  @Column(name = "mtc_away_extratime_score")
  private Integer awayExtraTimeScore;

  @Column(name = "mtc_home_penalty_score")
  private Integer homePenaltyScore;

  @Column(name = "mtc_away_penalty_score")
  private Integer awayPenaltyScore;

  @Column(name = "mtc_attendance")
  private Integer attendance;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "referee_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Person referee;

  @Column(name = "mtc_weather_conditions")
  private String weatherConditions;

  @Column(name = "mtc_notes", columnDefinition = "TEXT")
  private String notes;

  @Override
  public int hashCode() {
    return Objects.hash(
        matchday,
        homeTeam,
        awayTeam,
        venue,
        scheduledDate,
        kickoffTime,
        status,
        homeScore,
        awayScore,
        homeHalfTimeScore,
        awayHalfTimeScore,
        extraTimeAllowed,
        penaltyShootoutAllowed,
        homeExtraTimeScore,
        awayExtraTimeScore,
        homePenaltyScore,
        awayPenaltyScore,
        attendance,
        referee);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Match))
      return false;
    Match other = (Match) obj;
    return Objects.equals(matchday, other.matchday)
        && Objects.equals(homeTeam, other.homeTeam)
        && Objects.equals(awayTeam, other.awayTeam)
        && Objects.equals(venue, other.venue)
        && Objects.equals(scheduledDate, other.scheduledDate)
        && Objects.equals(kickoffTime, other.kickoffTime)
        && Objects.equals(status, other.status)
        && Objects.equals(homeScore, other.homeScore)
        && Objects.equals(awayScore, other.awayScore)
        && Objects.equals(homeHalfTimeScore, other.homeHalfTimeScore)
        && Objects.equals(awayHalfTimeScore, other.awayHalfTimeScore)
        && Objects.equals(extraTimeAllowed, other.extraTimeAllowed)
        && Objects.equals(penaltyShootoutAllowed, other.penaltyShootoutAllowed)
        && Objects.equals(homeExtraTimeScore, other.homeExtraTimeScore)
        && Objects.equals(awayExtraTimeScore, other.awayExtraTimeScore)
        && Objects.equals(homePenaltyScore, other.homePenaltyScore)
        && Objects.equals(awayPenaltyScore, other.awayPenaltyScore)
        && Objects.equals(attendance, other.attendance)
        && Objects.equals(referee, other.referee);
  }

}
