package com.eagle.futbolapi.features.rosterentry.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.rosterentry.dto.RosterEntryDTO;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RosterEntryMapper extends BaseMapper<RosterEntry, RosterEntryDTO> {

}
