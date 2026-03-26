package com.eagle.futbolapi.features.lineup.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.lineup.entity.Lineup;

@Repository
public interface LineupRepository extends BaseRepository<Lineup, Long> {

  // Unique field methods: match + team (one lineup per team per match)
  Optional<Lineup> findByMatchIdAndTeamId(Long matchId, Long teamId);

  boolean existsByMatchIdAndTeamId(Long matchId, Long teamId);

  boolean existsByMatchIdAndTeamIdAndIdNot(Long matchId, Long teamId, Long id);

  Optional<Lineup> findByDisplayName(String displayName);

}
