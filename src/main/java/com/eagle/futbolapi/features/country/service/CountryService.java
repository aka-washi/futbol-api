package com.eagle.futbolapi.features.country.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.repository.CountryRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Service
@Transactional
public class CountryService extends BaseCrudService<Country, Long> {

  private final CountryRepository countryRepository;

  public CountryService(CountryRepository countryRepository) {
    super(countryRepository);
    this.countryRepository = countryRepository;

  }

  @Override
  protected boolean isDuplicate(@NotNull Country country) {
    Objects.requireNonNull(country, "Country cannot be null");

    // Check for duplicate code
    if (country.getCode() != null && countryRepository.existsByCode(country.getCode())) {
      return true;
    }

    if (country.getIsoCode() != null && countryRepository.existsByIsoCode(country.getIsoCode())) {
      return true;
    }
    return country.getDisplayName() != null && countryRepository.existsByDisplayName(country.getDisplayName());
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Country country) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(country, "Country details cannot be null");

    if (country.getDisplayName() == null || country.getCode() == null || country.getIsoCode() == null) {
      throw new IllegalArgumentException("Unique Registration Key and Email cannot be null");
    }

    boolean existsByCode = country.getCode() != null && countryRepository.existsByCodeAndIdNot(country.getCode(), id);
    boolean existsByIsoCode = country.getIsoCode() != null &&countryRepository.existsByIsoCodeAndIdNot(country.getIsoCode(), id);
    boolean existsByDisplayName = country.getDisplayName() != null && countryRepository.existsByDisplayNameAndIdNot(country.getDisplayName(), id);

    Country existingCountry = getById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Country", "id", id));

    // Check for duplicate code
    if (country.getCode() != null && !country.getCode().equals(existingCountry.getCode())
        && countryRepository.existsByCode(country.getCode())) {
      return true;
        }

    // Check for duplicate ISO code
    if (country.getIsoCode() != null && !country.getIsoCode().equals(existingCountry.getIsoCode())
        && countryRepository.existsByIsoCode(country.getIsoCode())) {
      return true;
    }

    // Check for duplicate display name
    if (country.getDisplayName() != null && !country.getDisplayName().equals(existingCountry.getDisplayName())
        && countryRepository.existsByDisplayName(country.getDisplayName())) {
      return true;
    }

    return false;
  }
}