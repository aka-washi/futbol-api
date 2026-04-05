package com.eagle.futbolapi.features.teambrand.entity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.team.entity.Team;

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
@Table(name = "team_brand")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "tbr_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "tbr_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "tbr_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "tbr_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "tbr_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TeamBrand extends BaseEntity {

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tbr_team_id", nullable = false)
  private Team team;

  @Column(name = "tbr_name", length = 100)
  private String name;
  @Column(name = "tbr_display_name", length = 50)
  private String displayName;
  @ElementCollection
  @CollectionTable(name = "team_nicknames", joinColumns = @JoinColumn(name = "tbr_team_brand_id"))
  @Column(name = "tbr_nickname", length = 100)
  private Set<String> nicknames;
  @Column(name = "tbr_abbreviation", length = 10)
  private String abbreviation;
  @Column(name = "tbr_primary_color", length = 7)
  private String primaryColor;
  @Column(name = "tbr_secondary_color", length = 7)
  private String secondaryColor;
  @Column(name = "tbr_logo_url", length = 255)
  private String logoUrl;
  @Column(name = "tbr_website_url", length = 255)
  private String websiteUrl;
  @Column(name = "tbr_start_date")
  private LocalDate startDate;
  @Column(name = "tbr_end_date")
  private LocalDate endDate;

  @Override
  public int hashCode() {
    return Objects.hash(
        team,
        name,
        displayName,
        nicknames,
        abbreviation,
        primaryColor,
        secondaryColor,
        logoUrl,
        websiteUrl,
        startDate,
        endDate);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof TeamBrand))
      return false;
    TeamBrand other = (TeamBrand) obj;
    return Objects.equals(team, other.team)
        && Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(nicknames, other.nicknames)
        && Objects.equals(abbreviation, other.abbreviation)
        && Objects.equals(primaryColor, other.primaryColor)
        && Objects.equals(secondaryColor, other.secondaryColor)
        && Objects.equals(logoUrl, other.logoUrl)
        && Objects.equals(websiteUrl, other.websiteUrl)
        && Objects.equals(startDate, other.startDate)
        && Objects.equals(endDate, other.endDate);
  }
}
