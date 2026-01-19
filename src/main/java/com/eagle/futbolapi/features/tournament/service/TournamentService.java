package com.eagle.futbolapi.features.tournament.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.NoChangesDetectedException;
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
  private final TournamentMapper tournamentMapper;

  public TournamentService(TournamentRepository tournamentRepository, OrganizationService organizationService,
      TournamentMapper tournamentMapper) {
    super(tournamentRepository);
    this.tournamentRepository = tournamentRepository;
    this.organizationService = organizationService;
    this.tournamentMapper = tournamentMapper;
  }

  public TournamentDTO getTournamentByName(String name) {
    Tournament tournament = tournamentRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("Tournament with given name does not exist"));
    return tournamentMapper.toTournamentDTO(tournament);
  }

  public TournamentDTO getTournamentByDisplayName(String displayName) {
    Tournament tournament = tournamentRepository.findByDisplayName(displayName)
        .orElseThrow(() -> new IllegalArgumentException("Tournament with given display name does not exist"));
    return tournamentMapper.toTournamentDTO(tournament);
  }

  public TournamentDTO getTournamentByUniqueValues(Long organizationId, String type, String ageCategory,
      Integer level) {
    Tournament tournament = tournamentRepository.findByUniqueValues(
        null, // Fetch organization entity by ID if needed
        null, // Convert type string to TournamentType enum if needed
        ageCategory,
        level).orElseThrow(() -> new IllegalArgumentException("Tournament with given unique values does not exist"));
    return tournamentMapper.toTournamentDTO(tournament);
  }

  public Page<TournamentDTO> getTournamentsByOrganizationAndActive(Long organizationId, Boolean active,
      Pageable pageable) {
    Page<Tournament> tournaments = tournamentRepository.findByOrganizationAndActive(
        null, // Fetch organization entity by ID if needed
        active,
        pageable);
    return tournaments.map(tournamentMapper::toTournamentDTO);
  }

  public Page<TournamentDTO> getTournamentsByTypeAndActive(String type, Boolean active, Pageable pageable) {
    Page<Tournament> tournaments = tournamentRepository.findByTypeAndActive(
        null, // Convert type string to TournamentType enum if needed
        active,
        pageable);
    return tournaments.map(tournamentMapper::toTournamentDTO);
  }

  @Override
  protected Tournament convertToEntity(TournamentDTO dto) {
    return tournamentMapper.toTournament(dto);
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
  public Tournament update(@NotNull Long id, @NotNull TournamentDTO dto) {
    // Get existing entity to preserve audit fields
    Tournament existing = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Entity with given ID does not exist"));

    // Convert DTO to entity and resolve relationships
    Tournament tournament = convertToEntity(dto);
    resolveRelationships(dto, tournament);

    // Preserve audit fields from existing entity
    tournament.setId(id);
    tournament.setCreatedAt(existing.getCreatedAt());
    tournament.setCreatedBy(existing.getCreatedBy());

    // Validate and save
    if (existing.equals(tournament)) {
      throw new NoChangesDetectedException("No changes detected for entity", id);
    }
    if (isDuplicate(id, tournament)) {
      throw new IllegalArgumentException("Duplicate entity");
    }
    return repository.save(tournament);
  }

  @Override
  protected boolean isDuplicate(@NotNull Tournament tournament) {
    Objects.requireNonNull(tournament, "Tournament cannot be null");

    return tournamentRepository.existsByUniqueValues(
        tournament.getOrganization(),
        tournament.getType(),
        tournament.getAgeCategory(),
        tournament.getLevel());
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Tournament tournament) {
    Objects.requireNonNull(tournament, "Tournament cannot be null");

    return tournamentRepository.existsByUniqueValuesAndIdNot(
        tournament.getOrganization(),
        tournament.getType(),
        tournament.getAgeCategory(),
        tournament.getLevel(),
        id);
  }

}
