package com.eagle.futbolapi.features.seasonParticipation.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.seasonParticipation.entity.SeasonParticipation;

@Repository
public interface SeasonParticipationRepository extends BaseRepository<SeasonParticipation, Long> {

  boolean existsBySeasonIdAndTeamId(Long seasonId, Long teamId);

  boolean existsBySeasonIdAndTeamIdAndIdNot(Long seasonId, Long teamId, Long id);

}
