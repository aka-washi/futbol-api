package com.eagle.futbolapi.features.seasonParticipation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.seasonParticipation.dto.SeasonParticipationDto;
import com.eagle.futbolapi.features.seasonParticipation.entity.SeasonParticipation;
import com.eagle.futbolapi.features.seasonParticipation.mapper.SeasonParticipationMapper;
import com.eagle.futbolapi.features.seasonParticipation.service.SeasonParticipationService;

@RestController
@RequestMapping("/season-participations")
public class SeasonParticipationController
    extends BaseCrudController<SeasonParticipation, SeasonParticipationDto,
        SeasonParticipationService, SeasonParticipationMapper> {

  private static final String RESOURCE_NAME = "Season Participation";
  private static final String SUCCESS_MESSAGE = "Season Participation(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Season Participation already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public SeasonParticipationController(SeasonParticipationService service,
      SeasonParticipationMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
