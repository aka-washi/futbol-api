package com.eagle.futbolapi.features.competition.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.dto.CompetitionDTO;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.mapper.CompetitionMapper;
import com.eagle.futbolapi.features.competition.repository.CompetitionRepository;
import com.eagle.futbolapi.features.season.service.SeasonService;

@Service
@Transactional
public class CompetitionService extends BaseCrudService<Competition, Long, CompetitionDTO> {

  private final CompetitionRepository competitionRepository;
  private final SeasonService seasonService;

  protected CompetitionService(CompetitionRepository competitionRepository, SeasonService seasonService,
      CompetitionMapper mapper) {
    super(competitionRepository, mapper);
    this.competitionRepository = competitionRepository;
    this.seasonService = seasonService;
  }

  public Optional<Competition> getCompetitionByName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Competition name cannot be null or empty");
    }
    return competitionRepository.findByName(name);
  }

  public Optional<Competition> getCompetitionByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Competition display name cannot be null or empty");
    }
    return competitionRepository.findByDisplayName(displayName);
  }

  public Optional<Competition> getCompetitionBySeasonIdAndActive(Long seasonId, Boolean active) {
    if (seasonId == null) {
      throw new IllegalArgumentException("Season ID cannot be null");
    }
    if (active == null) {
      throw new IllegalArgumentException("Competition active status cannot be null");
    }
    return competitionRepository.findBySeasonIdAndActive(seasonId, active);
  }

  public Optional<Competition> getByTypeAndDate(CompetitionType type, LocalDate date) {
    if (type == null) {
      throw new IllegalArgumentException("Competition type cannot be null");
    }
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    return competitionRepository.findByTypeAndDate(type, date);
  }

  public Page<Competition> getActiveCompetitions(Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return competitionRepository.findActiveCompetitions(pageable);
  }

  public Page<Competition> getCompetitionsBySeasonId(Long seasonId, Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    if (seasonId == null) {
      throw new IllegalArgumentException("Season ID cannot be null");
    }
    return competitionRepository.findBySeasonId(seasonId, pageable);
  }

  public Page<Competition> getCompetitionsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    if (startDate == null) {
      throw new IllegalArgumentException("Start date cannot be null");
    }
    if (endDate == null) {
      throw new IllegalArgumentException("End date cannot be null");
    }
    return competitionRepository.findByDateRange(startDate, endDate, pageable);
  }

  @Override
  protected void resolveRelationships(@NotNull CompetitionDTO dto, @NotNull Competition competition) {
    // Map season from display name or ID
    if (dto.getSeasonDisplayName() != null && !dto.getSeasonDisplayName().trim().isEmpty()) {
      var season = seasonService.getSeasonByDisplayName(dto.getSeasonDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "displayName", dto.getSeasonDisplayName()));
      competition.setSeason(season);
    } else if (dto.getSeasonId() != null) {
      var season = seasonService.getById(dto.getSeasonId())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "id", dto.getSeasonId()));
      competition.setSeason(season);
    }
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

    return isDuplicate(id, competition, UniquenessStrategy.ALL);
  }
}
