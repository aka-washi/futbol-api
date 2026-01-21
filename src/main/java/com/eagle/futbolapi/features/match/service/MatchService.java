package com.eagle.futbolapi.features.match.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.match.entity.Match;

@Service
@Transactional
public class MatchService extends BaseCrudService<Match, Long> {

}
