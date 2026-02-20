package com.eagle.futbolapi.features.tournament.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.tournament.dto.TournamentDto;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
import com.eagle.futbolapi.features.tournament.mapper.TournamentMapper;
import com.eagle.futbolapi.features.tournament.repository.TournamentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TournamentService extends BaseCrudService<Tournament, Long, TournamentDto> {

  private final TournamentRepository repository;
  private final OrganizationService organizationService;

  protected TournamentService(TournamentRepository repository, TournamentMapper mapper,
      OrganizationService organizationService) {
    super(repository, mapper);
    this.repository = repository;
    this.organizationService = organizationService;
  }

  public Optional<Tournament> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Tournament display name cannot be null or empty");
    }
    return repository.findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull Tournament tournament) {
    Objects.requireNonNull(tournament, "Tournament must not be null");

    return isDuplicate(tournament, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Tournament tournament) {
    Objects.requireNonNull(id, "ID must not be null");
    Objects.requireNonNull(tournament, "Tournament must not be null");

    return isDuplicate(tournament, UniquenessStrategy.ALL);
  }

  @Override
  protected void resolveRelationships(TournamentDto dto, Tournament tournament) {
    // Map organization from display name or ID
    if (dto.getOrganizationDisplayName() != null && !dto.getOrganizationDisplayName().trim().isEmpty()) {
      var organization = organizationService.findByDisplayName(dto.getOrganizationDisplayName())
          .orElseThrow(
              () -> new ResourceNotFoundException("Organization", "displayName", dto.getOrganizationDisplayName()));
      tournament.setOrganization(organization);
    } else if (dto.getOrganizationId() != null) {
      var organization = organizationService.getById(dto.getOrganizationId())
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", dto.getOrganizationId()));
      tournament.setOrganization(organization);
    }

    // Map relegation to from display name or ID
    if (dto.getRelegationToDisplayName() != null && !dto.getRelegationToDisplayName().trim().isEmpty()) {
      var relegationTo = repository.findByDisplayName(dto.getRelegationToDisplayName())
          .orElseThrow(
              () -> new ResourceNotFoundException("Tournament", "displayName", dto.getRelegationToDisplayName()));
      tournament.setRelegationTo(relegationTo);
    } else if (dto.getRelegationToId() != null) {
      var relegationTo = repository.findById(dto.getRelegationToId())
          .orElseThrow(() -> new ResourceNotFoundException("Tournament", "id", dto.getRelegationToId()));
      tournament.setRelegationTo(relegationTo);
    }
  }

}
