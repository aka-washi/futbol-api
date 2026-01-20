package com.eagle.futbolapi.features.stage.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.stage.dto.StageDTO;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.service.StageService;

@RestController
@RequestMapping("/stages")
@Validated
public class StageController extends BaseCrudController<Stage, StageDTO, StageService, Object> {

}
