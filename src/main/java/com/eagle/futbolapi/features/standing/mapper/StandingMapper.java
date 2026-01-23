package com.eagle.futbolapi.features.standing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.standing.dto.StandingDTO;
import com.eagle.futbolapi.features.standing.entity.Standing;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StandingMapper extends BaseMapper<Standing, StandingDTO> {

}
