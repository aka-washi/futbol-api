package com.eagle.futbolapi.features.standing.service;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.stage.repository.StageRepository;
import com.eagle.futbolapi.features.standing.dto.StandingDto;
import com.eagle.futbolapi.features.standing.entity.Standing;
import com.eagle.futbolapi.features.standing.mapper.StandingMapper;
import com.eagle.futbolapi.features.standing.repository.StandingRepository;
import com.eagle.futbolapi.features.team.repository.TeamRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
@Validated
public class StandingService extends BaseCrudService<Standing, Long, StandingDto> {

  private final StandingRepository standingRepository;
  private final StageRepository stageRepository;
  private final TeamRepository teamRepository;
  private final TeamService teamService;

  public StandingService(
      StandingRepository standingRepository,
      StageRepository stageRepository,
      TeamRepository teamRepository,
      TeamService teamService,
      StandingMapper mapper) {
    super(standingRepository, mapper);
    this.standingRepository = standingRepository;
    this.stageRepository = stageRepository;
    this.teamRepository = teamRepository;
    this.teamService = teamService;
  }

  public Page<Standing> getByStageIdOrderByPosition(Long stageId, Pageable pageable) {
    if (stageId == null) {
      throw new IllegalArgumentException("Stage ID cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return standingRepository.findByStageIdOrderByPositionAsc(stageId, pageable);
  }

  public Page<Standing> getByStageIdOrderByPointsDesc(Long stageId, Pageable pageable) {
    if (stageId == null) {
      throw new IllegalArgumentException("Stage ID cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return standingRepository.findByStageIdOrderByPointsDesc(stageId, pageable);
  }

  @Override
  protected void resolveRelationships(StandingDto dto, Standing standing) {
    // Map stage from ID
    if (dto.getStageId() != null) {
      var stage = stageRepository.findById(dto.getStageId())
          .orElseThrow(() -> new ResourceNotFoundException("Stage", "id", dto.getStageId()));
      standing.setStage(stage);
    }

    // Map team from ID (preferred) or display name
    if (dto.getTeamId() != null) {
      var team = teamRepository.findById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      standing.setTeam(team);
    } else if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.findByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      standing.setTeam(team);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Standing standing) {
    Objects.requireNonNull(standing, "Standing cannot be null");

    // Check composite unique constraint: stage + team
    if (standing.getStage() != null && standing.getTeam() != null) {
      return existsByUniqueFields(Map.of(
          "stage.id", standing.getStage().getId(),
          "team.id", standing.getTeam().getId()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Standing standing) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(standing, "Standing cannot be null");

    // Check composite unique constraint: stage + team (excluding current ID)
    if (standing.getStage() != null && standing.getTeam() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "stage.id", standing.getStage().getId(),
          "team.id", standing.getTeam().getId()), id);
    }
    return false;
  }

}
