package com.eagle.futbolapi.features.lineup.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.lineup.dto.LineupDTO;
import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.mapper.LineupMapper;
import com.eagle.futbolapi.features.lineup.repository.LineupRepository;
import com.eagle.futbolapi.features.match.service.MatchService;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class LineupService extends BaseCrudService<Lineup, Long, LineupDTO> {

  private final LineupRepository lineupRepository;
  private final MatchService matchService;
  private final TeamService teamService;
  private final PlayerService playerService;

  public LineupService(LineupRepository lineupRepository, MatchService matchService,
      TeamService teamService, PlayerService playerService, LineupMapper mapper) {
    super(lineupRepository, mapper);
    this.lineupRepository = lineupRepository;
    this.matchService = matchService;
    this.teamService = teamService;
    this.playerService = playerService;
  }

  @Override
  protected void resolveRelationships(LineupDTO dto, Lineup lineup) {
    // Map match from ID
    if (dto.getMatchId() != null) {
      var match = matchService.getById(dto.getMatchId())
          .orElseThrow(() -> new ResourceNotFoundException("Match", "id", dto.getMatchId()));
      lineup.setMatch(match);
    }

    // Map team from display name or ID
    if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.getByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      lineup.setTeam(team);
    } else if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      lineup.setTeam(team);
    }

    // Map player from ID
    if (dto.getPlayerId() != null) {
      var player = playerService.getById(dto.getPlayerId())
          .orElseThrow(() -> new ResourceNotFoundException("Player", "id", dto.getPlayerId()));
      lineup.setPlayer(player);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Lineup lineup) {
    Objects.requireNonNull(lineup, "Lineup cannot be null");
    // A lineup entry is unique by match, team, and player
    return lineup.getMatch() != null && lineup.getTeam() != null && lineup.getPlayer() != null
        && lineupRepository.existsByMatchIdAndTeamIdAndPlayerId(
            lineup.getMatch().getId(), lineup.getTeam().getId(), lineup.getPlayer().getId());
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Lineup lineup) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(lineup, "Lineup cannot be null");

    return lineup.getMatch() != null && lineup.getTeam() != null && lineup.getPlayer() != null
        && lineupRepository.existsByMatchIdAndTeamIdAndPlayerIdAndIdNot(
            lineup.getMatch().getId(), lineup.getTeam().getId(), lineup.getPlayer().getId(), id);
  }

}
