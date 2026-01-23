package com.eagle.futbolapi.features.lineup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.lineup.dto.LineupDTO;
import com.eagle.futbolapi.features.lineup.entity.Lineup;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LineupMapper extends BaseMapper<Lineup, LineupDTO> {

}
