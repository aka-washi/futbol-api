package com.eagle.futbolapi.features.rosterentry.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;

@Repository
public interface RosterEntryRepository extends BaseRepository<RosterEntry, Long> {

  @Query("SELECT r FROM RosterEntry r WHERE r.season.id = :seasonId AND r.team.id = :teamId AND r.player IS NOT NULL AND r.active = true")
  Page<RosterEntry> findActivePlayersBySeasonAndTeam(@Param("seasonId") Long seasonId, @Param("teamId") Long teamId,
      Pageable pageable);

  @Query("SELECT r FROM RosterEntry r WHERE r.season.id = :seasonId AND r.team.id = :teamId AND r.staff IS NOT NULL AND r.active = true")
  Page<RosterEntry> findActiveStaffBySeasonAndTeam(@Param("seasonId") Long seasonId, @Param("teamId") Long teamId,
      Pageable pageable);

  @Query("SELECT r FROM RosterEntry r WHERE r.joinedDate <= :date AND (r.leftDate IS NULL OR r.leftDate >= :date)")
  Page<RosterEntry> findActiveOnDate(@Param("date") LocalDate date, Pageable pageable);

  // Unique field methods: season + team + player
  Optional<RosterEntry> findBySeasonIdAndTeamIdAndPlayerId(Long seasonId, Long teamId, Long playerId);

  boolean existsBySeasonIdAndTeamIdAndPlayerId(Long seasonId, Long teamId, Long playerId);

  boolean existsBySeasonIdAndTeamIdAndPlayerIdAndIdNot(Long seasonId, Long teamId, Long playerId, Long id);

  // Unique field methods: season + team + staff
  Optional<RosterEntry> findBySeasonIdAndTeamIdAndStaffId(Long seasonId, Long teamId, Long staffId);

  boolean existsBySeasonIdAndTeamIdAndStaffId(Long seasonId, Long teamId, Long staffId);

  boolean existsBySeasonIdAndTeamIdAndStaffIdAndIdNot(Long seasonId, Long teamId, Long staffId, Long id);

}
