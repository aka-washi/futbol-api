package com.eagle.futbolapi.features.seasonteam.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;

@Service
@Transactional
public class SeasonTeamService extends BaseCrudService<SeasonTeam, Long> {

}
