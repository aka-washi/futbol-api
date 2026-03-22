package com.eagle.futbolapi.features.venue.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.venue.dto.VenueDto;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.mapper.VenueMapper;
import com.eagle.futbolapi.features.venue.repository.VenueRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@Validated
public class VenueService extends BaseCrudService<Venue, Long, VenueDto> {

  private final VenueRepository repository;
  private final CountryService countryService;

  protected VenueService(VenueRepository repository, VenueMapper mapper, CountryService countryService) {
    super(repository, mapper);
    this.repository = repository;
    this.countryService = countryService;
  }

  public Optional<Venue> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Venue display name cannot be null or empty");
    }
    return repository.findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull Venue entity) {
    Objects.requireNonNull(entity, "Venue cannot be null");

    return isDuplicate(entity, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Venue entity) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "Venue cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, name={}, displayName={}, capacity={}",
        id, entity.getName(), entity.getDisplayName(), entity.getCapacity());

    boolean result = isDuplicate(id, entity, UniquenessStrategy.ALL);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(VenueDto dto, Venue venue) {
    // Map country from display name or ID
    if (dto.getCountryDisplayName() != null && !dto.getCountryDisplayName().trim().isEmpty()) {
      var country = countryService.findByDisplayName(dto.getCountryDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "displayName", dto.getCountryDisplayName()));
      venue.setCountry(country);
    } else if (dto.getCountryId() != null) {
      var country = countryService.getById(dto.getCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getCountryId()));
      venue.setCountry(country);
    }
  }

}
