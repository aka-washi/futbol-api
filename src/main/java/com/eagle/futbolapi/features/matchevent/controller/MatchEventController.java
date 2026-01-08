package com.eagle.futbolapi.features.matchevent.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.matchevent.dto.MatchEventDTO;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.matchevent.service.MatchEventService;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/match-events")
@Validated
public class MatchEventController extends BaseCrudController<MatchEvent, MatchEventDTO, MatchEventService, Object> {

    public MatchEventController(MatchEventService matchEventService) {
        super(
                matchEventService,
                null, // Mapper not available yet
                "MatchEvent",
                "Match event retrieved successfully",
                "Match event already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<MatchEvent> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected MatchEvent getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected MatchEvent createEntity(MatchEvent entity) {
        return service.create(entity);
    }

    @Override
    protected MatchEvent updateEntity(Long id, MatchEvent entity) {
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
    protected MatchEventDTO toDTO(MatchEvent entity) {
        // Simple DTO conversion (would be better with mapper)
        return MatchEventDTO.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    @Override
    protected MatchEvent toEntity(MatchEventDTO dto) {
        // Simple entity conversion (would be better with mapper)
        return MatchEvent.builder()
                .id(dto.getId())
                .build();
    }
}
