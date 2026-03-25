package com.eagle.futbolapi.features.matchday.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.matchday.dto.MatchdayDto;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.mapper.MatchdayMapper;
import com.eagle.futbolapi.features.matchday.repository.MatchdayRepository;
import com.eagle.futbolapi.features.stage.service.StageService;

@Service
@Transactional
@Validated
public class MatchdayService extends BaseCrudService<Matchday, Long, MatchdayDto> {

  private final MatchdayRepository repository;
  private final StageService stageService;

  protected MatchdayService(
      MatchdayRepository repository,
      StageService stageService,
      MatchdayMapper mapper) {
    super(repository, mapper);
    this.repository = repository;
    this.stageService = stageService;
  }

  public Optional<Matchday> getByName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Matchday name cannot be null or empty");
    }
    return repository.findByName(name);
  }

  public Optional<Matchday> getByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Matchday display name cannot be null or empty");
    }
    return repository.findByDisplayName(displayName);
  }

  @Override
  protected void resolveRelationships(MatchdayDto dto, Matchday matchday) {
    if (dto.getStageId() != null) {
      var stage = stageService.getById(dto.getStageId())
          .orElseThrow(() -> new ResourceNotFoundException("Stage", "id", dto.getStageId()));
      matchday.setStage(stage);
    } else if (dto.getStageDisplayName() != null && !dto.getStageDisplayName().trim().isEmpty()) {
      var stage = stageService.findByDisplayName(dto.getStageDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Stage", "displayName", dto.getStageDisplayName()));
      matchday.setStage(stage);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Matchday entity) {
    Objects.requireNonNull(entity, "Matchday cannot be null");

    // Check composite unique constraint: stage + number
    if (entity.getStage() != null && entity.getNumber() != null) {
      return existsByUniqueFields(Map.of(
          "stage.id", entity.getStage().getId(),
          "number", entity.getNumber()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Matchday entity) {
    Objects.requireNonNull(entity, "Matchday cannot be null");
    Objects.requireNonNull(id, "Matchday ID cannot be null");

    // Check composite unique constraint: stage + number (excluding current ID)
    if (entity.getStage() != null && entity.getNumber() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "stage.id", entity.getStage().getId(),
          "number", entity.getNumber()), id);
    }
    return false;
  }

}
