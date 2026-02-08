package com.eagle.futbolapi.features.season.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.season.entity.Season;

/**
 * Repository interface for Season entity data access operations.
 * Extends BaseRepository to inherit standard CRUD operations and custom query
 * methods.
 *
 * <p>
 * This repository is automatically implemented by Spring Data JPA at runtime.
 * Additional custom query methods can be added here if needed beyond the base
 * functionality.
 *
 * @see Season
 * @see BaseRepository
 */
@Repository
public interface SeasonRepository extends BaseRepository<Season, Long> {

}
