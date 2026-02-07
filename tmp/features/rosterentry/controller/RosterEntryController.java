package com.eagle.futbolapi.features.rosterentry.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.rosterentry.dto.RosterEntryDTO;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;
import com.eagle.futbolapi.features.rosterentry.mapper.RosterEntryMapper;
import com.eagle.futbolapi.features.rosterentry.service.RosterEntryService;

@Validated
@RestController
@RequestMapping("/roster-entries")
public class RosterEntryController
    extends BaseCrudController<RosterEntry, RosterEntryDTO, RosterEntryService, RosterEntryMapper> {

  private static final String RESOURCE_NAME = "RosterEntry";
  private static final String SUCCESS_MESSAGE = "Roster Entry(ies) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Roster Entry already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public RosterEntryController(RosterEntryService rosterEntryService, RosterEntryMapper rosterEntryMapper) {
    super(
        rosterEntryService,
        rosterEntryMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
