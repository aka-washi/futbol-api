package com.eagle.futbolapi.features.season.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.season.dto.SeasonDTO;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.mapper.SeasonMapper;
import com.eagle.futbolapi.features.season.repository.SeasonRepository;
import com.eagle.futbolapi.features.tournament.service.TournamentService;

@Service
@Transactional
public class SeasonService extends BaseCrudService<Season, Long, SeasonDTO> {

  private final SeasonRepository seasonRepository;
  private final TournamentService tournamentService;
  private final SeasonMapper seasonMapper;

  public SeasonService(SeasonRepository seasonRepository, TournamentService tournamentService,
      SeasonMapper seasonMapper) {
    super(seasonRepository);
    this.seasonRepository = seasonRepository;
    this.tournamentService = tournamentService;
    this.seasonMapper = seasonMapper;
  }

  public Optional<Season> getSeasonByName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Season name cannot be null or empty");
    }
    return seasonRepository.findByName(name);
  }

  public Optional<Season> getSeasonByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Season display name cannot be null or empty");
    }
    return seasonRepository.findByDisplayName(displayName);
  }

  public Optional<Season> getSeasonByTournamentAndActive(Long tournamentId, Boolean active) {
    if (tournamentId == null) {
      throw new IllegalArgumentException("Tournament ID cannot be null");
    }
    if (active == null) {
      throw new IllegalArgumentException("Active status cannot be null");
    }
    return seasonRepository.findByTournamentIdAndActive(tournamentId, active);
  }

  public Optional<Season> getSeasonByTournamentAndDateRange(Long tournamentId, LocalDate date) {
    if (tournamentId == null) {
      throw new IllegalArgumentException("Tournament ID cannot be null");
    }
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    return seasonRepository.findByTournamentAndDateRange(tournamentId, date);
  }

  public Optional<Season> getSeasonByUniqueValues(
      Long tournamentId,
      String name,
      LocalDate startDate,
      LocalDate endDate,
      Boolean active) {
    return seasonRepository.findByUniqueValues(
        tournamentService.getById(tournamentId)
            .orElseThrow(() -> new ResourceNotFoundException("Tournament", "id", tournamentId)),
        name,
        startDate,
        endDate,
        active);
  }

  Page<Season> getSeasonsByTournamentId(Long tournamentId) {
    if (tournamentId == null) {
      throw new IllegalArgumentException("Tournament ID cannot be null");
    }
    return seasonRepository.findByTournamentId(tournamentId);
  }

  Page<Season> getSeasonsByDateRange(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    return seasonRepository.findByDateRange(date);
  }

  @Override
  protected Season convertToEntity(SeasonDTO dto) {
    return seasonMapper.toSeason(dto);
  }

  // Resolve related entities (Tournament) from DTO
  @Override
  protected void resolveRelationships(@NotNull SeasonDTO dto, @NotNull Season season) {
    // Map tournament from ID
    if (dto.getTournamentDisplayName() != null && !dto.getTournamentDisplayName().trim().isEmpty()) {
      var tournament = tournamentService.getTournamentByDisplayName(dto.getTournamentDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Tournament with display name '" + dto.getTournamentDisplayName() + "' not found"));
      season.setTournament(tournament);
    } else if (dto.getTournamentId() != null) {
      var tournament = tournamentService.getById(dto.getTournamentId())
          .orElseThrow(
              () -> new ResourceNotFoundException("Tournament with ID '" + dto.getTournamentId() + "' not found"));
      season.setTournament(tournament);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Season season) {
    Objects.requireNonNull(season, "Season cannot be null");

    return seasonRepository.existsByUniqueValues(
        season.getName(),
        season.getTournament(),
        season.getStartDate(),
        season.getEndDate(),
        season.getActive());
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Season season) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(season, "Season cannot be null");

    return seasonRepository.existsByUniqueValuesAndIdNot(
        season.getName(),
        season.getTournament(),
        season.getStartDate(),
        season.getEndDate(),
        season.getActive(),
        id);
  }
}
