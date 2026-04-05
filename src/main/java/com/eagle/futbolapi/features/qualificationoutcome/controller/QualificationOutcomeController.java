package com.eagle.futbolapi.features.qualificationoutcome.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.qualificationoutcome.dto.QualificationOutcomeDto;
import com.eagle.futbolapi.features.qualificationoutcome.entity.QualificationOutcome;
import com.eagle.futbolapi.features.qualificationoutcome.mapper.QualificationOutcomeMapper;
import com.eagle.futbolapi.features.qualificationoutcome.service.QualificationOutcomeService;

@RestController
@RequestMapping("/qualification-outcomes")
public class QualificationOutcomeController
    extends BaseCrudController<QualificationOutcome, QualificationOutcomeDto,
        QualificationOutcomeService, QualificationOutcomeMapper> {

  private static final String RESOURCE_NAME = "Qualification Outcome";
  private static final String SUCCESS_MESSAGE = "Qualification Outcome(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Qualification Outcome already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public QualificationOutcomeController(QualificationOutcomeService service,
      QualificationOutcomeMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
