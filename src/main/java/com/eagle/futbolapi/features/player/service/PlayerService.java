package com.eagle.futbolapi.features.player.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.player.dto.PlayerDTO;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.mapper.PlayerMapper;
import com.eagle.futbolapi.features.player.repository.PlayerRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class PlayerService extends BaseCrudService<Player, Long, PlayerDTO> {

  private final PlayerRepository playerRepository;
  private final PersonService personService;
  private final TeamService teamService;

  public PlayerService(PlayerRepository playerRepository, PersonService personService,
      TeamService teamService, PlayerMapper mapper) {
    super(playerRepository, mapper);
    this.playerRepository = playerRepository;
    this.personService = personService;
    this.teamService = teamService;
  }

  @Override
  protected void resolveRelationships(PlayerDTO dto, Player player) {
    // Map person from ID
    if (dto.getPersonId() != null) {
      var person = personService.getById(dto.getPersonId())
          .orElseThrow(() -> new ResourceNotFoundException("Person", "id", dto.getPersonId()));
      player.setPerson(person);
    }

    // Map current team from display name or ID
    if (dto.getCurrentTeamDisplayName() != null && !dto.getCurrentTeamDisplayName().trim().isEmpty()) {
      var team = teamService.getByDisplayName(dto.getCurrentTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getCurrentTeamDisplayName()));
      player.setCurrentTeam(team);
    } else if (dto.getCurrentTeamId() != null) {
      var team = teamService.getById(dto.getCurrentTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getCurrentTeamId()));
      player.setCurrentTeam(team);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Player player) {
    Objects.requireNonNull(player, "Player cannot be null");
    // A player is unique by person - one person can only be one player
    return player.getPerson() != null && playerRepository.existsByPersonId(player.getPerson().getId());
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Player player) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(player, "Player cannot be null");

    return player.getPerson() != null && playerRepository.existsByPersonIdAndIdNot(player.getPerson().getId(), id);
  }

}
