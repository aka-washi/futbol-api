package com.eagle.futbolapi.features.rosterentry.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;

@Service
@Transactional
public class RosterEntryService extends BaseCrudService<RosterEntry, Long> {

}
