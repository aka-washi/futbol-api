package com.eagle.futbolapi.features.lineup.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.lineup.dto.LineupDTO;
import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.mapper.LineupMapper;
import com.eagle.futbolapi.features.lineup.service.LineupService;

@RestController
@RequestMapping("/lineups")
@Validated
public class LineupController extends BaseCrudController<Lineup, LineupDTO, LineupService, LineupMapper> {

  protected LineupController(LineupService service, LineupMapper mapper, String resourceName, String successMessage,
      String duplicateMessage, String serverError) {
    super(service, mapper, resourceName, successMessage, duplicateMessage, serverError);
    // TODO Auto-generated constructor stub
  }
}
