package com.eagle.futbolapi.features.qualificationoutcome.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.qualificationoutcome.dto.QualificationOutcomeDto;
import com.eagle.futbolapi.features.qualificationoutcome.entity.QualificationOutcome;
import com.eagle.futbolapi.features.qualificationoutcome.mapper.QualificationOutcomeMapper;
import com.eagle.futbolapi.features.qualificationoutcome.repository.QualificationOutcomeRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
@Validated
public class QualificationOutcomeService
    extends BaseCrudService<QualificationOutcome, Long, QualificationOutcomeDto> {

  private final CompetitionService competitionService;
  private final TeamService teamService;

  public QualificationOutcomeService(QualificationOutcomeRepository repository,
      QualificationOutcomeMapper mapper, CompetitionService competitionService, TeamService teamService) {
    super(repository, mapper);
    this.competitionService = competitionService;
    this.teamService = teamService;
  }

  @Override
  protected void resolveRelationships(QualificationOutcomeDto dto, QualificationOutcome entity) {
    if (dto.getSourceCompetitionId() != null) {
      entity.setSourceCompetition(competitionService.getById(dto.getSourceCompetitionId())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "id", dto.getSourceCompetitionId())));
    } else if (dto.getSourceCompetitionDisplayName() != null && !dto.getSourceCompetitionDisplayName().trim().isEmpty()) {
      entity.setSourceCompetition(competitionService.findByDisplayName(dto.getSourceCompetitionDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "displayName", dto.getSourceCompetitionDisplayName())));
    }

    if (dto.getTargetCompetitionId() != null) {
      entity.setTargetCompetition(competitionService.getById(dto.getTargetCompetitionId())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "id", dto.getTargetCompetitionId())));
    } else if (dto.getTargetCompetitionDisplayName() != null && !dto.getTargetCompetitionDisplayName().trim().isEmpty()) {
      entity.setTargetCompetition(competitionService.findByDisplayName(dto.getTargetCompetitionDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "displayName", dto.getTargetCompetitionDisplayName())));
    }

    if (dto.getTeamId() != null) {
      entity.setTeam(teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId())));
    } else if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      entity.setTeam(teamService.findByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName())));
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull QualificationOutcome entity) {
    Objects.requireNonNull(entity, "QualificationOutcome cannot be null");
    return isDuplicate(entity, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull QualificationOutcome entity) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "QualificationOutcome cannot be null");
    return isDuplicate(id, entity, UniquenessStrategy.ALL);
  }

}
