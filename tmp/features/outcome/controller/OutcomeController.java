package com.eagle.futbolapi.features.outcome.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.outcome.dto.OutcomeDTO;
import com.eagle.futbolapi.features.outcome.entity.Outcome;
import com.eagle.futbolapi.features.outcome.service.OutcomeService;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/outcomes")
@Validated
public class OutcomeController extends BaseCrudController<Outcome, OutcomeDTO, OutcomeService, Object> {

    public OutcomeController(OutcomeService outcomeService) {
        super(
                outcomeService,
                null, // Mapper not available yet
                "Outcome",
                "Outcome retrieved successfully",
                "Outcome already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Outcome> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Outcome getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Outcome createEntity(Outcome entity) {
        return service.create(entity);
    }

    @Override
    protected Outcome updateEntity(Long id, Outcome entity) {
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
    protected OutcomeDTO toDTO(Outcome entity) {
        // Simple DTO conversion (would be better with mapper)
        return OutcomeDTO.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    @Override
    protected Outcome toEntity(OutcomeDTO dto) {
        // Simple entity conversion (would be better with mapper)
        return Outcome.builder()
                .id(dto.getId())
                .build();
    }
}
