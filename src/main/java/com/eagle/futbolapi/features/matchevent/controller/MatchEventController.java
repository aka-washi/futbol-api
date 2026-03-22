package com.eagle.futbolapi.features.matchevent.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.matchevent.dto.MatchEventDto;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.matchevent.mapper.MatchEventMapper;
import com.eagle.futbolapi.features.matchevent.service.MatchEventService;

@Validated
@RestController
@RequestMapping("/match-events")
public class MatchEventController
    extends BaseCrudController<MatchEvent, MatchEventDto, MatchEventService, MatchEventMapper> {

  private static final String RESOURCE_NAME = "MatchEvent";
  private static final String SUCCESS_MESSAGE = "Match Event(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Match Event already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public MatchEventController(MatchEventService matchEventService, MatchEventMapper matchEventMapper) {
    super(
        matchEventService,
        matchEventMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
