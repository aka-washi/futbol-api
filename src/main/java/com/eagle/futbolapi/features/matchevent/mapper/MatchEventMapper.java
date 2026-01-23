package com.eagle.futbolapi.features.matchevent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.matchevent.dto.MatchEventDTO;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchEventMapper extends BaseMapper<MatchEvent, MatchEventDTO> {

}
