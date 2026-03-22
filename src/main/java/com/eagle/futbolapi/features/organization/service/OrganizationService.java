package com.eagle.futbolapi.features.organization.service;

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
import com.eagle.futbolapi.features.organization.dto.OrganizationDto;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.mapper.OrganizationMapper;
import com.eagle.futbolapi.features.organization.repository.OrganizationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@Validated
public class OrganizationService extends BaseCrudService<Organization, Long, OrganizationDto> {

  private final OrganizationRepository repository;
  private final CountryService countryService;

  protected OrganizationService(OrganizationRepository repository, OrganizationMapper mapper,
      CountryService countryService) {
    super(repository, mapper);
    this.repository = repository;
    this.countryService = countryService;
  }

  public Optional<Organization> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Organization display name cannot be null or empty");
    }
    return repository.findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull Organization organization) {
    Objects.requireNonNull(organization, "Organization cannot be null");

    return isDuplicate(organization, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Organization organization) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(organization, "Organization cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, name={}, displayName={}, type={}, abbreviation={}",
        id, organization.getName(), organization.getDisplayName(),
        organization.getType(), organization.getAbbreviation());

    boolean result = isDuplicate(id, organization, UniquenessStrategy.ALL);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(OrganizationDto dto, Organization organization) {
    // Map country from display name or ID
    if (dto.getCountryDisplayName() != null && !dto.getCountryDisplayName().trim().isEmpty()) {
      var country = countryService.findByDisplayName(dto.getCountryDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "displayName", dto.getCountryDisplayName()));
      organization.setCountry(country);
    } else if (dto.getCountryId() != null) {
      var country = countryService.getById(dto.getCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getCountryId()));
      organization.setCountry(country);
    }

    // Map parent organization from display name or ID
    if (dto.getParentOrganizationDisplayName() != null) {
      var parent = findByDisplayName(dto.getParentOrganizationDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "displayName",
              dto.getParentOrganizationDisplayName()));
      organization.setParentOrganization(parent);
    } else if (dto.getParentOrganizationId() != null) {
      var parent = getById(dto.getParentOrganizationId())
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", dto.getParentOrganizationId()));
      organization.setParentOrganization(parent);
    }
  }

}
