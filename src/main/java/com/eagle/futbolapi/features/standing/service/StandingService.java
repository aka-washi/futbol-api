package com.eagle.futbolapi.features.standing.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.stage.service.StageService;
import com.eagle.futbolapi.features.standing.dto.StandingDTO;
import com.eagle.futbolapi.features.standing.entity.Standing;
import com.eagle.futbolapi.features.standing.mapper.StandingMapper;
import com.eagle.futbolapi.features.standing.repository.StandingRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class StandingService extends BaseCrudService<Standing, Long, StandingDTO> {

  private final StandingRepository standingRepository;
  private final StageService stageService;
  private final TeamService teamService;

  public StandingService(StandingRepository standingRepository, StageService stageService,
      TeamService teamService, StandingMapper mapper) {
    super(standingRepository, mapper);
    this.standingRepository = standingRepository;
    this.stageService = stageService;
    this.teamService = teamService;
  }

  public Page<Standing> getByStageIdOrderByPosition(Long stageId, Pageable pageable) {
    if (stageId == null) {
      throw new IllegalArgumentException("Stage ID cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return standingRepository.findByStageIdOrderByPosition(stageId, pageable);
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
  protected void resolveRelationships(StandingDTO dto, Standing standing) {
    // Map stage from ID
    if (dto.getStageId() != null) {
      var stage = stageService.getById(dto.getStageId())
          .orElseThrow(() -> new ResourceNotFoundException("Stage", "id", dto.getStageId()));
      standing.setStage(stage);
    }

    // Map team from display name or ID
    if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.getByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      standing.setTeam(team);
    } else if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      standing.setTeam(team);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Standing standing) {
    Objects.requireNonNull(standing, "Standing cannot be null");
    // A standing is unique by stage and team
    return standing.getStage() != null && standing.getTeam() != null
        && standingRepository.existsByStageIdAndTeamId(standing.getStage().getId(), standing.getTeam().getId());
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Standing standing) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(standing, "Standing cannot be null");

    return standing.getStage() != null && standing.getTeam() != null
        && standingRepository.existsByStageIdAndTeamIdAndIdNot(
            standing.getStage().getId(), standing.getTeam().getId(), id);
  }

}
