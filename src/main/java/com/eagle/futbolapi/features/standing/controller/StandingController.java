package com.eagle.futbolapi.features.standing.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.standing.dto.StandingDTO;
import com.eagle.futbolapi.features.standing.entity.Standing;
import com.eagle.futbolapi.features.standing.service.StandingService;

@RestController
@RequestMapping("/standings")
@Validated
public class StandingController extends BaseCrudController<Standing, StandingDTO, StandingService, Object> {

}
