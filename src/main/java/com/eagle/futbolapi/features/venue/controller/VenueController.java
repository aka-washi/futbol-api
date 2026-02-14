package com.eagle.futbolapi.features.venue.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.venue.dto.VenueDto;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.mapper.VenueMapper;
import com.eagle.futbolapi.features.venue.service.VenueService;

@Validated
@RestController
@RequestMapping("/venues")
public class VenueController extends BaseCrudController<Venue, VenueDto, VenueService, VenueMapper> {

  private static final String RESOURCE_NAME = "Venue";
  private static final String SUCCESS_MESSAGE = "Venue retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Venue already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected VenueController(VenueService service, VenueMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }
}
