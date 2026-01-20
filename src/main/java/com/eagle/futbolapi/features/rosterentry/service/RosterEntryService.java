package com.eagle.futbolapi.features.rosterentry.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;
import com.eagle.futbolapi.features.rosterentry.repository.RosterEntryRepository;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;

@Service
@Transactional
public class RosterEntryService extends BaseCrudService<RosterEntry, Long> {

}
