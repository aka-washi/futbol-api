package com.eagle.futbolapi.features.outcome.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.outcome.dto.OutcomeDTO;
import com.eagle.futbolapi.features.outcome.entity.Outcome;
import com.eagle.futbolapi.features.outcome.mapper.OutcomeMapper;
import com.eagle.futbolapi.features.outcome.service.OutcomeService;

@RestController
@RequestMapping("/outcomes")
@Validated
public class OutcomeController extends BaseCrudController<Outcome, OutcomeDTO, OutcomeService, OutcomeMapper> {

  protected OutcomeController(OutcomeService service, OutcomeMapper mapper, String resourceName,
      String successMessage, String duplicateMessage, String serverError) {
    super(service, mapper, resourceName, successMessage, duplicateMessage, serverError);
    // TODO Auto-generated constructor stub
  }

}
