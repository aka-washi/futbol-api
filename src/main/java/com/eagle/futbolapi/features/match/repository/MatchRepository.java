package com.eagle.futbolapi.features.match.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.match.entity.MatchStatus;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.venue.entity.Venue;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    Page<Match> findByMatchday(Matchday matchday, Pageable pageable);

    Page<Match> findByMatchdayId(Long matchdayId, Pageable pageable);

    Page<Match> findByHomeTeam(Team homeTeam, Pageable pageable);

    Page<Match> findByHomeTeamId(Long homeTeamId, Pageable pageable);

    Page<Match> findByAwayTeam(Team awayTeam, Pageable pageable);

    Page<Match> findByAwayTeamId(Long awayTeamId, Pageable pageable);

    Page<Match> findByVenue(Venue venue, Pageable pageable);

    Page<Match> findByVenueId(Long venueId, Pageable pageable);

    Page<Match> findByReferee(Person referee, Pageable pageable);

    Page<Match> findByRefereeId(Long refereeId, Pageable pageable);

    Page<Match> findByStatus(MatchStatus status, Pageable pageable);

    Page<Match> findByScheduledDate(LocalDate scheduledDate, Pageable pageable);

    @Query("SELECT m FROM Match m WHERE m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId")
    Page<Match> findByTeamId(@Param("teamId") Long teamId, Pageable pageable);

    @Query("SELECT m FROM Match m WHERE (m.homeTeam.id = :homeTeamId AND m.awayTeam.id = :awayTeamId) OR (m.homeTeam.id = :awayTeamId AND m.awayTeam.id = :homeTeamId)")
    Page<Match> findByTeams(@Param("homeTeamId") Long homeTeamId, @Param("awayTeamId") Long awayTeamId,
            Pageable pageable);

    @Query("SELECT m FROM Match m WHERE m.scheduledDate BETWEEN :startDate AND :endDate ORDER BY m.scheduledDate ASC, m.kickoffTime ASC")
    Page<Match> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
            Pageable pageable);

    @Query("SELECT m FROM Match m WHERE m.matchday.stage.id = :stageId ORDER BY m.scheduledDate ASC, m.kickoffTime ASC")
    Page<Match> findByStageId(@Param("stageId") Long stageId, Pageable pageable);

    Page<Match> findByHomeTeamIdAndAwayTeamId(Long homeTeamId, Long awayTeamId, Pageable pageable);

}
