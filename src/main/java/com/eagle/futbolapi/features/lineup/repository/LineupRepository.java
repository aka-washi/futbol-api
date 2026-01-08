package com.eagle.futbolapi.features.lineup.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.entity.LineupType;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface LineupRepository extends JpaRepository<Lineup, Long> {

    Page<Lineup> findByMatch(Match match, Pageable pageable);

    Page<Lineup> findByMatchId(Long matchId, Pageable pageable);

    Page<Lineup> findByTeam(Team team, Pageable pageable);

    Page<Lineup> findByTeamId(Long teamId, Pageable pageable);

    Page<Lineup> findByPlayer(Player player, Pageable pageable);

    Page<Lineup> findByPlayerId(Long playerId, Pageable pageable);

    Page<Lineup> findByType(LineupType type, Pageable pageable);

    Page<Lineup> findByMatchIdAndTeamId(Long matchId, Long teamId, Pageable pageable);

    Page<Lineup> findByMatchIdAndType(Long matchId, LineupType type, Pageable pageable);

    Page<Lineup> findByMatchIdAndTeamIdAndType(Long matchId, Long teamId, LineupType type,
            Pageable pageable);

    Optional<Lineup> findByMatchIdAndPlayerId(Long matchId, Long playerId);

    @Query("SELECT l FROM Lineup l WHERE l.match.id = :matchId AND l.team.id = :teamId AND l.type = 'STARTING' ORDER BY l.position")
    Page<Lineup> findStartingLineupByMatchAndTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId,
            Pageable pageable);

    @Query("SELECT l FROM Lineup l WHERE l.match.id = :matchId AND l.team.id = :teamId AND l.type = 'SUBSTITUTE' ORDER BY l.position")
    Page<Lineup> findSubstitutesByMatchAndTeam(@Param("matchId") Long matchId, @Param("teamId") Long teamId,
            Pageable pageable);

    Page<Lineup> findByCaptain(Boolean captain, Pageable pageable);

    Optional<Lineup> findByMatchIdAndTeamIdAndCaptain(Long matchId, Long teamId, Boolean captain);

}
