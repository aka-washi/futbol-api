package com.eagle.futbolapi.features.match.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.match.dto.MatchDTO;
import com.eagle.futbolapi.features.match.entity.Match;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchMapper extends BaseMapper<Match, MatchDTO> {

}
