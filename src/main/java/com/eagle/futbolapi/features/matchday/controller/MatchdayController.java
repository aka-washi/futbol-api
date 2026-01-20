package com.eagle.futbolapi.features.matchday.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.matchday.dto.MatchdayDTO;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.service.MatchdayService;
import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/matchdays")
@Validated
public class MatchdayController extends BaseCrudController<Matchday, MatchdayDTO, MatchdayService, Object> {

}
