package com.eagle.futbolapi.features.teambrand.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.teambrand.dto.TeamBrandDto;
import com.eagle.futbolapi.features.teambrand.entity.TeamBrand;
import com.eagle.futbolapi.features.teambrand.mapper.TeamBrandMapper;
import com.eagle.futbolapi.features.teambrand.service.TeamBrandService;

@RestController
@RequestMapping("/team-brands")
public class TeamBrandController
    extends BaseCrudController<TeamBrand, TeamBrandDto, TeamBrandService, TeamBrandMapper> {

  private static final String RESOURCE_NAME = "Team Brand";
  private static final String SUCCESS_MESSAGE = "Team Brand(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Team Brand already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public TeamBrandController(TeamBrandService service, TeamBrandMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
