package com.eagle.futbolapi.features.lineup.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.lineup.dto.LineupDto;
import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.mapper.LineupMapper;
import com.eagle.futbolapi.features.lineup.repository.LineupRepository;
import com.eagle.futbolapi.features.match.service.MatchService;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
@Validated
public class LineupService extends BaseCrudService<Lineup, Long, LineupDto> {

  private final MatchService matchService;
  private final TeamService teamService;

  public LineupService(LineupRepository lineupRepository, MatchService matchService,
      TeamService teamService, LineupMapper mapper) {
    super(lineupRepository, mapper);
    this.matchService = matchService;
    this.teamService = teamService;
  }

  @Override
  protected void resolveRelationships(LineupDto dto, Lineup lineup) {
    // Map match from ID or display name
    if (dto.getMatchId() != null) {
      var match = matchService.getById(dto.getMatchId())
          .orElseThrow(() -> new ResourceNotFoundException("Match", "id", dto.getMatchId()));
      lineup.setMatch(match);
    } else if (dto.getMatchDisplayName() != null && !dto.getMatchDisplayName().trim().isEmpty()) {
      var match = matchService.getMatchByDisplayName(dto.getMatchDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Match", "displayName", dto.getMatchDisplayName()));
      lineup.setMatch(match);
    }

    // Map team from ID or display name
    if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      lineup.setTeam(team);
    } else if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.findByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      lineup.setTeam(team);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Lineup lineup) {
    Objects.requireNonNull(lineup, "Lineup cannot be null");

    // Check composite unique constraint: match + team
    if (lineup.getMatch() != null && lineup.getTeam() != null) {
      return existsByUniqueFields(Map.of(
          "match.id", lineup.getMatch().getId(),
          "team.id", lineup.getTeam().getId()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Lineup lineup) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(lineup, "Lineup cannot be null");

    // Check composite unique constraint: match + team (excluding current ID)
    if (lineup.getMatch() != null && lineup.getTeam() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "match.id", lineup.getMatch().getId(),
          "team.id", lineup.getTeam().getId()), id);
    }
    return false;
  }

  public Optional<Lineup> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Lineup display name cannot be null or empty");
    }
    return ((LineupRepository) repository).findByDisplayName(displayName);
  }

}
