package com.eagle.futbolapi.features.match.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.matchday.entity.Matchday;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

  Optional<Match> findByName(String name);

  Optional<Match> findByDisplayName(String displayName);

  @Query("SELECT m FROM Match m WHERE m.matchday = :matchday AND m.homeTeam.displayName = :homeTeam AND m.awayTeam.displayName = :awayTeam AND m.scheduledDate = :scheduledDate")
  Optional<Match> findByUniqueValues(@Param("matchday") Matchday matchday, @Param("homeTeam") String homeTeam,
      @Param("awayTeam") String awayTeam, @Param("scheduledDate") LocalDate scheduledDate);

  @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Match m WHERE m.matchday = :matchday AND m.homeTeam.displayName = :homeTeam AND m.awayTeam.displayName = :awayTeam AND m.scheduledDate = :scheduledDate")
  boolean existsByUniqueValues(@Param("matchday") Matchday matchday, @Param("homeTeam") String homeTeam,
      @Param("awayTeam") String awayTeam, @Param("scheduledDate") LocalDate scheduledDate);

  @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Match m WHERE m.matchday = :matchday AND m.homeTeam.displayName = :homeTeam AND m.awayTeam.displayName = :awayTeam AND m.scheduledDate = :scheduledDate AND m.id <> :id")
  boolean existsByUniqueValuesAndIdNot(@Param("matchday") Matchday matchday, @Param("homeTeam") String homeTeam,
      @Param("awayTeam") String awayTeam, @Param("scheduledDate") LocalDate scheduledDate, @Param("id") Long id);

  boolean existsByMatchdayIdAndHomeTeamIdAndAwayTeamIdAndScheduledDate(Long matchdayId, Long homeTeamId,
      Long awayTeamId, LocalDate scheduledDate);

  boolean existsByMatchdayIdAndHomeTeamIdAndAwayTeamIdAndScheduledDateAndIdNot(Long matchdayId, Long homeTeamId,
      Long awayTeamId, LocalDate scheduledDate, Long id);
}
