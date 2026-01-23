package com.eagle.futbolapi.features.seasonteam.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.seasonteam.dto.SeasonTeamDTO;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;
import com.eagle.futbolapi.features.seasonteam.mapper.SeasonTeamMapper;
import com.eagle.futbolapi.features.seasonteam.service.SeasonTeamService;

@RestController
@RequestMapping("/season-teams")
@Validated
public class SeasonTeamController
    extends BaseCrudController<SeasonTeam, SeasonTeamDTO, SeasonTeamService, SeasonTeamMapper> {

  protected SeasonTeamController(SeasonTeamService service, SeasonTeamMapper mapper, String resourceName,
      String successMessage, String duplicateMessage, String serverError) {
    super(service, mapper, resourceName, successMessage, duplicateMessage, serverError);
    // TODO Auto-generated constructor stub
  }

}
