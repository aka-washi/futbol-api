package com.eagle.futbolapi.features.organization.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.NoChangesDetectedException;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.organization.dto.OrganizationDTO;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.entity.OrganizationType;
import com.eagle.futbolapi.features.organization.mapper.OrganizationMapper;
import com.eagle.futbolapi.features.organization.repository.OrganizationRepository;

@Service
@Transactional
public class OrganizationService extends BaseCrudService<Organization, Long, OrganizationDTO> {

  private final OrganizationRepository organizationRepository;
  private final CountryService countryService;
  private final OrganizationMapper organizationMapper;

  protected OrganizationService(
      OrganizationRepository organizationRepository,
      CountryService countryService,
      OrganizationMapper organizationMapper) {
    super(organizationRepository);
    this.organizationRepository = organizationRepository;
    this.countryService = countryService;
    this.organizationMapper = organizationMapper;
  }

  public Optional<Organization> getOrganizationByName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Organization name cannot be null or empty");
    }
    return organizationRepository.findByName(name);
  }

  public Optional<Organization> getOrganizationByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Organization display name cannot be null or empty");
    }
    return organizationRepository.findByDisplayName(displayName);
  }

  public Optional<Organization> getOrganizationByAbbreviation(String abbreviation) {
    if (abbreviation == null || abbreviation.isEmpty()) {
      throw new IllegalArgumentException("Organization abbreviation cannot be null or empty");
    }
    return organizationRepository.findByAbbreviation(abbreviation);
  }

  public Page<Organization> getOrganizationsByType(OrganizationType type, Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    if (type == null) {
      throw new IllegalArgumentException("Organization type cannot be null");
    }
    return organizationRepository.findByType(type, pageable);
  }

  @Override
  protected Organization convertToEntity(OrganizationDTO dto) {
    return organizationMapper.toOrganization(dto);
  }

  /**
   * Resolves related entities (Country, Parent Organization) from DTO.
   */
  @Override
  protected void resolveRelationships(OrganizationDTO dto, Organization organization) {
    // Map country from display name or ID
    if (dto.getCountryDisplayName() != null) {
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
  public Organization update(Long id, OrganizationDTO dto) {
    // Get existing entity to preserve audit fields
    Organization existing = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Entity with given ID does not exist"));

    // Convert DTO to entity and resolve relationships
    Organization organization = convertToEntity(dto);
    resolveRelationships(dto, organization);

    // Preserve audit fields from existing entity
    organization.setId(id);
    organization.setCreatedAt(existing.getCreatedAt());
    organization.setCreatedBy(existing.getCreatedBy());

    // Validate and save
    if (existing.equals(organization)) {
      throw new NoChangesDetectedException("No changes detected for entity", id);
    }

    if (isDuplicate(id, organization)) {
      throw new IllegalArgumentException("Duplicate entity");
    }

    return repository.save(organization);
  }

  @Override
  protected boolean isDuplicate(@NotNull Organization organization) {
    Objects.requireNonNull(organization, "Organization cannot be null");

    return (organization.getAbbreviation() != null
        && organizationRepository.existsByAbbreviation(organization.getAbbreviation())
        || (organization.getDisplayName() != null
            && organizationRepository.existsByDisplayName(organization.getDisplayName()))
        || (organization.getName() != null && organizationRepository.existsByName(organization.getName())));
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Organization organization) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(organization, "Organization cannot be null");

    return (organization.getAbbreviation() != null
        && organizationRepository.existsByAbbreviationAndIdNot(organization.getAbbreviation(), id))
        || (organization.getDisplayName() != null
            && organizationRepository.existsByDisplayNameAndIdNot(organization.getDisplayName(), id))
        || (organization.getName() != null
            && organizationRepository.existsByNameAndIdNot(organization.getName(), id));
  }

}
