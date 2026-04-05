package com.eagle.futbolapi.features.competitionoutcome.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.competitionoutcome.dto.CompetitionOutcomeDto;
import com.eagle.futbolapi.features.competitionoutcome.entity.CompetitionOutcome;
import com.eagle.futbolapi.features.competitionoutcome.mapper.CompetitionOutcomeMapper;
import com.eagle.futbolapi.features.competitionoutcome.repository.CompetitionOutcomeRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
@Validated
public class CompetitionOutcomeService extends BaseCrudService<CompetitionOutcome, Long, CompetitionOutcomeDto> {

  private final CompetitionService competitionService;
  private final TeamService teamService;

  public CompetitionOutcomeService(CompetitionOutcomeRepository repository,
      CompetitionOutcomeMapper mapper, CompetitionService competitionService, TeamService teamService) {
    super(repository, mapper);
    this.competitionService = competitionService;
    this.teamService = teamService;
  }

  @Override
  protected void resolveRelationships(CompetitionOutcomeDto dto, CompetitionOutcome competitionOutcome) {
    // Resolve Competition by ID or display name using CompetitionService
    String competitionDisplayName = dto.getCompetitionDisplayName();
    Long competitionId = dto.getCompetitionId();
    if(competitionDisplayName != null && !competitionDisplayName.trim().isEmpty()) {
      var competition = competitionService.findByDisplayName(competitionDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "displayName", competitionDisplayName));
      competitionOutcome.setCompetition(competition);
    } else if (competitionId != null) {
      var competition = competitionService.getById(competitionId)
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "id", competitionId));
      competitionOutcome.setCompetition(competition);
    }

    // Resolve Team by ID or display name using TeamService
    String teamDisplayName = dto.getTeamDisplayName();
    Long teamId = dto.getTeamId();
    if (teamDisplayName != null && !teamDisplayName.trim().isEmpty()) {
      var team = teamService.findByDisplayName(teamDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", teamDisplayName));
      competitionOutcome.setTeam(team);
    } else if (teamId != null) {
      var team = teamService.getById(teamId)
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", teamId));
      competitionOutcome.setTeam(team);
    }

  }

  @Override
  protected boolean isDuplicate(@NotNull CompetitionOutcome competitionOutcome) {
    Objects.requireNonNull(competitionOutcome, "CompetitionOutcome entity must not be null");
    return isDuplicate(competitionOutcome, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull CompetitionOutcome entity) {
    Objects.requireNonNull(id, "ID must not be null");
    Objects.requireNonNull(entity, "CompetitionOutcome entity must not be null");
    return isDuplicate(id, entity, UniquenessStrategy.ALL);
  }

}
