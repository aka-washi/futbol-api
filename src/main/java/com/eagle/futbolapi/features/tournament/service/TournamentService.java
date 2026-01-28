package com.eagle.futbolapi.features.tournament.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.TournamentType;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.tournament.dto.TournamentDTO;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
import com.eagle.futbolapi.features.tournament.mapper.TournamentMapper;
import com.eagle.futbolapi.features.tournament.repository.TournamentRepository;

@Service
@Transactional
public class TournamentService extends BaseCrudService<Tournament, Long, TournamentDTO> {

  private final TournamentRepository tournamentRepository;
  private final OrganizationService organizationService;

  public TournamentService(TournamentRepository tournamentRepository, OrganizationService organizationService,
      TournamentMapper mapper) {
    super(tournamentRepository, mapper);
    this.tournamentRepository = tournamentRepository;
    this.organizationService = organizationService;
  }

  public Optional<Tournament> getTournamentByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Tournament name cannot be null or empty");
    }
    return tournamentRepository.findByName(name);
  }

  public Optional<Tournament> getTournamentByDisplayName(String displayName) {
    if (displayName == null || displayName.trim().isEmpty()) {
      throw new IllegalArgumentException("Tournament display name cannot be null or empty");
    }
    return tournamentRepository.findByDisplayName(displayName);
  }

  public Page<Tournament> getTournamentsByOrganizationAndActive(Long organizationId, Boolean active,
      Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return tournamentRepository.findByOrganizationIdAndActive(organizationId, active, pageable);
  }

  public Page<Tournament> getTournamentsByTypeAndActive(TournamentType type, Boolean active, Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    if (type == null) {
      throw new IllegalArgumentException("Tournament type cannot be null or empty");
    }
    return tournamentRepository.findByTypeAndActive(type, active, pageable);
  }

  // Relationships
  @Override
  public void resolveRelationships(@NotNull TournamentDTO dto, @NotNull Tournament tournament) {
    // Map organization from name or ID
    if (dto.getOrganizationDisplayName() != null && !dto.getOrganizationDisplayName().isEmpty()) {
      var organization = organizationService.getOrganizationByDisplayName(dto.getOrganizationDisplayName())
          .orElseThrow(
              () -> new ResourceNotFoundException("Organization", "displayName", dto.getOrganizationDisplayName()));
      tournament.setOrganization(organization);
    } else if (dto.getOrganizationId() != null) {
      var organization = organizationService.getById(dto.getOrganizationId())
          .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", dto.getOrganizationId()));
      tournament.setOrganization(organization);
    }

    // Map relegationTo tournament from name or ID
    if (dto.getRelegationToDisplayName() != null && !dto.getRelegationToDisplayName().isEmpty()) {
      var relegationTo = tournamentRepository.findByDisplayName(dto.getRelegationToDisplayName())
          .orElseThrow(
              () -> new ResourceNotFoundException("Tournament", "displayName", dto.getRelegationToDisplayName()));
      tournament.setRelegationTo(relegationTo);
    } else if (dto.getRelegationToId() != null) {
      var relegationTo = getById(dto.getRelegationToId())
          .orElseThrow(() -> new ResourceNotFoundException("Tournament", "id", dto.getRelegationToId()));
      tournament.setRelegationTo(relegationTo);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Tournament tournament) {
    Objects.requireNonNull(tournament, "Tournament cannot be null");

    // Check composite unique constraint: name + organization
    if (tournament.getName() != null && tournament.getOrganization() != null) {
      return existsByUniqueFields(Map.of(
          "name", tournament.getName(),
          "organization.id", tournament.getOrganization().getId()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Tournament tournament) {
    Objects.requireNonNull(tournament, "Tournament cannot be null");

    // Check composite unique constraint: name + organization (excluding current ID)
    if (tournament.getName() != null && tournament.getOrganization() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "name", tournament.getName(),
          "organization.id", tournament.getOrganization().getId()), id);
    }
    return false;
  }

}
