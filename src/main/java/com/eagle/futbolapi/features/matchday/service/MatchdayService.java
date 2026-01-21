package com.eagle.futbolapi.features.matchday.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.matchday.entity.Matchday;

@Service
@Transactional
public class MatchdayService extends BaseCrudService<Matchday, Long> {

}
