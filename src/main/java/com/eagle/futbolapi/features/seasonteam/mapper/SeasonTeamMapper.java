package com.eagle.futbolapi.features.seasonteam.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.seasonteam.dto.SeasonTeamDTO;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonTeamMapper extends BaseMapper<SeasonTeam, SeasonTeamDTO> {

}
