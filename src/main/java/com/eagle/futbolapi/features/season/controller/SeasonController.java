package com.eagle.futbolapi.features.season.controller;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.season.dto.SeasonDto;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.mapper.SeasonMapper;
import com.eagle.futbolapi.features.season.service.SeasonService;

/**
 * REST controller for managing Season resources.
 * Provides endpoints for CRUD operations on seasons including:
 * <ul>
 * <li>Creating new seasons</li>
 * <li>Retrieving seasons by ID or listing all seasons</li>
 * <li>Updating existing seasons</li>
 * <li>Deleting seasons</li>
 * </ul>
 *
 * <p>
 * All endpoints are mapped under the {@code /seasons} base path.
 * Request and response bodies use {@link SeasonDto} for data transfer.
 * Input validation is performed automatically using Bean Validation
 * annotations.
 *
 * @see SeasonDto
 * @see SeasonService
 * @see BaseCrudController
 */
public class SeasonController extends BaseCrudController<Season, SeasonDto, SeasonService, SeasonMapper> {

  private static final String RESOURCE_NAME = "Season";
  private static final String SUCCESS_MESSAGE = "Season retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Season already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  /**
   * Constructs a new SeasonController with the required dependencies.
   *
   * @param service the SeasonService instance for business logic operations
   * @param mapper  the SeasonMapper instance for entity-DTO conversions
   */
  protected SeasonController(SeasonService service, SeasonMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
