package com.eagle.futbolapi.features.matchevent.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.matchevent.dto.MatchEventDTO;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.matchevent.service.MatchEventService;
import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/match-events")
@Validated
public class MatchEventController extends BaseCrudController<MatchEvent, MatchEventDTO, MatchEventService, Object> {

}
