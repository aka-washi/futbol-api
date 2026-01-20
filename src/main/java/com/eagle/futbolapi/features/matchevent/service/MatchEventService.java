package com.eagle.futbolapi.features.matchevent.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.matchevent.repository.MatchEventRepository;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;

@Service
@Transactional
public class MatchEventService extends BaseCrudService<MatchEvent, Long> {

}
