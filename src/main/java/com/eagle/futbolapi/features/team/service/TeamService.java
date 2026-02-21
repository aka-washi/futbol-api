package com.eagle.futbolapi.features.team.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.team.dto.TeamDto;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.mapper.TeamMapper;
import com.eagle.futbolapi.features.team.repository.TeamRepository;
import com.eagle.futbolapi.features.venue.service.VenueService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Team entity business logic.
 */
@Slf4j
@Service
@Transactional
public class TeamService extends BaseCrudService<Team, Long, TeamDto> {

  private final TeamRepository repository;
  private final OrganizationService organizationService;
  private final CountryService countryService;
  private final VenueService venueService;

  protected TeamService(TeamRepository repository, TeamMapper mapper,
      OrganizationService organizationService, CountryService countryService, VenueService venueService) {
    super(repository, mapper);
    this.repository = repository;
    this.organizationService = organizationService;
    this.countryService = countryService;
    this.venueService = venueService;
  }

  public Optional<Team> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Team display name cannot be null or empty");
    }
    return repository.findByDisplayName(displayName);
  }


  @Override
  protected boolean isDuplicate(@NotNull Team team) {
    Objects.requireNonNull(team, "Team cannot be null");

    return isDuplicate(team, UniquenessStrategy.ANY);
  }


  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Team team) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(team, "Team cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, name={}, displayName={}, gender={}, ageCategory={}",
        id, team.getName(), team.getDisplayName(), team.getGender(), team.getAgeCategory());

    boolean result = isDuplicate(id, team, UniquenessStrategy.ANY);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(TeamDto dto, Team team) {
    // Map organization from display name or ID
    if (dto.getOrganizationDisplayName() != null && !dto.getOrganizationDisplayName().trim().isEmpty()) {
      var organization = organizationService.findByDisplayName(dto.getOrganizationDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "displayName",
              dto.getOrganizationDisplayName()));
      team.setOrganization(organization);
    } else if (dto.getOrganizationId() != null) {
      var organization = organizationService.getById(dto.getOrganizationId())
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", dto.getOrganizationId()));
      team.setOrganization(organization);
    }

    // Map country from display name or ID
    if (dto.getCountryDisplayName() != null && !dto.getCountryDisplayName().trim().isEmpty()) {
      var country = countryService.findByDisplayName(dto.getCountryDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "displayName", dto.getCountryDisplayName()));
      team.setCountry(country);
    } else if (dto.getCountryId() != null) {
      var country = countryService.getById(dto.getCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getCountryId()));
      team.setCountry(country);
    }

    // Map venue from display name or ID (optional relationship)
    if (dto.getVenueDisplayName() != null && !dto.getVenueDisplayName().trim().isEmpty()) {
      var venue = venueService.findByDisplayName(dto.getVenueDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Venue", "displayName", dto.getVenueDisplayName()));
      team.setVenue(venue);
    } else if (dto.getVenueId() != null) {
      var venue = venueService.getById(dto.getVenueId())
          .orElseThrow(() -> new ResourceNotFoundException("Venue", "id", dto.getVenueId()));
      team.setVenue(venue);
    }
  }

}
