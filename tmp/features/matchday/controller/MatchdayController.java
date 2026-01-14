package com.eagle.futbolapi.features.matchday.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.matchday.dto.MatchdayDTO;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.service.MatchdayService;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/matchdays")
@Validated
public class MatchdayController extends BaseCrudController<Matchday, MatchdayDTO, MatchdayService, Object> {

    public MatchdayController(MatchdayService matchdayService) {
        super(
                matchdayService,
                null, // Mapper not available yet
                "Matchday",
                "Matchday retrieved successfully",
                "Matchday already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Matchday> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Matchday getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Matchday createEntity(Matchday entity) {
        return service.create(entity);
    }

    @Override
    protected Matchday updateEntity(Long id, Matchday entity) {
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
    protected MatchdayDTO toDTO(Matchday entity) {
        // Simple DTO conversion (would be better with mapper)
        return MatchdayDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    @Override
    protected Matchday toEntity(MatchdayDTO dto) {
        // Simple entity conversion (would be better with mapper)
        return Matchday.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
