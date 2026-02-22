package com.eagle.futbolapi.features.lineupMember.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.lineupMember.entity.LineupMember;

/**
 * Repository interface for LineupMember entity.
 */
@Repository
public interface LineupMemberRepository extends BaseRepository<LineupMember, Long> {
}
