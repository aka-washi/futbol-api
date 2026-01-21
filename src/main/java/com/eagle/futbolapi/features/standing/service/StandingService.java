package com.eagle.futbolapi.features.standing.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.standing.entity.Standing;

@Service
@Transactional
public class StandingService extends BaseCrudService<Standing, Long> {

}
