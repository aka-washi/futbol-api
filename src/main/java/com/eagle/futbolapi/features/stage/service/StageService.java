package com.eagle.futbolapi.features.stage.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.StageStatus;
import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.stage.dto.StageDTO;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.mapper.StageMapper;
import com.eagle.futbolapi.features.stage.repository.StageRepository;
import com.eagle.futbolapi.features.structure.service.StructureService;

@Service
@Transactional
public class StageService extends BaseCrudService<Stage, Long, StageDTO> {

  private final StageRepository stageRepository;
  private final CompetitionService competitionService;
  private final StructureService structureService;

  public StageService(StageRepository stageRepository, CompetitionService competitionService,
      StructureService structureService, StageMapper mapper) {
    super(stageRepository, mapper);
    this.stageRepository = stageRepository;
    this.competitionService = competitionService;
    this.structureService = structureService;
  }

  public Optional<Stage> getStageByName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Stage name cannot be null or empty");
    }
    return stageRepository.findByName(name);
  }

  public Optional<Stage> getStageByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Stage display name cannot be null or empty");
    }
    return stageRepository.findByDisplayName(displayName);
  }

  public Optional<Stage> getStageByCompetitionId(Long competitionId) {
    if (competitionId == null) {
      throw new IllegalArgumentException("Competition ID cannot be null");
    }
    return stageRepository.findByCompetitionId(competitionId);
  }

  public Optional<Stage> getStageByCompetitionAndDateRange(Long competitionId) {
    if (competitionId == null) {
      throw new IllegalArgumentException("Competition ID cannot be null");
    }
    return stageRepository.findByStagesByCompetitionIdAndDateRange(competitionId, java.time.LocalDate.now());
  }

  public Page<Stage> getStagesByDateRange(LocalDate date, Pageable pageable) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return stageRepository.findByDateRange(date, pageable);
  }

  public Page<Stage> getStagesByCompetitionAndStatus(Long competitionId, StageStatus status, Pageable pageable) {
    if (competitionId == null) {
      throw new IllegalArgumentException("Competition ID cannot be null");
    }
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return stageRepository.findByCompetitionIdAndStatus(competitionId, status, pageable);
  }

  @Override
  protected void resolveRelationships(@NotNull StageDTO dto, @NotNull Stage stage) {
    // Map Competition
    if (dto.getCompetitionDisplayName() != null && !dto.getCompetitionDisplayName().trim().isEmpty()) {
      var competition = competitionService.getCompetitionByDisplayName(dto.getCompetitionDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Competition with display name '" + dto.getCompetitionDisplayName() + "' not found"));
      stage.setCompetition(competition);
    } else if (dto.getCompetitionId() != null) {
      var competition = competitionService.getById(dto.getCompetitionId())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Competition with ID '" + dto.getCompetitionId() + "' not found"));
      stage.setCompetition(competition);
    }

    // Map Structure
    if (dto.getStructureDisplayName() != null && !dto.getStructureDisplayName().trim().isEmpty()) {
      var structure = structureService.getStructureByDisplayName(dto.getStructureDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Structure with display name '" + dto.getStructureDisplayName() + "' not found"));
      stage.setStructure(structure);
    } else if (dto.getStructureId() != null) {
      var structure = structureService.getById(dto.getStructureId())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Structure with ID '" + dto.getStructureId() + "' not found"));
      stage.setStructure(structure);
    }
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

    return isDuplicate(id, stage, UniquenessStrategy.ALL);
  }

}
