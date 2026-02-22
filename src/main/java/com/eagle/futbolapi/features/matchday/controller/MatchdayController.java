package com.eagle.futbolapi.features.matchday.controller;

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
import com.eagle.futbolapi.features.matchday.dto.MatchdayDto;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.mapper.MatchdayMapper;
import com.eagle.futbolapi.features.matchday.service.MatchdayService;

@Validated
@RestController
@RequestMapping("/matchdays")
public class MatchdayController extends BaseCrudController<Matchday, MatchdayDto, MatchdayService, MatchdayMapper> {

  private static final String RESOURCE_NAME = "Matchday";
  private static final String SUCCESS_MESSAGE = "Matchday(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Matchday already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public MatchdayController(MatchdayService matchdayService, MatchdayMapper matchdayMapper) {
    super(
        matchdayService,
        matchdayMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<MatchdayDto>> getMatchdayByName(@PathVariable String name) {
    Matchday matchday = service.getByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "name", name));
    MatchdayDto matchdayDto = mapper.toDto(matchday);
    return ResponseUtil.success(matchdayDto, SUCCESS_MESSAGE);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<MatchdayDto>> getMatchdayByDisplayName(@PathVariable String displayName) {
    Matchday matchday = service.getByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
    MatchdayDto matchdayDto = mapper.toDto(matchday);
    return ResponseUtil.success(matchdayDto, SUCCESS_MESSAGE);
  }

}
