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

  private final CountryRepository countryRepository;
  private final CountryMapper countryMapper;

  public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
    super(countryRepository);
    this.countryRepository = countryRepository;
    this.countryMapper = countryMapper;
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
  protected Country convertToEntity(CountryDTO dto) {
    return countryMapper.toCountry(dto);
  }

  // No relationships to resolve for Country

  @Override
  public Country update(Long id, CountryDTO dto) {
    // Get existing entity to preserve audit fields
    Country existing = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
    
    // Convert DTO to entity
    Country country = convertToEntity(dto);
    
    // Preserve audit fields from existing entity
    country.setId(id);
    country.setCreatedAt(existing.getCreatedAt());
    country.setCreatedBy(existing.getCreatedBy());
    
    // Validate and save
    if (existing.equals(country)) {
      throw new com.eagle.futbolapi.features.base.exception.NoChangesDetectedException("No changes detected for entity", id);
    }
    
    if (isDuplicate(id, country)) {
      throw new IllegalArgumentException("Duplicate entity");
    }
    
    return repository.save(country);
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
