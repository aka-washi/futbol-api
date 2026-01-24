package com.eagle.futbolapi.features.lineup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.lineup.entity.Lineup;

@Repository
public interface LineupRepository extends JpaRepository<Lineup, Long> {

  boolean existsByMatchIdAndTeamIdAndPlayerId(Long matchId, Long teamId, Long playerId);

  @Query("SELECT COUNT(l) > 0 FROM Lineup l WHERE l.match.id = :matchId AND l.team.id = :teamId AND l.player.id = :playerId AND l.id != :id")
  boolean existsByMatchIdAndTeamIdAndPlayerIdAndIdNot(@Param("matchId") Long matchId, @Param("teamId") Long teamId,
      @Param("playerId") Long playerId, @Param("id") Long id);

}
