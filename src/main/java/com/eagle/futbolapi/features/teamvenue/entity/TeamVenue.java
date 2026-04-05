package com.eagle.futbolapi.features.teamvenue.entity;

import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.VenueDesignation;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.venue.entity.Venue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "team_venue")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "tmv_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "tmv_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "tmv_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "tmv_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "tmv_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TeamVenue extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tmv_team_id")
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tmv_venue_id")
  private Venue venue;

  @Enumerated(EnumType.STRING)
  @Column(name = "tmv_designation", length = 50)
  private VenueDesignation designation;

  @Column(name = "tmv_start_date")
  private LocalDate startDate;

  @Column(name = "tmv_end_date")
  private LocalDate endDate;

}
