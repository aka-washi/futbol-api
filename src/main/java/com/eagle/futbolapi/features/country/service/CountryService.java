package com.eagle.futbolapi.features.country.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.repository.CountryRepository;

@Service
@Transactional
public class CountryService extends BaseCrudService<Country, Long> {

  private final CountryRepository countryRepository;

  public CountryService(CountryRepository countryRepository) {
    super(countryRepository);
    this.countryRepository = countryRepository;
  }

  public Optional<Country> getCountryByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Country name cannot be null or empty");
    }
    return countryRepository.findByName(name);
  }

  public Optional<Country> getCountryByDisplayName(String displayName) {
    if (displayName == null || displayName.trim().isEmpty()) {
      throw new IllegalArgumentException("Country display name cannot be null or empty");
    }
    return countryRepository.findByDisplayName(displayName);
  }

  public Optional<Country> getCountryByCode(String code) {
    if (code == null || code.trim().isEmpty()) {
      throw new IllegalArgumentException("Country code cannot be null or empty");
    }
    return countryRepository.findByCode(code);
  }

  public Optional<Country> getCountryByIsoCode(String isoCode) {
    if (isoCode == null || isoCode.trim().isEmpty()) {
      throw new IllegalArgumentException("ISO code cannot be null or empty");
    }
    return countryRepository.findByIsoCode(isoCode);
  }

  @Override
  public Country update(Long id, Country country) {
    Country existing = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
    country.setCreatedAt(existing.getCreatedAt());
    country.setId(id);
    return super.update(id, country);
  }

  @Override
  protected boolean isDuplicate(@NotNull Country country) {
    Objects.requireNonNull(country, "Country cannot be null");

    return (country.getCode() != null && countryRepository.existsByCode(country.getCode()))
        || (country.getIsoCode() != null && countryRepository.existsByIsoCode(country.getIsoCode()))
        || (country.getDisplayName() != null && countryRepository.existsByDisplayName(country.getDisplayName()));
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Country country) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(country, "Country cannot be null");

    return (country.getCode() != null && countryRepository.existsByCodeAndIdNot(country.getCode(), id))
        || (country.getIsoCode() != null && countryRepository.existsByIsoCodeAndIdNot(country.getIsoCode(), id))
        || (country.getDisplayName() != null
            && countryRepository.existsByDisplayNameAndIdNot(country.getDisplayName(), id));
  }
}
