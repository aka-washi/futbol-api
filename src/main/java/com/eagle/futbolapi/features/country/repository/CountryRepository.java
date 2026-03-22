package com.eagle.futbolapi.features.country.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.country.entity.Country;

/**
 * Repository interface for Country entity data access operations.
 */
@Repository
public interface CountryRepository extends BaseRepository<Country, Long> {

  Optional<Country> findByDisplayName(String displayName);

}
