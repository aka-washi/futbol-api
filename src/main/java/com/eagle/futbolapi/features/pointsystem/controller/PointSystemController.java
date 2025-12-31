package com.eagle.futbolapi.features.pointsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDTO;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;
import com.eagle.futbolapi.features.pointsystem.mapper.PointSystemMapper;
import com.eagle.futbolapi.features.pointsystem.service.PointSystemService;
import com.eagle.futbolapi.features.shared.ApiResponse;

@RestController
@RequestMapping("/pointSystems")
@Validated
public class PointSystemController extends com.eagle.futbolapi.features.shared.controller.BaseCrudController<PointSystem, PointSystemDTO, PointSystemService, PointSystemMapper> {

    private static final String NOT_FOUND_SUFFIX = " not found";

    public PointSystemController(PointSystemService pointSystemService, PointSystemMapper pointSystemMapper) {
        super(
            pointSystemService,
            pointSystemMapper,
            "Point System",
            "Point System retrieved successfully",
            "Point System already exists",
            "SERVER_ERROR"
        );
    }

    // PointSystem-specific endpoints
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<PointSystemDTO>> getPointSystemByName(@PathVariable String name) {
        PointSystem pointSystem = service.getPointSystemByName(name)
                .orElseThrow(() -> new com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException(resourceName + NOT_FOUND_SUFFIX, "name", name));
        PointSystemDTO pointSystemDTO = mapper.toPointSystemDTO(pointSystem);
        return com.eagle.futbolapi.features.shared.ResponseUtil.success(pointSystemDTO, successMessage);
    }

    @GetMapping("/displayName/{displayName}")
    public ResponseEntity<ApiResponse<PointSystemDTO>> getPointSystemByDisplayName(@PathVariable String displayName) {
        PointSystem pointSystem = service.getPointSystemByDisplayName(displayName)
                .orElseThrow(() -> new com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException(resourceName + NOT_FOUND_SUFFIX, "displayName", displayName));
        PointSystemDTO pointSystemDTO = mapper.toPointSystemDTO(pointSystem);
        return com.eagle.futbolapi.features.shared.ResponseUtil.success(pointSystemDTO, successMessage);
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected org.springframework.data.domain.Page<PointSystem> getAllEntities(org.springframework.data.domain.Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected PointSystem getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException(resourceName + NOT_FOUND_SUFFIX, "id", id));
    }

    @Override
    protected PointSystem createEntity(PointSystem entity) {
        return service.create(entity);
    }

    @Override
    protected PointSystem updateEntity(Long id, PointSystem entity) {
        // PointSystemService does not use id for update, so just pass entity
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
    protected PointSystemDTO toDTO(PointSystem entity) {
        return mapper.toPointSystemDTO(entity);
    }

    @Override
    protected PointSystem toEntity(PointSystemDTO dto) {
        return mapper.toPointSystem(dto);
    }
}
