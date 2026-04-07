package com.eagle.futbolapi.features.leaguemembership.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.leaguemembership.dto.LeagueMembershipDto;
import com.eagle.futbolapi.features.leaguemembership.entity.LeagueMembership;
import com.eagle.futbolapi.features.leaguemembership.mapper.LeagueMembershipMapper;
import com.eagle.futbolapi.features.leaguemembership.service.LeagueMembershipService;

@RestController
@RequestMapping("/league-memberships")
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
