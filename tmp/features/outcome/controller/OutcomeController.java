package com.eagle.futbolapi.features.outcome.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.outcome.dto.OutcomeDTO;
import com.eagle.futbolapi.features.outcome.entity.Outcome;
import com.eagle.futbolapi.features.outcome.mapper.OutcomeMapper;
import com.eagle.futbolapi.features.outcome.service.OutcomeService;

@Validated
@RestController
@RequestMapping("/outcomes")
public class OutcomeController extends BaseCrudController<Outcome, OutcomeDTO, OutcomeService, OutcomeMapper> {

  private static final String RESOURCE_NAME = "Outcome";
  private static final String SUCCESS_MESSAGE = "Outcome(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Outcome already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public OutcomeController(OutcomeService outcomeService, OutcomeMapper outcomeMapper) {
    super(
        outcomeService,
        outcomeMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
