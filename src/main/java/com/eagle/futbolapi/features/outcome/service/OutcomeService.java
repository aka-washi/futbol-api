package com.eagle.futbolapi.features.outcome.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.outcome.entity.Outcome;

@Service
@Transactional
public class OutcomeService extends BaseCrudService<Outcome, Long> {

}
