package com.eagle.futbolapi.features.matchevent.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.matchevent.dto.MatchEventDTO;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.matchevent.service.MatchEventService;

@RestController
@RequestMapping("/match-events")
@Validated
public class MatchEventController extends BaseCrudController<MatchEvent, MatchEventDTO, MatchEventService, Object> {

}
