package com.eagle.futbolapi.features.outcome.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.outcome.dto.OutcomeDTO;
import com.eagle.futbolapi.features.outcome.entity.Outcome;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutcomeMapper extends BaseMapper<Outcome, OutcomeDTO> {

}
