package com.eagle.futbolapi.features.competition.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.competition.entity.Competition;

/**
 * Repository interface for Competition entity data access operations.
 */
@Repository
public interface CompetitionRepository extends BaseRepository<Competition, Long> {

  Optional<Competition> findByDisplayName(String displayName);

}
