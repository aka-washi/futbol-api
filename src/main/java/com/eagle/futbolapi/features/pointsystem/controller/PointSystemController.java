package com.eagle.futbolapi.features.pointsystem.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDto;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;
import com.eagle.futbolapi.features.pointsystem.mapper.PointSystemMapper;
import com.eagle.futbolapi.features.pointsystem.service.PointSystemService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing Point System resources.
 * Provides endpoints for CRUD operations on point systems including:
 * <ul>
 * <li>Creating new point systems</li>
 * <li>Retrieving point systems by ID or listing all point systems</li>
 * <li>Updating existing point systems</li>
 * <li>Deleting point systems</li>
 * </ul>
 *
 * <p>
 * All endpoints are mapped under the {@code /point-systems} base path.
 * Request and response bodies use {@link PointSystemDto} for data transfer.
 * Input validation is performed automatically using Bean Validation
 * annotations.
 *
 * @see PointSystemDto
 * @see PointSystemService
 * @see BaseCrudController
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/pointSystems")
public class PointSystemController
    extends BaseCrudController<PointSystem, PointSystemDto, PointSystemService, PointSystemMapper> {

  private static final String RESOURCE_NAME = "Point System";
  private static final String SUCCESS_MESSAGE = "Point System retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Point System already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  /**
   * Constructs a new PointSystemController with the required dependencies.
   *
   * @param service the PointSystemService instance for business logic operations
   * @param mapper  the PointSystemMapper instance for entity-DTO conversions
   */
  protected PointSystemController(PointSystemService service, PointSystemMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
