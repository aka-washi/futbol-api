package com.eagle.futbolapi.features.tournamentSeason.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.tournament.service.TournamentService;
import com.eagle.futbolapi.features.tournamentSeason.dto.TournamentSeasonDto;
import com.eagle.futbolapi.features.tournamentSeason.entity.TournamentSeason;
import com.eagle.futbolapi.features.tournamentSeason.mapper.TournamentSeasonMapper;
import com.eagle.futbolapi.features.tournamentSeason.repository.TournamentSeasonRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for TournamentSeason entity business logic.
 */
@Slf4j
@Service
@Transactional
public class TournamentSeasonService extends BaseCrudService<TournamentSeason, Long, TournamentSeasonDto> {

  private final SeasonService seasonService;
  private final TournamentService tournamentService;

  protected TournamentSeasonService(TournamentSeasonRepository repository, TournamentSeasonMapper mapper,
      SeasonService seasonService, TournamentService tournamentService) {
    super(repository, mapper);
    this.seasonService = seasonService;
    this.tournamentService = tournamentService;
  }

  public Optional<TournamentSeason> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("TournamentSeason display name cannot be null or empty");
    }
    return ((TournamentSeasonRepository) repository).findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull TournamentSeason tournamentSeason) {
    Objects.requireNonNull(tournamentSeason, "TournamentSeason cannot be null");
    return isDuplicate(tournamentSeason, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull TournamentSeason tournamentSeason) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(tournamentSeason, "TournamentSeason cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, seasonId={}, tournamentId={}",
        id,
        tournamentSeason.getSeason() != null ? tournamentSeason.getSeason().getId() : null,
        tournamentSeason.getTournament() != null ? tournamentSeason.getTournament().getId() : null);

    boolean result = isDuplicate(id, tournamentSeason, UniquenessStrategy.ALL);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(TournamentSeasonDto dto, TournamentSeason tournamentSeason) {
    // Map season from display name or ID
    if (dto.getSeasonDisplayName() != null && !dto.getSeasonDisplayName().trim().isEmpty()) {
      var season = seasonService.findByDisplayName(dto.getSeasonDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "displayName", dto.getSeasonDisplayName()));
      tournamentSeason.setSeason(season);
    } else if (dto.getSeasonId() != null) {
      var season = seasonService.getById(dto.getSeasonId())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "id", dto.getSeasonId()));
      tournamentSeason.setSeason(season);
    }

    // Map tournament from display name or ID
    if (dto.getTournamentDisplayName() != null && !dto.getTournamentDisplayName().trim().isEmpty()) {
      var tournament = tournamentService.findByDisplayName(dto.getTournamentDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Tournament", "displayName",
              dto.getTournamentDisplayName()));
      tournamentSeason.setTournament(tournament);
    } else if (dto.getTournamentId() != null) {
      var tournament = tournamentService.getById(dto.getTournamentId())
          .orElseThrow(() -> new ResourceNotFoundException("Tournament", "id", dto.getTournamentId()));
      tournamentSeason.setTournament(tournament);
    }
  }
}
