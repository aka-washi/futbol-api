package com.eagle.futbolapi.features.rosterentry.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.rosterentry.dto.RosterEntryDTO;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;
import com.eagle.futbolapi.features.rosterentry.service.RosterEntryService;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/roster-entries")
@Validated
public class RosterEntryController extends BaseCrudController<RosterEntry, RosterEntryDTO, RosterEntryService, Object> {

    public RosterEntryController(RosterEntryService rosterEntryService) {
        super(
                rosterEntryService,
                null, // Mapper not available yet
                "RosterEntry",
                "Roster entry retrieved successfully",
                "Roster entry already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<RosterEntry> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected RosterEntry getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected RosterEntry createEntity(RosterEntry entity) {
        return service.create(entity);
    }

    @Override
    protected RosterEntry updateEntity(Long id, RosterEntry entity) {
        return service.update(id, entity);
    }

    @Override
    protected void deleteEntity(Long id) {
        service.delete(id);
    }

    @Override
    protected boolean existsById(Long id) {
        return service.existsById(id);
    }

    @Override
    protected RosterEntryDTO toDTO(RosterEntry entity) {
        // Simple DTO conversion (would be better with mapper)
        return RosterEntryDTO.builder()
                .id(entity.getId())
                .jerseyNumber(entity.getJerseyNumber())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    @Override
    protected RosterEntry toEntity(RosterEntryDTO dto) {
        // Simple entity conversion (would be better with mapper)
        return RosterEntry.builder()
                .id(dto.getId())
                .jerseyNumber(dto.getJerseyNumber())
                .build();
    }
}
