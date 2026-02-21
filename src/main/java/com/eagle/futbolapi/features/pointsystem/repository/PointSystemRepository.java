package com.eagle.futbolapi.features.pointsystem.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;

/**
 * Repository interface for Point System entity data access operations.
 */
@Repository
public interface PointSystemRepository extends BaseRepository<PointSystem, Long> {

}
