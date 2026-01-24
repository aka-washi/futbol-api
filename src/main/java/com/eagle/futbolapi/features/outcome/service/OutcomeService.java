package com.eagle.futbolapi.features.outcome.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.outcome.dto.OutcomeDTO;
import com.eagle.futbolapi.features.outcome.entity.Outcome;
import com.eagle.futbolapi.features.outcome.mapper.OutcomeMapper;
import com.eagle.futbolapi.features.outcome.repository.OutcomeRepository;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.staff.service.StaffService;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class OutcomeService extends BaseCrudService<Outcome, Long, OutcomeDTO> {

  private final OutcomeRepository outcomeRepository;
  private final CompetitionService competitionService;
  private final TeamService teamService;
  private final PlayerService playerService;
  private final StaffService staffService;

  public OutcomeService(OutcomeRepository outcomeRepository, CompetitionService competitionService,
      TeamService teamService, PlayerService playerService, StaffService staffService, OutcomeMapper mapper) {
    super(outcomeRepository, mapper);
    this.outcomeRepository = outcomeRepository;
    this.competitionService = competitionService;
    this.teamService = teamService;
    this.playerService = playerService;
    this.staffService = staffService;
  }

  @Override
  protected void resolveRelationships(OutcomeDTO dto, Outcome outcome) {
    // Map competition from ID
    if (dto.getCompetitionId() != null) {
      var competition = competitionService.getById(dto.getCompetitionId())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "id", dto.getCompetitionId()));
      outcome.setCompetition(competition);
    }

    // Map team from display name or ID
    if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.getByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      outcome.setTeam(team);
    } else if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      outcome.setTeam(team);
    }

    // Map player from ID (optional)
    if (dto.getPlayerId() != null) {
      var player = playerService.getById(dto.getPlayerId())
          .orElseThrow(() -> new ResourceNotFoundException("Player", "id", dto.getPlayerId()));
      outcome.setPlayer(player);
    }

    // Map staff from ID (optional)
    if (dto.getStaffId() != null) {
      var staff = staffService.getById(dto.getStaffId())
          .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", dto.getStaffId()));
      outcome.setStaff(staff);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Outcome outcome) {
    Objects.requireNonNull(outcome, "Outcome cannot be null");
    // An outcome is unique by competition, team, type, and optionally player/staff
    return false; // Outcomes can have duplicates based on business rules
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Outcome outcome) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(outcome, "Outcome cannot be null");

    return false; // Outcomes can have duplicates based on business rules
  }

}
