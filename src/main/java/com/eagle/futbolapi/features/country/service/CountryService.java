package com.eagle.futbolapi.features.country.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.dto.CountryDTO;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.mapper.CountryMapper;
import com.eagle.futbolapi.features.country.repository.CountryRepository;

@Service
@Transactional
public class CountryService extends BaseCrudService<Country, Long, CountryDTO> {

  private final CountryRepository repository;

  public CountryService(CountryRepository repository, CountryMapper mapper) {
    super(repository, mapper);
    this.repository = repository;
  }

  public Optional<Country> getCountryByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Country name cannot be null or empty");
    }
    return repository.findByName(name);
  }

  public Optional<Country> getCountryByDisplayName(String displayName) {
    if (displayName == null || displayName.trim().isEmpty()) {
      throw new IllegalArgumentException("Country display name cannot be null or empty");
    }
    return repository.findByDisplayName(displayName);
  }

  public Optional<Country> getCountryByCode(String code) {
    if (code == null || code.trim().isEmpty()) {
      throw new IllegalArgumentException("Country code cannot be null or empty");
    }
    return repository.findByCode(code);
  }

  public Optional<Country> getCountryByIsoCode(String isoCode) {
    if (isoCode == null || isoCode.trim().isEmpty()) {
      throw new IllegalArgumentException("ISO code cannot be null or empty");
    }
    return repository.findByIsoCode(isoCode);
  }

  @Override
  protected boolean isDuplicate(@NotNull Country country) {
    Objects.requireNonNull(country, "Country cannot be null");

    // Check multiple unique constraints: code OR isoCode OR displayName
    return (country.getCode() != null && existsByUniqueFields(Map.of("code", country.getCode())))
        || (country.getIsoCode() != null && existsByUniqueFields(Map.of("isoCode", country.getIsoCode())))
        || (country.getDisplayName() != null && existsByUniqueFields(Map.of("displayName", country.getDisplayName())));
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Country country) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(country, "Country cannot be null");

    // Check multiple unique constraints excluding current ID: code OR isoCode OR displayName
    return (country.getCode() != null && existsByUniqueFieldsAndNotId(Map.of("code", country.getCode()), id))
        || (country.getIsoCode() != null && existsByUniqueFieldsAndNotId(Map.of("isoCode", country.getIsoCode()), id))
        || (country.getDisplayName() != null
            && existsByUniqueFieldsAndNotId(Map.of("displayName", country.getDisplayName()), id));
  }
}
