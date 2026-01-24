package com.eagle.futbolapi.features.matchevent.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.EventType;
import com.eagle.futbolapi.features.base.enums.Period;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.match.service.MatchService;
import com.eagle.futbolapi.features.matchevent.dto.MatchEventDTO;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.matchevent.mapper.MatchEventMapper;
import com.eagle.futbolapi.features.matchevent.repository.MatchEventRepository;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class MatchEventService extends BaseCrudService<MatchEvent, Long, MatchEventDTO> {

  private final MatchEventRepository matchEventRepository;
  private final MatchService matchService;
  private final TeamService teamService;
  private final PlayerService playerService;

  public MatchEventService(MatchEventRepository matchEventRepository, MatchService matchService,
      TeamService teamService, PlayerService playerService, MatchEventMapper mapper) {
    super(matchEventRepository, mapper);
    this.matchEventRepository = matchEventRepository;
    this.matchService = matchService;
    this.teamService = teamService;
    this.playerService = playerService;
  }

  @Override
  protected void resolveRelationships(MatchEventDTO dto, MatchEvent matchEvent) {
    // Map match from ID
    if (dto.getMatchId() != null) {
      var match = matchService.getById(dto.getMatchId())
          .orElseThrow(() -> new ResourceNotFoundException("Match", "id", dto.getMatchId()));
      matchEvent.setMatch(match);
    }

    // Map team from display name or ID
    if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.getByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      matchEvent.setTeam(team);
    } else if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      matchEvent.setTeam(team);
    }

    // Map player from ID
    if (dto.getPlayerId() != null) {
      var player = playerService.getById(dto.getPlayerId())
          .orElseThrow(() -> new ResourceNotFoundException("Player", "id", dto.getPlayerId()));
      matchEvent.setPlayer(player);
    }

    // Map event type from string
    if (dto.getEventType() != null && !dto.getEventType().trim().isEmpty()) {
      matchEvent.setType(EventType.valueOf(dto.getEventType().toUpperCase()));
    }

    // Map period from string
    if (dto.getPeriod() != null && !dto.getPeriod().trim().isEmpty()) {
      matchEvent.setPeriod(Period.valueOf(dto.getPeriod().toUpperCase()));
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull MatchEvent matchEvent) {
    Objects.requireNonNull(matchEvent, "MatchEvent cannot be null");
    // A match event is unique by match, player, type, and minute
    return matchEvent.getMatch() != null && matchEvent.getPlayer() != null && matchEvent.getType() != null
        && matchEvent.getMinute() != null
        && matchEventRepository.existsByMatchIdAndPlayerIdAndTypeAndMinute(
            matchEvent.getMatch().getId(), matchEvent.getPlayer().getId(), matchEvent.getType(),
            matchEvent.getMinute());
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull MatchEvent matchEvent) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(matchEvent, "MatchEvent cannot be null");

    return matchEvent.getMatch() != null && matchEvent.getPlayer() != null && matchEvent.getType() != null
        && matchEvent.getMinute() != null
        && matchEventRepository.existsByMatchIdAndPlayerIdAndTypeAndMinuteAndIdNot(
            matchEvent.getMatch().getId(), matchEvent.getPlayer().getId(), matchEvent.getType(),
            matchEvent.getMinute(), id);
  }

}
