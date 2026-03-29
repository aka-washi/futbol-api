package com.eagle.futbolapi.features.seasonParticipation.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.seasonParticipation.entity.SeasonParticipation;

/**
 * Repository interface for SeasonTeam entity data access operations.
 */
@Repository
public interface SeasonParticipationRepository extends BaseRepository<SeasonParticipation, Long> {

}
