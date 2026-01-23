package com.eagle.futbolapi.features.player.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.player.dto.PlayerDTO;
import com.eagle.futbolapi.features.player.entity.Player;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper extends BaseMapper<Player, PlayerDTO> {

}
