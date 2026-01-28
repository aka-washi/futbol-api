package com.eagle.futbolapi.features.match.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.match.entity.Match;

@Repository
public interface MatchRepository extends BaseRepository<Match, Long> {

  Optional<Match> findByName(String name);

  Optional<Match> findByDisplayName(String displayName);

  // Unique field methods: matchday + homeTeam + awayTeam
  Optional<Match> findByMatchdayIdAndHomeTeamIdAndAwayTeamId(Long matchdayId, Long homeTeamId, Long awayTeamId);

  boolean existsByMatchdayIdAndHomeTeamIdAndAwayTeamId(Long matchdayId, Long homeTeamId, Long awayTeamId);

  boolean existsByMatchdayIdAndHomeTeamIdAndAwayTeamIdAndIdNot(Long matchdayId, Long homeTeamId, Long awayTeamId,
      Long id);
}
