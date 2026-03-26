package com.eagle.futbolapi.features.registration.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.repository.PlayerRepository;
import com.eagle.futbolapi.features.registration.dto.RegistrationDto;
import com.eagle.futbolapi.features.registration.entity.Registration;
import com.eagle.futbolapi.features.registration.mapper.RegistrationMapper;
import com.eagle.futbolapi.features.registration.repository.RegistrationRepository;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.repository.StaffRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@Validated
public class RegistrationService extends BaseCrudService<Registration, Long, RegistrationDto> {

  private final CompetitionService competitionService;
  private final TeamService teamService;
  private final PlayerRepository playerRepository;
  private final StaffRepository staffRepository;

  public RegistrationService(RegistrationRepository repository, RegistrationMapper mapper,
      CompetitionService competitionService, TeamService teamService,
      PlayerRepository playerRepository, StaffRepository staffRepository) {
    super(repository, mapper);
    this.competitionService = competitionService;
    this.teamService = teamService;
    this.playerRepository = playerRepository;
    this.staffRepository = staffRepository;
  }

  @Override
  protected void validateForCreate(RegistrationDto dto, Registration entity) {
    // Ensure either player or staff is set (DTO-level constraint exists, but double-check)
    if ((entity.getPlayer() == null) && (entity.getStaff() == null)) {
      throw new IllegalArgumentException("Either player or staff must be provided");
    }

    // If a jersey number is provided, ensure it's unique for the competition+team
    if (entity.getJerseyNumber() != null) {
      Long compId = entity.getCompetition() != null ? entity.getCompetition().getId() : null;
      Long teamId = entity.getTeam() != null ? entity.getTeam().getId() : null;
      if (compId == null || teamId == null) {
        throw new IllegalArgumentException("Competition and Team must be provided when validating jersey number");
      }
      RegistrationRepository regRepo = (RegistrationRepository) repository;
      boolean exists = regRepo.existsByCompetitionIdAndTeamIdAndJerseyNumber(compId, teamId, entity.getJerseyNumber());
      if (exists) {
        throw new IllegalArgumentException("Jersey number already in use for this team and competition");
      }
    }
    // If registering a player, ensure the player is active
    if (entity.getPlayer() != null && Boolean.FALSE.equals(entity.getPlayer().getActive())) {
      throw new IllegalArgumentException("Player must be active to be registered");
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Registration registration) {
    Objects.requireNonNull(registration, "Registration cannot be null");
    return isDuplicate(registration, UniquenessStrategy.ANY);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Registration registration) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(registration, "Registration cannot be null");

    log.debug(
        "Checking duplicates for ID={}, competitionId={}, teamId={}, playerId={}, staffId={}",
        id,
        registration.getCompetition() != null ? registration.getCompetition().getId() : null,
        registration.getTeam() != null ? registration.getTeam().getId() : null,
        registration.getPlayer() != null ? registration.getPlayer().getId() : null,
        registration.getStaff() != null ? registration.getStaff().getId() : null);

    boolean result = isDuplicate(id, registration, UniquenessStrategy.ANY);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(RegistrationDto dto, Registration entity) {
    // Competition by display name or id
    if (dto.getCompetitionDisplayName() != null && !dto.getCompetitionDisplayName().trim().isEmpty()) {
      var competition = competitionService.findByDisplayName(dto.getCompetitionDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "displayName", dto.getCompetitionDisplayName()));
      entity.setCompetition(competition);
    } else if (dto.getCompetitionId() != null) {
      var competition = competitionService.getById(dto.getCompetitionId())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "id", dto.getCompetitionId()));
      entity.setCompetition(competition);
    }

    // Team by display name or id
    if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.findByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      entity.setTeam(team);
    } else if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      entity.setTeam(team);
    }

    // Player by id
    if (dto.getPlayerDisplayName() != null && !dto.getPlayerDisplayName().trim().isEmpty()) {
      Player player = playerRepository.findByPersonDisplayName(dto.getPlayerDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Player", "displayName", dto.getPlayerDisplayName()));
      entity.setPlayer(player);
    } else if (dto.getPlayerId() != null) {
      Player player = playerRepository.findById(dto.getPlayerId())
          .orElseThrow(() -> new ResourceNotFoundException("Player", "id", dto.getPlayerId()));
      entity.setPlayer(player);
    }

    // Staff by id
    if (dto.getStaffDisplayName() != null && !dto.getStaffDisplayName().trim().isEmpty()) {
      Staff staff = staffRepository.findByPersonDisplayName(dto.getStaffDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Staff", "displayName", dto.getStaffDisplayName()));
      entity.setStaff(staff);
    } else if (dto.getStaffId() != null) {
      Staff staff = staffRepository.findById(dto.getStaffId())
          .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", dto.getStaffId()));
      entity.setStaff(staff);
    }
  }

}
