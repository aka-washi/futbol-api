package com.eagle.futbolapi.features.stage.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.stage.entity.Stage;

@Service
@Transactional
public class StageService extends BaseCrudService<Stage, Long> {

}
