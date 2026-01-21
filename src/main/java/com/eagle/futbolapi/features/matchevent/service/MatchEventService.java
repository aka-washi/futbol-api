package com.eagle.futbolapi.features.matchevent.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;

@Service
@Transactional
public class MatchEventService extends BaseCrudService<MatchEvent, Long> {

}
