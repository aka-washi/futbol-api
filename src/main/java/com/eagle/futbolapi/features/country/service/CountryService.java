package com.eagle.futbolapi.features.country.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.dto.CountryDto;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.mapper.CountryMapper;
import com.eagle.futbolapi.features.country.repository.CountryRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing Country entities.
 * Provides CRUD operations and business logic specific to countries,
 * including duplicate checking based on uniqueness constraints.
 */
@Slf4j
@Service
@Transactional
public class CountryService extends BaseCrudService<Country, Long, CountryDto> {

  /**
   * Constructs a new CountryService with the required dependencies.
   *
   * @param repository the CountryRepository for data access
   * @param mapper     the CountryMapper for entity-DTO conversion
   */
  protected CountryService(CountryRepository repository, CountryMapper mapper) {
    super(repository, mapper);
  }

  /**
   * Checks if a country entity is a duplicate based on uniqueness constraints.
   * Uses ANY strategy to check against all unique fields.
   *
   * @param country the country entity to check for duplicates
   * @return true if the country is a duplicate, false otherwise
   * @throws NullPointerException if country is null
   */
  @Override
  protected boolean isDuplicate(@NotNull Country country) {
    Objects.requireNonNull(country, "Country cannot be null");

    return isDuplicate(country, UniquenessStrategy.ANY);
  }

  /**
   * Checks if a country entity is a duplicate, excluding the entity with the
   * given ID.
   * Uses ANY strategy to check against all unique fields.
   *
   * @param id      the ID of the entity to exclude from duplicate check
   * @param country the country entity to check for duplicates
   * @return true if the country is a duplicate, false otherwise
   * @throws NullPointerException if id or country is null
   */
  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Country country) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(country, "Country cannot be null");

    return isDuplicate(id, country, UniquenessStrategy.ANY);
  }

}
