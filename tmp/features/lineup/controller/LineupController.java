package com.eagle.futbolapi.features.lineup.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.lineup.dto.LineupDTO;
import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.service.LineupService;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/lineups")
@Validated
public class LineupController extends BaseCrudController<Lineup, LineupDTO, LineupService, Object> {

    public LineupController(LineupService lineupService) {
        super(
                lineupService,
                null, // Mapper not available yet
                "Lineup",
                "Lineup retrieved successfully",
                "Lineup already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Lineup> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Lineup getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Lineup createEntity(Lineup entity) {
        return service.create(entity);
    }

    @Override
    protected Lineup updateEntity(Long id, Lineup entity) {
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
    protected LineupDTO toDTO(Lineup entity) {
        // Simple DTO conversion (would be better with mapper)
        return LineupDTO.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    @Override
    protected Lineup toEntity(LineupDTO dto) {
        // Simple entity conversion (would be better with mapper)
        return Lineup.builder()
                .id(dto.getId())
                .build();
    }
}
