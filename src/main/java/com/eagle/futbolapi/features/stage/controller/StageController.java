package com.eagle.futbolapi.features.stage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.stage.dto.StageDTO;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.mapper.StageMapper;
import com.eagle.futbolapi.features.stage.service.StageService;

@Validated
@RestController
@RequestMapping("/stages")
public class StageController extends BaseCrudController<Stage, StageDTO, StageService, StageMapper> {

  private static final String RESOURCE_NAME = "Stage";
  private static final String SUCCESS_MESSAGE = "Stage(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Stage already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public StageController(StageService stageService, StageMapper stageMapper) {
    super(
        stageService,
        stageMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<StageDTO>> getStageByName(@PathVariable String name) {
    Stage stage = service.getStageByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "name", name));
    StageDTO stageDTO = mapper.toDTO(stage);
    return ResponseUtil.success(stageDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<StageDTO>> getStageByDisplayName(@PathVariable String displayName) {
    Stage stage = service.getStageByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
    StageDTO stageDTO = mapper.toDTO(stage);
    return ResponseUtil.success(stageDTO, SUCCESS_MESSAGE);
  }

}
