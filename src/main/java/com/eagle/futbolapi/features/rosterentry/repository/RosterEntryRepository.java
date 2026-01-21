package com.eagle.futbolapi.features.rosterentry.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;

@Repository
public interface RosterEntryRepository extends JpaRepository<RosterEntry, Long> {

  @Query("SELECT r FROM RosterEntry r WHERE r.season.id = :seasonId AND r.team.id = :teamId AND r.player IS NOT NULL AND r.active = true")
  Page<RosterEntry> findActivePlayersBySeasonAndTeam(@Param("seasonId") Long seasonId, @Param("teamId") Long teamId);

  @Query("SELECT r FROM RosterEntry r WHERE r.season.id = :seasonId AND r.team.id = :teamId AND r.staff IS NOT NULL AND r.active = true")
  Page<RosterEntry> findActiveStaffBySeasonAndTeam(@Param("seasonId") Long seasonId, @Param("teamId") Long teamId);

  @Query("SELECT r FROM RosterEntry r WHERE r.joinedDate <= :date AND (r.leftDate IS NULL OR r.leftDate >= :date)")
  Page<RosterEntry> findActiveOnDate(@Param("date") LocalDate date);

}
