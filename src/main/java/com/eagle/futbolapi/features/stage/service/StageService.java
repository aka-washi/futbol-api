package com.eagle.futbolapi.features.stage.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.stage.dto.StageDto;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.mapper.StageMapper;
import com.eagle.futbolapi.features.stage.repository.StageRepository;
import com.eagle.futbolapi.features.stageFormat.service.StageFormatService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Stage entity business logic.
 */
@Slf4j
@Service
@Transactional
public class StageService extends BaseCrudService<Stage, Long, StageDto> {

  private final CompetitionService competitionService;
  private final StageFormatService stageFormatService;

  private final StageRepository stageRepository;

  protected StageService(StageRepository repository, StageMapper mapper,
      CompetitionService competitionService, StageFormatService stageFormatService) {
    super(repository, mapper);
    this.stageRepository = repository;
    this.competitionService = competitionService;
    this.stageFormatService = stageFormatService;
  }

  public Optional<Stage> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Stage display name cannot be null or empty");
    }
    return stageRepository.findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull Stage stage) {
    Objects.requireNonNull(stage, "Stage cannot be null");
    return isDuplicate(stage, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Stage stage) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(stage, "Stage cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, displayName={}, competitionId={}, order={}",
        id, stage.getDisplayName(),
        stage.getCompetition() != null ? stage.getCompetition().getId() : null,
        stage.getOrder());

    boolean result = isDuplicate(id, stage, UniquenessStrategy.ALL);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(StageDto dto, Stage stage) {
    // Map competition from display name or ID
    if (dto.getCompetitionDisplayName() != null && !dto.getCompetitionDisplayName().trim().isEmpty()) {
      var competition = competitionService.findByDisplayName(dto.getCompetitionDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "displayName",
              dto.getCompetitionDisplayName()));
      stage.setCompetition(competition);
    } else if (dto.getCompetitionId() != null) {
      var competition = competitionService.getById(dto.getCompetitionId())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "id", dto.getCompetitionId()));
      stage.setCompetition(competition);
    }

    // Map stage format from display name or ID (optional relationship)
    if (dto.getStageFormatDisplayName() != null && !dto.getStageFormatDisplayName().trim().isEmpty()) {
      var stageFormat = stageFormatService.findByDisplayName(dto.getStageFormatDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("StageFormat", "displayName",
              dto.getStageFormatDisplayName()));
      stage.setStageFormat(stageFormat);
    } else if (dto.getStageFormatId() != null) {
      var stageFormat = stageFormatService.getById(dto.getStageFormatId())
          .orElseThrow(() -> new ResourceNotFoundException("StageFormat", "id", dto.getStageFormatId()));
      stage.setStageFormat(stageFormat);
    }
  }
}
