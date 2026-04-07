package com.eagle.futbolapi.features.team.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.team.dto.TeamDto;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.mapper.TeamMapper;
import com.eagle.futbolapi.features.team.repository.TeamRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Team entity business logic.
 */
@Slf4j
@Service
@Transactional
@Validated
public class TeamService extends BaseCrudService<Team, Long, TeamDto> {

  private final TeamRepository repository;
  private final OrganizationService organizationService;
  private final CountryService countryService;

  protected TeamService(TeamRepository repository, TeamMapper mapper,
      OrganizationService organizationService, CountryService countryService) {
    super(repository, mapper);
    this.repository = repository;
    this.organizationService = organizationService;
    this.countryService = countryService;
  }

  public Optional<Team> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Team display name cannot be null or empty");
    }
    return repository.findByDisplayName(displayName);
  }

  public Optional<Team> findByDisplayNameAndGender(String displayName, Gender gender) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Team display name cannot be null or empty");
    }
    if (gender == null) {
      throw new IllegalArgumentException("Gender cannot be null");
    }
    return repository.findByDisplayNameAndGender(displayName, gender);
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
    String organizationDisplayName = dto.getOrganizationDisplayName();
    Long organizationId = dto.getOrganizationId();
    if (organizationDisplayName != null && !organizationDisplayName.trim().isEmpty()) {
      var organization = organizationService.findByDisplayName(organizationDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "displayName",
              organizationDisplayName));
      team.setOrganization(organization);
    } else if (organizationId != null) {
      var organization = organizationService.getById(organizationId)
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", organizationId));
      team.setOrganization(organization);
    }

    // Map country from display name or ID
    String countryDisplayName = dto.getCountryDisplayName();
    Long countryId = dto.getCountryId();
    if (countryDisplayName != null && !countryDisplayName.trim().isEmpty()) {
      var country = countryService.findByDisplayName(countryDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("Country", "displayName", countryDisplayName));
      team.setCountry(country);
    } else if (countryId != null) {
      var country = countryService.getById(countryId)
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", countryId));
      team.setCountry(country);
    }
  }

}
