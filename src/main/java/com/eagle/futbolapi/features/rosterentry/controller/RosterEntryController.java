package com.eagle.futbolapi.features.rosterentry.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.rosterentry.dto.RosterEntryDTO;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;
import com.eagle.futbolapi.features.rosterentry.mapper.RosterEntryMapper;
import com.eagle.futbolapi.features.rosterentry.service.RosterEntryService;

@RestController
@RequestMapping("/roster-entries")
@Validated
public class RosterEntryController
    extends BaseCrudController<RosterEntry, RosterEntryDTO, RosterEntryService, RosterEntryMapper> {

  protected RosterEntryController(RosterEntryService service, RosterEntryMapper mapper, String resourceName,
      String successMessage, String duplicateMessage, String serverError) {
    super(service, mapper, resourceName, successMessage, duplicateMessage, serverError);
    // TODO Auto-generated constructor stub
  }

}
