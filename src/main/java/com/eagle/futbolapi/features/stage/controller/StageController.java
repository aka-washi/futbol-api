package com.eagle.futbolapi.features.stage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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


@RestController
@RequestMapping("/stages")
@Validated
public class StageController extends BaseCrudController<Stage, StageDTO, StageService, StageMapper> {

  private static final String RESOURCE_NAME = "Stage";
  private static final String SUCCESS_MESSAGE = "Stage retrieved successfully";
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
  public ResponseEntity<ApiResponse<StageDTO>> getStageByName(String name) {
    Stage stage = service.getStageByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "name", name));
    StageDTO stageDTO = mapper.toDTO(stage);
    return ResponseUtil.success(stageDTO, successMessage);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<StageDTO>> getStageByDisplayName(String displayName) {
    Stage stage = service.getStageByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "displayName", displayName));
    StageDTO stageDTO = mapper.toDTO(stage);
    return ResponseUtil.success(stageDTO, successMessage);
  }

}
