package com.eagle.futbolapi.features.team.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.AgeCategory;
import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.team.dto.TeamDTO;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.mapper.TeamMapper;
import com.eagle.futbolapi.features.team.repository.TeamRepository;
import com.eagle.futbolapi.features.venue.service.VenueService;

@Service
@Transactional
public class TeamService extends BaseCrudService<Team, Long, TeamDTO> {

  private final TeamRepository teamRepository;
  private final CountryService countryService;
  private final OrganizationService organizationService;
  private final VenueService venueService;
  private final TeamMapper teamMapper;

  public TeamService(TeamRepository teamRepository, CountryService countryService,
      OrganizationService organizationService, VenueService venueService, TeamMapper teamMapper) {
    super(teamRepository);
    this.teamRepository = teamRepository;
    this.countryService = countryService;
    this.organizationService = organizationService;
    this.venueService = venueService;
    this.teamMapper = teamMapper;
  }

  public Optional<Team> getTeamByDisplayName(String displayName) {
    if (displayName == null || displayName.trim().isEmpty()) {
      throw new IllegalArgumentException("Team display name cannot be null or empty");
    }
    return teamRepository.findByDisplayName(displayName);
  }

  public Optional<Team> getTeamByDisplayNameAndGenderAndAgeCategory(
      String displayName, Gender gender, AgeCategory ageCategory) {
    if (displayName == null || displayName.trim().isEmpty()) {
      throw new IllegalArgumentException("Team display name cannot be null or empty");
    }
    if (gender == null) {
      throw new IllegalArgumentException("Gender cannot be null");
    }
    if (ageCategory == null) {
      throw new IllegalArgumentException("Age category cannot be null");
    }
    return teamRepository.findByDisplayNameAndGenderAndAgeCategory(displayName, gender, ageCategory);
  }

  public Page<Team> getTeamsByGender(Gender gender, Pageable pageable) {
    if (gender == null) {
      throw new IllegalArgumentException("Gender cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return teamRepository.findByGender(gender, pageable);
  }

  public Page<Team> getTeamsByGenderAndAgeCategoryAndOrganizationId(Gender gender, AgeCategory ageCategory,
      Long organizationId, Pageable pageable) {
    if (gender == null) {
      throw new IllegalArgumentException("Gender cannot be null");
    }
    if (ageCategory == null) {
      throw new IllegalArgumentException("Age category cannot be null");
    }
    if (organizationId == null) {
      throw new IllegalArgumentException("Organization ID cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return teamRepository.findByGenderAndAgeCategoryAndOrganizationId(gender, ageCategory, organizationId, pageable);
  }

  public Page<Team> getTeamsByGenderAndAgeCategoryAndCountryId(Gender gender, AgeCategory ageCategory, Long countryId,
      Pageable pageable) {
    if (gender == null) {
      throw new IllegalArgumentException("Gender cannot be null");
    }
    if (ageCategory == null) {
      throw new IllegalArgumentException("Age category cannot be null");
    }
    if (countryId == null) {
      throw new IllegalArgumentException("Country ID cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return teamRepository.findByGenderAndAgeCategoryAndCountryId(gender, ageCategory, countryId, pageable);
  }

  @Override
  protected Team convertToEntity(TeamDTO dto) {
    return teamMapper.toTeam(dto);
  }

  @Override
  protected void resolveRelationships(TeamDTO dto, Team team) {
    // Map country from display name or ID
    if (dto.getCountryDisplayName() != null && !dto.getCountryDisplayName().trim().isEmpty()) {
      var country = countryService.getCountryByDisplayName(dto.getCountryDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "displayName", dto.getCountryDisplayName()));
      team.setCountry(country);
    } else if (dto.getCountryId() != null) {
      var country = countryService.getById(dto.getCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getCountryId()));
      team.setCountry(country);
    }

    // Map organization from display name or ID
    if (dto.getOrganizationDisplayName() != null && !dto.getOrganizationDisplayName().trim().isEmpty()) {
      var organization = organizationService.getOrganizationByDisplayName(dto.getOrganizationDisplayName())
          .orElseThrow(
              () -> new ResourceNotFoundException("Organization", "displayName", dto.getOrganizationDisplayName()));
      team.setOrganization(organization);
    } else if (dto.getOrganizationId() != null) {
      var organization = organizationService.getById(dto.getOrganizationId())
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", dto.getOrganizationId()));
      team.setOrganization(organization);
    }

    // Map venue from display name or ID
    if (dto.getVenueDisplayName() != null && !dto.getVenueDisplayName().trim().isEmpty()) {
      var venue = venueService.getVenueByDisplayName(dto.getVenueDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Venue", "displayName", dto.getVenueDisplayName()));
      team.setVenue(venue);
    } else if (dto.getVenueId() != null) {
      var venue = venueService.getById(dto.getVenueId())
          .orElseThrow(() -> new ResourceNotFoundException("Venue", "id", dto.getVenueId()));
      team.setVenue(venue);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Team entity) {
    Objects.requireNonNull(entity, "Entity cannot be null");

    return teamRepository.existsByNameAndGenderAndAgeCategoryAndOrganizationId(
        entity.getName(),
        entity.getGender(),
        entity.getAgeCategory(),
        entity.getOrganization().getId());
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Team entity) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "Entity cannot be null");

    return teamRepository.existsByNameAndGenderAndAgeCategoryAndOrganizationIdAndIdNot(
        entity.getName(),
        entity.getGender(),
        entity.getAgeCategory(),
        entity.getOrganization().getId(),
        id);
  }

}
