package com.eagle.futbolapi.features.venue.service;

import java.util.Objects;
import java.util.Optional;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.NoChangesDetectedException;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.venue.dto.VenueDTO;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.mapper.VenueMapper;
import com.eagle.futbolapi.features.venue.repository.VenueRepository;

import jakarta.validation.constraints.NotNull;

@Service
@Transactional
public class VenueService extends BaseCrudService<Venue, Long, VenueDTO> {

  private final VenueRepository venueRepository;
  private final CountryService countryService;
  private final VenueMapper venueMapper;

  public VenueService(VenueRepository venueRepository, CountryService countryService, VenueMapper venueMapper) {
    super(venueRepository);
    this.venueRepository = venueRepository;
    this.countryService = countryService;
    this.venueMapper = venueMapper;
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
  protected Venue convertToEntity(VenueDTO dto) {
    return venueMapper.toVenue(dto);
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
  public Venue update(Long id, VenueDTO dto) {
    Venue existing = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Entity with given ID does not exist"));

    // Convert DTO to entity and resolve relationships
    Venue venue = convertToEntity(dto);
    resolveRelationships(dto, venue);

    // Preserve audit fields from existing entity
    venue.setId(id);
    venue.setCreatedAt(existing.getCreatedAt());
    venue.setCreatedBy(existing.getCreatedBy());

    // Validate and save
    if (existing.equals(venue)) {
      throw new NoChangesDetectedException("No changes detected for entity", id);
    }

    if (isDuplicate(id, venue)) {
      throw new IllegalArgumentException("Duplicate entity");
    }

    return repository.save(venue);
  }

  @Override
  protected boolean isDuplicate(@NotNull Venue venue) {
    Objects.requireNonNull(venue, "Venue cannot be null");

    return venue.getName() != null && venueRepository.existsByName(venue.getName())
        || venue.getDisplayName() != null && venueRepository.existsByDisplayName(venue.getDisplayName())
        || venue.getCountry() != null && venueRepository.existsByCountryId(venue.getCountry().getId());
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Venue venue) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(venue, "Venue details cannot be null");

    return venue.getName() != null && venueRepository.existsByNameAndIdNot(venue.getName(), id)
        || venue.getDisplayName() != null && venueRepository.existsByDisplayNameAndIdNot(venue.getDisplayName(), id)
        || venue.getCountry() != null && venueRepository.existsByCountryIdAndIdNot(venue.getCountry().getId(), id);
  }

}
