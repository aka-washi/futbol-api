package com.eagle.futbolapi.features.standing.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.standing.dto.StandingDTO;
import com.eagle.futbolapi.features.standing.entity.Standing;
import com.eagle.futbolapi.features.standing.mapper.StandingMapper;
import com.eagle.futbolapi.features.standing.service.StandingService;

@RestController
@RequestMapping("/standings")
@Validated
public class StandingController extends BaseCrudController<Standing, StandingDTO, StandingService, StandingMapper> {

  protected StandingController(StandingService service, StandingMapper mapper, String resourceName,
      String successMessage, String duplicateMessage, String serverError) {
    super(service, mapper, resourceName, successMessage, duplicateMessage, serverError);
    // TODO Auto-generated constructor stub
  }

}
