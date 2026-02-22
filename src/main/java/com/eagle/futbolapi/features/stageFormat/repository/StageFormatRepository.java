package com.eagle.futbolapi.features.stageFormat.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.stageFormat.entity.StageFormat;

/**
 * Repository interface for StageFormat entity data access operations.
 */
@Repository
public interface StageFormatRepository extends BaseRepository<StageFormat, Long> {

  Optional<StageFormat> findByDisplayName(String displayName);

}
