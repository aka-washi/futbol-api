package com.eagle.futbolapi.features.rosterentry.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface RosterEntryRepository extends JpaRepository<RosterEntry, Long> {

    List<RosterEntry> findBySeason(Season season);

    List<RosterEntry> findBySeasonId(Long seasonId);

    List<RosterEntry> findByTeam(Team team);

    List<RosterEntry> findByTeamId(Long teamId);

    List<RosterEntry> findByPlayer(Player player);

    List<RosterEntry> findByPlayerId(Long playerId);

    List<RosterEntry> findByStaff(Staff staff);

    List<RosterEntry> findByStaffId(Long staffId);

    List<RosterEntry> findByActive(Boolean active);

    List<RosterEntry> findBySeasonIdAndTeamId(Long seasonId, Long teamId);

    List<RosterEntry> findBySeasonIdAndTeamIdAndActive(Long seasonId, Long teamId, Boolean active);

    Optional<RosterEntry> findBySeasonIdAndPlayerId(Long seasonId, Long playerId);

    Optional<RosterEntry> findBySeasonIdAndStaffId(Long seasonId, Long staffId);

    @Query("SELECT r FROM RosterEntry r WHERE r.season.id = :seasonId AND r.team.id = :teamId AND r.player IS NOT NULL AND r.active = true")
    List<RosterEntry> findActivePlayersBySeasonAndTeam(@Param("seasonId") Long seasonId, @Param("teamId") Long teamId);

    @Query("SELECT r FROM RosterEntry r WHERE r.season.id = :seasonId AND r.team.id = :teamId AND r.staff IS NOT NULL AND r.active = true")
    List<RosterEntry> findActiveStaffBySeasonAndTeam(@Param("seasonId") Long seasonId, @Param("teamId") Long teamId);

    @Query("SELECT r FROM RosterEntry r WHERE r.joinedDate <= :date AND (r.leftDate IS NULL OR r.leftDate >= :date)")
    List<RosterEntry> findActiveOnDate(@Param("date") LocalDate date);

}
