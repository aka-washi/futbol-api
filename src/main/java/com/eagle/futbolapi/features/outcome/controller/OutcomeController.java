package com.eagle.futbolapi.features.outcome.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.outcome.dto.OutcomeDTO;
import com.eagle.futbolapi.features.outcome.entity.Outcome;
import com.eagle.futbolapi.features.outcome.service.OutcomeService;
import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/outcomes")
@Validated
public class OutcomeController extends BaseCrudController<Outcome, OutcomeDTO, OutcomeService, Object> {

}
