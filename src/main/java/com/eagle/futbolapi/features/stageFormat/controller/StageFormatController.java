package com.eagle.futbolapi.features.stageFormat.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.stageFormat.dto.StageFormatDto;
import com.eagle.futbolapi.features.stageFormat.entity.StageFormat;
import com.eagle.futbolapi.features.stageFormat.mapper.StageFormatMapper;
import com.eagle.futbolapi.features.stageFormat.service.StageFormatService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for StageFormat resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/stage-formats")
public class StageFormatController
    extends BaseCrudController<StageFormat, StageFormatDto, StageFormatService, StageFormatMapper> {

  private static final String RESOURCE_NAME = "StageFormat";
  private static final String SUCCESS_MESSAGE = "StageFormat retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "StageFormat already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected StageFormatController(StageFormatService service, StageFormatMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }
}
