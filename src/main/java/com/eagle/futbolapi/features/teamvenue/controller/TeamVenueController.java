package com.eagle.futbolapi.features.teamvenue.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.teamvenue.dto.TeamVenueDto;
import com.eagle.futbolapi.features.teamvenue.entity.TeamVenue;
import com.eagle.futbolapi.features.teamvenue.mapper.TeamVenueMapper;
import com.eagle.futbolapi.features.teamvenue.service.TeamVenueService;

@RestController
@RequestMapping("/team-venues")
public class TeamVenueController
    extends BaseCrudController<TeamVenue, TeamVenueDto, TeamVenueService, TeamVenueMapper> {

  private static final String RESOURCE_NAME = "Team Venue";
  private static final String SUCCESS_MESSAGE = "Team Venue(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Team Venue already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public TeamVenueController(TeamVenueService service, TeamVenueMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
