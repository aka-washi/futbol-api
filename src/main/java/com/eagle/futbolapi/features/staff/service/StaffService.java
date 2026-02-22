package com.eagle.futbolapi.features.staff.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.staff.dto.StaffDto;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.mapper.StaffMapper;
import com.eagle.futbolapi.features.staff.repository.StaffRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Staff entity business logic.
 */
@Slf4j
@Service
@Transactional
public class StaffService extends BaseCrudService<Staff, Long, StaffDto> {

  private final PersonService personService;
  private final TeamService teamService;

  protected StaffService(StaffRepository repository, StaffMapper mapper,
      PersonService personService, TeamService teamService) {
    super(repository, mapper);
    this.personService = personService;
    this.teamService = teamService;
  }

  @Override
  protected boolean isDuplicate(@NotNull Staff staff) {
    Objects.requireNonNull(staff, "Staff cannot be null");

    return isDuplicate(staff, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Staff staff) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(staff, "Staff cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, personId={}, role={}, currentTeamId={}, active={}",
        id, staff.getPerson() != null ? staff.getPerson().getId() : null,
        staff.getRole(),
        staff.getCurrentTeam() != null ? staff.getCurrentTeam().getId() : null,
        staff.getActive());

    boolean result = isDuplicate(id, staff, UniquenessStrategy.ALL);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(StaffDto dto, Staff staff) {
    // Map person from PersonDto object - find or create
    if (dto.getPerson() != null) {
      var person = personService.findOrCreate(dto.getPerson());
      staff.setPerson(person);
    }

    // Map current team from display name or ID (optional relationship)
    if (dto.getCurrentTeamDisplayName() != null && !dto.getCurrentTeamDisplayName().trim().isEmpty()) {
      var team = teamService.findByDisplayName(dto.getCurrentTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getCurrentTeamDisplayName()));
      staff.setCurrentTeam(team);
    } else if (dto.getCurrentTeamId() != null) {
      var team = teamService.getById(dto.getCurrentTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getCurrentTeamId()));
      staff.setCurrentTeam(team);
    }
  }

}
