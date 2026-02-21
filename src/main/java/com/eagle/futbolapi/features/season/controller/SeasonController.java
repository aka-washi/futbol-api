package com.eagle.futbolapi.features.season.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.season.dto.SeasonDto;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.mapper.SeasonMapper;
import com.eagle.futbolapi.features.season.service.SeasonService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for Season resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/seasons")
public class SeasonController extends BaseCrudController<Season, SeasonDto, SeasonService, SeasonMapper> {

  private static final String RESOURCE_NAME = "Season";
  private static final String SUCCESS_MESSAGE = "Season retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Season already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected SeasonController(SeasonService service, SeasonMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
