package com.eagle.futbolapi.features.standing.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.standing.dto.StandingDTO;
import com.eagle.futbolapi.features.standing.entity.Standing;
import com.eagle.futbolapi.features.standing.service.StandingService;

@RestController
@RequestMapping("/standings")
@Validated
public class StandingController extends BaseCrudController<Standing, StandingDTO, StandingService, Object> {

    public StandingController(StandingService standingService) {
        super(
                standingService,
                null, // Mapper not available yet
                "Standing",
                "Standing retrieved successfully",
                "Standing already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Standing> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Standing getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Standing createEntity(Standing entity) {
        return service.create(entity);
    }

    @Override
    protected Standing updateEntity(Long id, Standing entity) {
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
    protected StandingDTO toDTO(Standing entity) {
        // Simple DTO conversion (would be better with mapper)
        return StandingDTO.builder()
                .id(entity.getId())
                .position(entity.getPosition())
                .played(entity.getPlayed())
                .won(entity.getWon())
                .drawn(entity.getDrawn())
                .lost(entity.getLost())
                .goalsFor(entity.getGoalsFor())
                .goalsAgainst(entity.getGoalsAgainst())
                .goalDifference(entity.getGoalDifference())
                .points(entity.getPoints())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    @Override
    protected Standing toEntity(StandingDTO dto) {
        // Simple entity conversion (would be better with mapper)
        return Standing.builder()
                .id(dto.getId())
                .position(dto.getPosition())
                .played(dto.getPlayed())
                .won(dto.getWon())
                .drawn(dto.getDrawn())
                .lost(dto.getLost())
                .goalsFor(dto.getGoalsFor())
                .goalsAgainst(dto.getGoalsAgainst())
                .goalDifference(dto.getGoalDifference())
                .points(dto.getPoints())
                .build();
    }
}
