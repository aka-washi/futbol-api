package com.eagle.futbolapi.features.competition.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.competition.dto.CompetitionDTO;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.season.repository.SeasonRepository;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompetitionMapper {

  Competition toEntity(CompetitionDTO dto);

  CompetitionDTO toDTO(Competition entity);
}
