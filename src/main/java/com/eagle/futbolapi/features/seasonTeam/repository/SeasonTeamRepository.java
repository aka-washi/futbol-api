package com.eagle.futbolapi.features.seasonTeam.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.seasonTeam.entity.SeasonTeam;

/**
 * Repository interface for SeasonTeam entity data access operations.
 */
@Repository
public interface SeasonTeamRepository extends BaseRepository<SeasonTeam, Long> {

}
