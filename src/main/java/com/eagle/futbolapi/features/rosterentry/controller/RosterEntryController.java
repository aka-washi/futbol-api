package com.eagle.futbolapi.features.rosterentry.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.rosterentry.dto.RosterEntryDTO;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;
import com.eagle.futbolapi.features.rosterentry.service.RosterEntryService;

@RestController
@RequestMapping("/roster-entries")
@Validated
public class RosterEntryController extends BaseCrudController<RosterEntry, RosterEntryDTO, RosterEntryService, Object> {

}
