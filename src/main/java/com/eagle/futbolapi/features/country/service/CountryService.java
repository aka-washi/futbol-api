package com.eagle.futbolapi.features.country.service;

import java.util.Objects;
import java.util.Optional;

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
 * Service for Country entity business logic.
 */
@Slf4j
@Service
@Transactional
public class CountryService extends BaseCrudService<Country, Long, CountryDto> {

  protected CountryService(CountryRepository repository, CountryMapper mapper) {
    super(repository, mapper);
  }

  public Optional<Country> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Country display name cannot be null or empty");
    }
    return ((CountryRepository) repository).findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull Country country) {
    Objects.requireNonNull(country, "Country cannot be null");

    return isDuplicate(country, UniquenessStrategy.ANY);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Country country) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(country, "Country cannot be null");

    return isDuplicate(id, country, UniquenessStrategy.ANY);
  }

}
