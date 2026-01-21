package com.eagle.futbolapi.features.lineup.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.lineup.dto.LineupDTO;
import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.service.LineupService;

@RestController
@RequestMapping("/lineups")
@Validated
public class LineupController extends BaseCrudController<Lineup, LineupDTO, LineupService, Object> {
}
