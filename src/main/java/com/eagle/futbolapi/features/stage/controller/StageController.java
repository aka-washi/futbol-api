package com.eagle.futbolapi.features.stage.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.stage.dto.StageDto;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.mapper.StageMapper;
import com.eagle.futbolapi.features.stage.service.StageService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for Stage resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/stages")
public class StageController extends BaseCrudController<Stage, StageDto, StageService, StageMapper> {

  private static final String RESOURCE_NAME = "Stage";
  private static final String SUCCESS_MESSAGE = "Stage retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Stage already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected StageController(StageService service, StageMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }
}
