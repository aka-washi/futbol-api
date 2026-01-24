package com.eagle.futbolapi.features.staff.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.staff.dto.StaffDTO;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.mapper.StaffMapper;
import com.eagle.futbolapi.features.staff.repository.StaffRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class StaffService extends BaseCrudService<Staff, Long, StaffDTO> {

  private final StaffRepository staffRepository;
  private final PersonService personService;
  private final TeamService teamService;

  public StaffService(StaffRepository staffRepository, PersonService personService,
      TeamService teamService, StaffMapper mapper) {
    super(staffRepository, mapper);
    this.staffRepository = staffRepository;
    this.personService = personService;
    this.teamService = teamService;
  }

  @Override
  protected void resolveRelationships(StaffDTO dto, Staff staff) {
    // Map person from ID
    if (dto.getPersonId() != null) {
      var person = personService.getById(dto.getPersonId())
          .orElseThrow(() -> new ResourceNotFoundException("Person", "id", dto.getPersonId()));
      staff.setPerson(person);
    }

    // Map current team from display name or ID
    if (dto.getCurrentTeamDisplayName() != null && !dto.getCurrentTeamDisplayName().trim().isEmpty()) {
      var team = teamService.getByDisplayName(dto.getCurrentTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getCurrentTeamDisplayName()));
      staff.setCurrentTeam(team);
    } else if (dto.getCurrentTeamId() != null) {
      var team = teamService.getById(dto.getCurrentTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getCurrentTeamId()));
      staff.setCurrentTeam(team);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Staff staff) {
    Objects.requireNonNull(staff, "Staff cannot be null");
    // A staff member is unique by person and role - one person can have multiple
    // staff roles
    return staff.getPerson() != null && staff.getRole() != null
        && staffRepository.existsByPersonIdAndRole(staff.getPerson().getId(), staff.getRole());
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Staff staff) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(staff, "Staff cannot be null");

    return staff.getPerson() != null && staff.getRole() != null
        && staffRepository.existsByPersonIdAndRoleAndIdNot(staff.getPerson().getId(), staff.getRole(), id);
  }

}
