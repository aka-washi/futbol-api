package com.eagle.futbolapi.features.lineup.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.lineup.entity.Lineup;

@Service
@Transactional
public class LineupService extends BaseCrudService<Lineup, Long> {
}
