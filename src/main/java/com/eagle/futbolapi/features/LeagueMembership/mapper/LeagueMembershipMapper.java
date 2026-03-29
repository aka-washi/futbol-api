package com.eagle.futbolapi.features.LeagueMembership.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.LeagueMembership.dto.LeagueMembershipDto;
import com.eagle.futbolapi.features.LeagueMembership.entity.LeagueMembership;
import com.eagle.futbolapi.features.base.enums.MembershipStatus;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeagueMembershipMapper extends BaseMapper<LeagueMembership, LeagueMembershipDto> {

  @Mapping(target = "leagueId", source = "league.id")
  @Mapping(target = "leagueDisplayName", source = "league.displayName")
  @Mapping(target = "memberId", source = "member.id")
  @Mapping(target = "memberDisplayName", source = "member.displayName")
  @Mapping(target = "membershipStatus", expression = "java(membershipStatusToString(leagueMembership.getMembershipStatus()))")
  LeagueMembershipDto toDto(LeagueMembership leagueMembership);

  @Mapping(target = "league", ignore = true)
  @Mapping(target = "member", ignore = true)
  @Mapping(target = "membershipStatus", expression = "java(stringToMembershipStatus(leagueMembershipDto.getMembershipStatus()))")
  LeagueMembership toEntity(LeagueMembershipDto leagueMembershipDto);

  default String membershipStatusToString(MembershipStatus status) {
    return status != null ? status.getLabel() : null;
  }

  default MembershipStatus stringToMembershipStatus(String status) {
    return MembershipStatus.fromLabel(status);
  }
}
