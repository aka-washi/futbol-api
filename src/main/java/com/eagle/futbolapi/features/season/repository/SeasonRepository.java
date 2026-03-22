package com.eagle.futbolapi.features.season.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.season.entity.Season;

/**
 * Repository interface for Season entity data access operations.
 */
@Repository
public interface SeasonRepository extends BaseRepository<Season, Long> {

  Optional<Season> findByDisplayName(String displayName);

}
