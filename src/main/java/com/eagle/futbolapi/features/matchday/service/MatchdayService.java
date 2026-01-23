package com.eagle.futbolapi.features.matchday.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.matchday.dto.MatchdayDTO;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.mapper.MatchdayMapper;
import com.eagle.futbolapi.features.matchday.repository.MatchdayRepository;
import com.eagle.futbolapi.features.stage.service.StageService;

@Service
@Transactional
public class MatchdayService extends BaseCrudService<Matchday, Long, MatchdayDTO> {

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

  @Override
  protected void resolveRelationships(MatchdayDTO dto, Matchday matchday) {
    if (dto.getStageDisplayName() != null && !dto.getStageDisplayName().trim().isEmpty()) {
      var stage = stageService.getStageByDisplayName(dto.getStageDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Stage", "displayName", dto.getStageDisplayName()));
      matchday.setStage(stage);
    } else if (dto.getStageId() != null) {
      var stage = stageService.getById(dto.getStageId())
          .orElseThrow(() -> new ResourceNotFoundException("Stage", "id", dto.getStageId()));
      matchday.setStage(stage);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Matchday entity) {
    Objects.requireNonNull(entity, "Matchday cannot be null");
    return repository.existsByUniqueValues(entity.getName(), entity.getStage().getId());
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Matchday entity) {
    Objects.requireNonNull(entity, "Matchday cannot be null");
    Objects.requireNonNull(id, "Matchday ID cannot be null");

    return repository.existsByUniqueValuesAndIdNot(entity.getName(), entity.getStage().getId(), id);
  }

}
