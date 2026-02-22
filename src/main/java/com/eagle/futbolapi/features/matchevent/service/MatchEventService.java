package com.eagle.futbolapi.features.matchevent.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.MatchEventType;
import com.eagle.futbolapi.features.base.enums.MatchPeriod;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.match.service.MatchService;
import com.eagle.futbolapi.features.matchevent.dto.MatchEventDto;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.matchevent.mapper.MatchEventMapper;
import com.eagle.futbolapi.features.matchevent.repository.MatchEventRepository;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class MatchEventService extends BaseCrudService<MatchEvent, Long, MatchEventDto> {

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
  protected void resolveRelationships(MatchEventDto dto, MatchEvent matchEvent) {
    // Map match from ID
    if (dto.getMatchId() != null) {
      var match = matchService.getById(dto.getMatchId())
          .orElseThrow(() -> new ResourceNotFoundException("Match", "id", dto.getMatchId()));
      matchEvent.setMatch(match);
    }

    // Map team from display name or ID
    if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.findByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      matchEvent.setTeam(team);
    } else if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      matchEvent.setTeam(team);
    }

    // Map player from ID or display name
    if (dto.getPlayerId() != null) {
      var player = playerService.getById(dto.getPlayerId())
          .orElseThrow(() -> new ResourceNotFoundException("Player", "id", dto.getPlayerId()));
      matchEvent.setPlayer(player);
    } else if (dto.getPlayerDisplayName() != null && !dto.getPlayerDisplayName().trim().isEmpty()) {
      var player = playerService.findByPersonDisplayName(dto.getPlayerDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Player", "playerDisplayName", dto.getPlayerDisplayName()));
      matchEvent.setPlayer(player);
    }

    // Map assist player from ID or display name
    if (dto.getAssistPlayerId() != null) {
      var assistPlayer = playerService.getById(dto.getAssistPlayerId())
          .orElseThrow(() -> new ResourceNotFoundException("Assist Player", "id", dto.getAssistPlayerId()));
      matchEvent.setAssistPlayer(assistPlayer);
    } else if (dto.getAssistPlayerDisplayName() != null && !dto.getAssistPlayerDisplayName().trim().isEmpty()) {
      var assistPlayer = playerService.findByPersonDisplayName(dto.getAssistPlayerDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Assist Player", "playerDisplayName",
              dto.getAssistPlayerDisplayName()));
      matchEvent.setAssistPlayer(assistPlayer);
    }

    // Map substitute player from ID or display name
    if (dto.getSubstitutePlayerId() != null) {
      var substitutePlayer = playerService.getById(dto.getSubstitutePlayerId())
          .orElseThrow(() -> new ResourceNotFoundException("Substitute Player", "id", dto.getSubstitutePlayerId()));
      matchEvent.setSubstitutePlayer(substitutePlayer);
    } else if (dto.getSubstitutePlayerDisplayName() != null && !dto.getSubstitutePlayerDisplayName().trim().isEmpty()) {
      var substitutePlayer = playerService.findByPersonDisplayName(dto.getSubstitutePlayerDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Substitute Player", "playerDisplayName",
              dto.getSubstitutePlayerDisplayName()));
      matchEvent.setSubstitutePlayer(substitutePlayer);
    }

    // Map event type from string
    if (dto.getType() != null && !dto.getType().trim().isEmpty()) {
      matchEvent.setType(MatchEventType.valueOf(dto.getType().toUpperCase()));
    }

    // Map period from string
    if (dto.getPeriod() != null && !dto.getPeriod().trim().isEmpty()) {
      matchEvent.setPeriod(MatchPeriod.valueOf(dto.getPeriod().toUpperCase()));
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
