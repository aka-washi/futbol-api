package com.eagle.futbolapi.features.stage.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.stage.dto.StageDTO;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.service.StageService;

@RestController
@RequestMapping("/stages")
@Validated
public class StageController extends BaseCrudController<Stage, StageDTO, StageService, Object> {

    public StageController(StageService stageService) {
        super(
                stageService,
                null, // Mapper not available yet
                "Stage",
                "Stage retrieved successfully",
                "Stage already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Stage> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Stage getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Stage createEntity(Stage entity) {
        return service.create(entity);
    }

    @Override
    protected Stage updateEntity(Long id, Stage entity) {
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
    protected StageDTO toDTO(Stage entity) {
        // Simple DTO conversion (would be better with mapper)
        return StageDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    @Override
    protected Stage toEntity(StageDTO dto) {
        // Simple entity conversion (would be better with mapper)
        return Stage.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
