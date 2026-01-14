package com.eagle.futbolapi.features.staff.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.person.repository.PersonRepository;
import com.eagle.futbolapi.features.staff.dto.StaffDTO;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.entity.StaffRole;
import com.eagle.futbolapi.features.team.repository.TeamRepository;

@Component
public class StaffMapper {

    private final PersonRepository personRepository;
    private final TeamRepository teamRepository;

    public StaffMapper(PersonRepository personRepository, TeamRepository teamRepository) {
        this.personRepository = personRepository;
        this.teamRepository = teamRepository;
    }

    public StaffDTO toStaffDTO(Staff staff) {
        if (staff == null) {
            return null;
        }

        return StaffDTO.builder()
                .id(staff.getId())
                .personId(staff.getPerson() != null ? staff.getPerson().getId() : null)
                .personDisplayName(staff.getPerson() != null ? staff.getPerson().getDisplayName() : null)
                .role(staff.getRole() != null ? staff.getRole().name() : null)
                .currentTeamId(staff.getCurrentTeam() != null ? staff.getCurrentTeam().getId() : null)
                .currentTeamDisplayName(staff.getCurrentTeam() != null ? staff.getCurrentTeam().getDisplayName() : null)
                .active(staff.getActive())
                .createdAt(staff.getCreatedAt())
                .createdBy(staff.getCreatedBy())
                .updatedAt(staff.getUpdatedAt())
                .updatedBy(staff.getUpdatedBy())
                .build();
    }

    public Staff toStaff(StaffDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Staff.builder()
                .id(dto.getId())
                .active(dto.getActive())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy());

        if (dto.getPersonId() != null) {
            var person = personRepository.findById(dto.getPersonId())
                    .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + dto.getPersonId()));
            builder.person(person);
        }

        if (dto.getRole() != null) {
            try {
                builder.role(StaffRole.valueOf(dto.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid staff role: " + dto.getRole());
            }
        }

        if (dto.getCurrentTeamId() != null) {
            var team = teamRepository.findById(dto.getCurrentTeamId())
                    .orElse(null);
            builder.currentTeam(team);
        }

        return builder.build();
    }
}
