package com.eagle.futbolapi.features.standing.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.standing.dto.StandingDTO;
import com.eagle.futbolapi.features.standing.entity.Standing;
import com.eagle.futbolapi.features.standing.mapper.StandingMapper;
import com.eagle.futbolapi.features.standing.service.StandingService;

@Validated
@RestController
@RequestMapping("/standings")
public class StandingController extends BaseCrudController<Standing, StandingDTO, StandingService, StandingMapper> {

  private static final String RESOURCE_NAME = "Standing";
  private static final String SUCCESS_MESSAGE = "Standing(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Standing already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public StandingController(StandingService standingService, StandingMapper standingMapper) {
    super(
        standingService,
        standingMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
