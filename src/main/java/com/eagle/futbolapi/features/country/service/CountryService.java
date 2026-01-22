package com.eagle.futbolapi.features.country.service;

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

    return (country.getCode() != null && repository.existsByCode(country.getCode()))
        || (country.getIsoCode() != null && repository.existsByIsoCode(country.getIsoCode()))
        || (country.getDisplayName() != null && repository.existsByDisplayName(country.getDisplayName()));
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Country country) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(country, "Country cannot be null");

    return (country.getCode() != null && repository.existsByCodeAndIdNot(country.getCode(), id))
        || (country.getIsoCode() != null && repository.existsByIsoCodeAndIdNot(country.getIsoCode(), id))
        || (country.getDisplayName() != null
            && repository.existsByDisplayNameAndIdNot(country.getDisplayName(), id));
  }
}
