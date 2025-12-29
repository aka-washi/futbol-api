package com.eagle.futbolapi.features.rosterentry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;

public interface RosterEntryRepository extends JpaRepository<RosterEntry, Long> {

}
