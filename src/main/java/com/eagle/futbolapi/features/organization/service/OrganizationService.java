package com.eagle.futbolapi.features.organization.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.entity.OrganizationType;
import com.eagle.futbolapi.features.organization.repository.OrganizationRepository;

import jakarta.validation.constraints.NotNull;

@Service
@Transactional
public class OrganizationService extends BaseCrudService<Organization, Long> {

  private final OrganizationRepository organizationRepository;

  protected OrganizationService(OrganizationRepository organizationRepository) {
    super(organizationRepository);
    this.organizationRepository = organizationRepository;
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

  public Page<Organization> getOrganizationsByType(OrganizationType type,
      org.springframework.data.domain.Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    if (type == null) {
      throw new IllegalArgumentException("Organization type cannot be null");
    }
    return organizationRepository.findByType(type, pageable);
  }

  @Override
  public Organization update(Long id, Organization organization) {
    Organization existing = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
    organization.setCreatedAt(existing.getCreatedAt());
    organization.setId(id);
    return super.update(id, organization);
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
