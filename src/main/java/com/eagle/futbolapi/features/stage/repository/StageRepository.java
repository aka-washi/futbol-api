package com.eagle.futbolapi.features.stage.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.stage.entity.Stage;

/**
 * Repository interface for Stage entity data access operations.
 */
@Repository
public interface StageRepository extends BaseRepository<Stage, Long> {

  Optional<Stage> findByDisplayName(String displayName);

}
