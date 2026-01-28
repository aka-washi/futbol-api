package com.eagle.futbolapi.features.organization.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.OrganizationType;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.organization.dto.OrganizationDTO;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.mapper.OrganizationMapper;
import com.eagle.futbolapi.features.organization.repository.OrganizationRepository;

@Service
@Transactional
public class OrganizationService extends BaseCrudService<Organization, Long, OrganizationDTO> {

  private final OrganizationRepository repository;
  private final CountryService countryService;

  protected OrganizationService(
      OrganizationRepository repository,
      CountryService countryService,
      OrganizationMapper mapper) {
    super(repository, mapper);
    this.repository = repository;
    this.countryService = countryService;
  }

  public Optional<Organization> getOrganizationByName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Organization name cannot be null or empty");
    }
    return repository.findByName(name);
  }

  public Optional<Organization> getOrganizationByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Organization display name cannot be null or empty");
    }
    return repository.findByDisplayName(displayName);
  }

  public Optional<Organization> getOrganizationByAbbreviation(String abbreviation) {
    if (abbreviation == null || abbreviation.isEmpty()) {
      throw new IllegalArgumentException("Organization abbreviation cannot be null or empty");
    }
    return repository.findByAbbreviation(abbreviation);
  }

  public Page<Organization> getOrganizationsByType(OrganizationType type, Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    if (type == null) {
      throw new IllegalArgumentException("Organization type cannot be null");
    }
    return repository.findByType(type, pageable);
  }

  /**
   * Resolves related entities (Country, Parent Organization) from DTO.
   */
  @Override
  protected void resolveRelationships(OrganizationDTO dto, Organization organization) {
    // Map country from display name or ID
    if (dto.getCountryDisplayName() != null && !dto.getCountryDisplayName().trim().isEmpty()) {
      var country = countryService.getCountryByDisplayName(dto.getCountryDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "displayName", dto.getCountryDisplayName()));
      organization.setCountry(country);
    } else if (dto.getCountryId() != null) {
      var country = countryService.getById(dto.getCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getCountryId()));
      organization.setCountry(country);
    }

    // Map parent organization from display name or ID
    if (dto.getParentOrganizationDisplayName() != null) {
      var parent = getOrganizationByDisplayName(dto.getParentOrganizationDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "displayName",
              dto.getParentOrganizationDisplayName()));
      organization.setParentOrganization(parent);
    } else if (dto.getParentOrganizationId() != null) {
      var parent = getById(dto.getParentOrganizationId())
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", dto.getParentOrganizationId()));
      organization.setParentOrganization(parent);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Organization organization) {
    Objects.requireNonNull(organization, "Organization cannot be null");

    // Check composite unique constraint: name + country + type
    if (organization.getName() != null && organization.getCountry() != null && organization.getType() != null) {
      return existsByUniqueFields(Map.of(
          "name", organization.getName(),
          "country.id", organization.getCountry().getId(),
          "type", organization.getType()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Organization organization) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(organization, "Organization cannot be null");

    // Check composite unique constraint: name + country + type (excluding current ID)
    if (organization.getName() != null && organization.getCountry() != null && organization.getType() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "name", organization.getName(),
          "country.id", organization.getCountry().getId(),
          "type", organization.getType()), id);
    }
    return false;
  }

}
