package com.eagle.futbolapi.features.LeagueMembership.controller;

import com.eagle.futbolapi.features.LeagueMembership.dto.LeagueMembershipDto;
import com.eagle.futbolapi.features.LeagueMembership.entity.LeagueMembership;
import com.eagle.futbolapi.features.LeagueMembership.mapper.LeagueMembershipMapper;
import com.eagle.futbolapi.features.LeagueMembership.service.LeagueMembershipService;
import com.eagle.futbolapi.features.base.controller.BaseCrudController;

public class LeagueMembershipController
    extends BaseCrudController<LeagueMembership, LeagueMembershipDto, LeagueMembershipService, LeagueMembershipMapper> {

  private static final String RESOURCE_NAME = "League Membership";
  private static final String SUCCESS_MESSAGE = "League Membership(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "League Membership already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public LeagueMembershipController(LeagueMembershipService leagueMembershipService,
      LeagueMembershipMapper leagueMembershipMapper) {
    super(
        leagueMembershipService,
        leagueMembershipMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
