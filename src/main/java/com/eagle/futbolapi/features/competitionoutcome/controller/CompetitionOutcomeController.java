package com.eagle.futbolapi.features.competitionoutcome.controller;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.competitionoutcome.dto.CompetitionOutcomeDto;
import com.eagle.futbolapi.features.competitionoutcome.entity.CompetitionOutcome;
import com.eagle.futbolapi.features.competitionoutcome.mapper.CompetitionOutcomeMapper;
import com.eagle.futbolapi.features.competitionoutcome.service.CompetitionOutcomeService;

public class CompetitionOutcomeController extends BaseCrudController<CompetitionOutcome, CompetitionOutcomeDto,
    CompetitionOutcomeService, CompetitionOutcomeMapper> {

  private static final String RESOURCE_NAME = "Competition Outcome";
  private static final String SUCCESS_MESSAGE = "Competition Outcome retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Competition Outcome already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected CompetitionOutcomeController(CompetitionOutcomeService service, CompetitionOutcomeMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
