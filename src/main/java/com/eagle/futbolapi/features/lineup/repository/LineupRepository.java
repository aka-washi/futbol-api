package com.eagle.futbolapi.features.lineup.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.lineup.entity.Lineup;

@Repository
public interface LineupRepository extends BaseRepository<Lineup, Long> {

  // Unique field methods: match + team + player
  Optional<Lineup> findByMatchIdAndTeamIdAndPlayerId(Long matchId, Long teamId, Long playerId);

  boolean existsByMatchIdAndTeamIdAndPlayerId(Long matchId, Long teamId, Long playerId);

  @Query("SELECT COUNT(l) > 0 FROM Lineup l WHERE l.match.id = :matchId AND l.team.id = :teamId AND l.player.id = :playerId AND l.id != :id")
  boolean existsByMatchIdAndTeamIdAndPlayerIdAndIdNot(@Param("matchId") Long matchId, @Param("teamId") Long teamId,
      @Param("playerId") Long playerId, @Param("id") Long id);

}
