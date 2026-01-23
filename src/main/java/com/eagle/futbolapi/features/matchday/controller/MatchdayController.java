package com.eagle.futbolapi.features.matchday.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.matchday.dto.MatchdayDTO;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.mapper.MatchdayMapper;
import com.eagle.futbolapi.features.matchday.service.MatchdayService;

@RestController
@RequestMapping("/matchdays")
@Validated
public class MatchdayController extends BaseCrudController<Matchday, MatchdayDTO, MatchdayService, MatchdayMapper> {

  protected MatchdayController(MatchdayService service, MatchdayMapper mapper, String resourceName,
      String successMessage, String duplicateMessage, String serverError) {
    super(service, mapper, resourceName, successMessage, duplicateMessage, serverError);
    // TODO Auto-generated constructor stub
  }

}
