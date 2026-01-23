package com.eagle.futbolapi.features.pointsystem.controller;

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
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "name", name));
    PointSystemDTO pointSystemDTO = mapper.toDTO(pointSystem);
    return ResponseUtil.success(pointSystemDTO, successMessage);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<PointSystemDTO>> getPointSystemByDisplayName(@PathVariable String displayName) {
    PointSystem pointSystem = service.getPointSystemByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "displayName", displayName));
    PointSystemDTO pointSystemDTO = mapper.toDTO(pointSystem);
    return ResponseUtil.success(pointSystemDTO, successMessage);
  }

}
