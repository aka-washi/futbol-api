package com.eagle.futbolapi.features.match.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.match.dto.MatchDTO;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.match.service.MatchService;

@RestController
@RequestMapping("/matches")
@Validated
public class MatchController extends BaseCrudController<Match, MatchDTO, MatchService, Object> {
}
