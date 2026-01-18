package com.eagle.futbolapi.features.pointsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDTO;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;
import com.eagle.futbolapi.features.pointsystem.mapper.PointSystemMapper;
import com.eagle.futbolapi.features.pointsystem.service.PointSystemService;

@Validated
@RestController
@RequestMapping("/pointSystems")
public class PointSystemController
    extends BaseCrudController<PointSystem, PointSystemDTO, PointSystemService, PointSystemMapper> {

  private static final String NOT_FOUND_SUFFIX = " not found";
  private static final String RESOURCE_NAME = "Point System";
  private static final String SUCCESS_MESSAGE = "Point System retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Point System already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public PointSystemController(PointSystemService pointSystemService, PointSystemMapper pointSystemMapper) {
    super(
        pointSystemService,
        pointSystemMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  // PointSystem-specific endpoints
  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<PointSystemDTO>> getPointSystemByName(@PathVariable String name) {
    PointSystem pointSystem = service.getPointSystemByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName + NOT_FOUND_SUFFIX, "name", name));
    PointSystemDTO pointSystemDTO = mapper.toPointSystemDTO(pointSystem);
    return ResponseUtil.success(pointSystemDTO, successMessage);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<PointSystemDTO>> getPointSystemByDisplayName(@PathVariable String displayName) {
    PointSystem pointSystem = service.getPointSystemByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName + NOT_FOUND_SUFFIX, "displayName", displayName));
    PointSystemDTO pointSystemDTO = mapper.toPointSystemDTO(pointSystem);
    return ResponseUtil.success(pointSystemDTO, successMessage);
  }

  // Implement abstract methods from BaseCrudController
  @Override
  protected Page<PointSystem> getAllEntities(Pageable pageable) {
    return service.getAll(pageable);
  }

  @Override
  protected PointSystem getEntityById(Long id) {
    return service.getById(id)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName + NOT_FOUND_SUFFIX, "id", id.toString()));
  }

  @Override
  protected PointSystem createEntity(PointSystemDTO dto) {
    return service.create(dto);
  }

  @Override
  protected PointSystem updateEntity(Long id, PointSystemDTO dto) {
    return service.update(id, dto);
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
