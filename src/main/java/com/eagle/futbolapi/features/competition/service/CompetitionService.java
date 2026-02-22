package com.eagle.futbolapi.features.competition.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.dto.CompetitionDto;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.mapper.CompetitionMapper;
import com.eagle.futbolapi.features.competition.repository.CompetitionRepository;
import com.eagle.futbolapi.features.tournamentSeason.service.TournamentSeasonService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Competition entity business logic.
 */
@Slf4j
@Service
@Transactional
public class CompetitionService extends BaseCrudService<Competition, Long, CompetitionDto> {

  private final TournamentSeasonService tournamentSeasonService;

  protected CompetitionService(CompetitionRepository repository, CompetitionMapper mapper,
      TournamentSeasonService tournamentSeasonService) {
    super(repository, mapper);
    this.tournamentSeasonService = tournamentSeasonService;
  }

  public Optional<Competition> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Competition display name cannot be null or empty");
    }
    return ((CompetitionRepository) repository).findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull Competition competition) {
    Objects.requireNonNull(competition, "Competition cannot be null");
    return isDuplicate(competition, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Competition competition) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(competition, "Competition cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, displayName={}, tournamentSeasonId={}, type={}",
        id, competition.getDisplayName(),
        competition.getTournamentSeason() != null ? competition.getTournamentSeason().getId() : null,
        competition.getType());

    boolean result = isDuplicate(id, competition, UniquenessStrategy.ALL);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(CompetitionDto dto, Competition competition) {
    // Map tournament season from display name or ID
    if (dto.getTournamentSeasonDisplayName() != null && !dto.getTournamentSeasonDisplayName().trim().isEmpty()) {
      var tournamentSeason = tournamentSeasonService.findByDisplayName(dto.getTournamentSeasonDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("TournamentSeason", "displayName",
              dto.getTournamentSeasonDisplayName()));
      competition.setTournamentSeason(tournamentSeason);
    } else if (dto.getTournamentSeasonId() != null) {
      var tournamentSeason = tournamentSeasonService.getById(dto.getTournamentSeasonId())
          .orElseThrow(() -> new ResourceNotFoundException("TournamentSeason", "id", dto.getTournamentSeasonId()));
      competition.setTournamentSeason(tournamentSeason);
    }
  }
}
