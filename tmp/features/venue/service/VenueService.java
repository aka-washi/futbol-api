package com.eagle.futbolapi.features.venue.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.venue.dto.VenueDTO;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.mapper.VenueMapper;
import com.eagle.futbolapi.features.venue.repository.VenueRepository;

@Service
@Transactional
public class VenueService extends BaseCrudService<Venue, Long, VenueDTO> {

  private final VenueRepository venueRepository;
  private final CountryService countryService;

  public VenueService(VenueRepository venueRepository, CountryService countryService, VenueMapper mapper) {
    super(venueRepository, mapper);
    this.venueRepository = venueRepository;
    this.countryService = countryService;

  }

  public Optional<Venue> getVenueByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Venue name cannot be null or empty");
    }
    return venueRepository.findByName(name);
  }

  public Optional<Venue> getVenueByDisplayName(String displayName) {
    if (displayName == null || displayName.trim().isEmpty()) {
      throw new IllegalArgumentException("Venue display name cannot be null or empty");
    }
    return venueRepository.findByDisplayName(displayName);
  }

  public Page<Venue> getVenuesByCountryId(@NotNull Long countryId, Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return venueRepository.findByCountryId(countryId, pageable);
  }

  @Override
  protected void resolveRelationships(VenueDTO dto, Venue venue) {
    if (dto.getCountryDisplayName() != null) {
      var country = countryService.getCountryByDisplayName(dto.getCountryDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Country with display name " + dto.getCountryDisplayName() + " not found"));
      venue.setCountry(country);
    } else if (dto.getCountryId() != null) {
      var country = countryService.getById(dto.getCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country with ID " + dto.getCountryId() + " not found"));
      venue.setCountry(country);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Venue venue) {
    Objects.requireNonNull(venue, "Venue cannot be null");

    // Check composite unique constraint: name + country
    if (venue.getName() != null && venue.getCountry() != null) {
      return existsByUniqueFields(Map.of(
          "name", venue.getName(),
          "country.id", venue.getCountry().getId()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Venue venue) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(venue, "Venue details cannot be null");

    // Check composite unique constraint: name + country (excluding current ID)
    if (venue.getName() != null && venue.getCountry() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "name", venue.getName(),
          "country.id", venue.getCountry().getId()), id);
    }
    return false;
  }

}
