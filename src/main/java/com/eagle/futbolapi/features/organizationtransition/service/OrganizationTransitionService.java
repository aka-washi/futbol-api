package com.eagle.futbolapi.features.organizationtransition.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.organizationtransition.dto.OrganizationTransitionDto;
import com.eagle.futbolapi.features.organizationtransition.entity.OrganizationTransition;
import com.eagle.futbolapi.features.organizationtransition.mapper.OrganizationTransitionMapper;
import com.eagle.futbolapi.features.organizationtransition.repository.OrganizationTransitionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for OrganizationTransition entity business logic.
 */
@Slf4j
@Service
@Transactional
@Validated
public class OrganizationTransitionService extends BaseCrudService<OrganizationTransition, Long, OrganizationTransitionDto> {

  private final OrganizationService organizationService;

  protected OrganizationTransitionService(OrganizationTransitionRepository repository, OrganizationTransitionMapper mapper,
      OrganizationService organizationService) {
    super(repository, mapper);
    this.organizationService = organizationService;
  }

  @Override
  protected boolean isDuplicate(@NotNull OrganizationTransition organizationTransition) {
    Objects.requireNonNull(organizationTransition, "OrganizationTransition cannot be null");

    return isDuplicate(organizationTransition, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull OrganizationTransition organizationTransition) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(organizationTransition, "OrganizationTransition cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, fromOrganizationId={}, fromOrganizationDisplayName={}, toOrganizationId={}, toOrganizationDisplayName={}",
        id, organizationTransition.getFromOrganization() != null ? organizationTransition.getFromOrganization().getId() : null,
        organizationTransition.getFromOrganization() != null ? organizationTransition.getFromOrganization().getDisplayName() : null,
        organizationTransition.getToOrganization() != null ? organizationTransition.getToOrganization().getId() : null,
        organizationTransition.getToOrganization() != null ? organizationTransition.getToOrganization().getDisplayName() : null);

    boolean result = isDuplicate(id, organizationTransition, UniquenessStrategy.ALL);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(OrganizationTransitionDto dto, OrganizationTransition organizationTransition) {
    // Map fromOrganization from display name or ID
    String fromOrganizationDisplayName = dto.getFromOrganizationDisplayName();
    Long fromOrganizationId = dto.getFromOrganizationId();
    if (fromOrganizationDisplayName != null && !fromOrganizationDisplayName.trim().isEmpty()) {
      var organization = organizationService.findByDisplayName(fromOrganizationDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "displayName",
              fromOrganizationDisplayName));
      organizationTransition.setFromOrganization(organization);
    } else if (fromOrganizationId != null) {
      var organization = organizationService.getById(fromOrganizationId)
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", fromOrganizationId));
      organizationTransition.setFromOrganization(organization);
    }

    // Map toOrganization from display name or ID
    String toOrganizationDisplayName = dto.getToOrganizationDisplayName();
    Long toOrganizationId = dto.getToOrganizationId();
    if (toOrganizationDisplayName != null && !toOrganizationDisplayName.trim().isEmpty()) {
      var organization = organizationService.findByDisplayName(toOrganizationDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "displayName",
              toOrganizationDisplayName));
      organizationTransition.setToOrganization(organization);
    } else if (toOrganizationId != null) {
      var organization = organizationService.getById(toOrganizationId)
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", toOrganizationId));
      organizationTransition.setToOrganization(organization);
    }

  }

}
