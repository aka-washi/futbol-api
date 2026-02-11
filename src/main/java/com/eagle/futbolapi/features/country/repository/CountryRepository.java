package com.eagle.futbolapi.features.country.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.country.entity.Country;

/**
 * Repository interface for Country entity data access operations.
 * Extends BaseRepository to inherit standard CRUD operations and custom query
 * methods.
 *
 * <p>
 * This repository is automatically implemented by Spring Data JPA at runtime.
 * Additional custom query methods can be added here if needed beyond the base
 * functionality.
 *
 * @see Country
 * @see BaseRepository
 */
@Repository
public interface CountryRepository extends BaseRepository<Country, Long> {

  Optional<Country> findByDisplayName(String displayName);

}
