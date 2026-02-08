package com.eagle.futbolapi.features.country.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.country.dto.CountryDto;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.mapper.CountryMapper;
import com.eagle.futbolapi.features.country.service.CountryService;

/**
 * REST controller for managing Country resources.
 * Provides endpoints for CRUD operations on countries including:
 * <ul>
 * <li>Creating new countries</li>
 * <li>Retrieving countries by ID or listing all countries</li>
 * <li>Updating existing countries</li>
 * <li>Deleting countries</li>
 * </ul>
 * 
 * <p>
 * All endpoints are mapped under the {@code /countries} base path.
 * Request and response bodies use {@link CountryDto} for data transfer.
 * Input validation is performed automatically using Bean Validation
 * annotations.
 * 
 * @see CountryDto
 * @see CountryService
 * @see BaseCrudController
 */
@Validated
@RestController
@RequestMapping("/countries")
public class CountryController extends BaseCrudController<Country, CountryDto, CountryService, CountryMapper> {

  private static final String RESOURCE_NAME = "Country";
  private static final String SUCCESS_MESSAGE = "Country retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Country already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  /**
   * Constructs a new CountryController with the required dependencies.
   * 
   * @param service the CountryService instance for business logic operations
   * @param mapper  the CountryMapper instance for entity-DTO conversions
   */
  protected CountryController(CountryService service, CountryMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
