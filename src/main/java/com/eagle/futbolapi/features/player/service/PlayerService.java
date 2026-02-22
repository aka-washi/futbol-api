package com.eagle.futbolapi.features.player.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.player.dto.PlayerDto;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.mapper.PlayerMapper;
import com.eagle.futbolapi.features.player.repository.PlayerRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Player entity business logic.
 */
@Slf4j
@Service
@Transactional
public class PlayerService extends BaseCrudService<Player, Long, PlayerDto> {

  private final PlayerRepository repository;
  private final PersonService personService;
  private final TeamService teamService;

  protected PlayerService(PlayerRepository repository, PlayerMapper mapper,
      PersonService personService, TeamService teamService) {
    super(repository, mapper);
    this.repository = repository;
    this.personService = personService;
    this.teamService = teamService;
  }

  public Optional<Player> findByPersonDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Person display name cannot be null or empty");
    }
    return repository.findByPersonDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull Player player) {
    Objects.requireNonNull(player, "Player cannot be null");

    return isDuplicate(player, UniquenessStrategy.ANY);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Player player) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(player, "Player cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, personId={}, position={}, preferredFoot={}, currentTeamId={}, active={}",
        id, player.getPerson() != null ? player.getPerson().getId() : null,
        player.getPosition(), player.getPreferredFoot(),
        player.getCurrentTeam() != null ? player.getCurrentTeam().getId() : null,
        player.getActive());

    boolean result = isDuplicate(id, player, UniquenessStrategy.ANY);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(PlayerDto dto, Player player) {
    // Map person from PersonDto object - find or create
    if (dto.getPerson() != null) {
      Person person = personService.findOrCreate(dto.getPerson());
      player.setPerson(person);
    }

    // Map current team from display name or ID (optional relationship)
    if (dto.getCurrentTeamDisplayName() != null && !dto.getCurrentTeamDisplayName().trim().isEmpty()) {
      var team = teamService.findByDisplayName(dto.getCurrentTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getCurrentTeamDisplayName()));
      player.setCurrentTeam(team);
    } else if (dto.getCurrentTeamId() != null) {
      var team = teamService.getById(dto.getCurrentTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getCurrentTeamId()));
      player.setCurrentTeam(team);
    }
  }

}
