package com.eagle.futbolapi.features.team.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.AgeCategory;
import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.base.enums.TeamStatus;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.organization.entity.Organization;
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
@Table(name = "team", uniqueConstraints = {
    @UniqueConstraint(name = "uk_team_natural", columnNames = {
        "tem_name",
        "tem_gender",
        "tem_age_category",
        "organization_id"
    })
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "tem_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "tem_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "tem_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "tem_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "tem_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Team extends BaseEntity {

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Organization organization;

  @Column(name = "tem_name", nullable = false)
  private String name;

  @Column(name = "tem_display_name", nullable = false, length = 100)
  private String displayName;

  @Column(name = "tem_abbreviation", length = 10)
  private String abbreviation;

  @Enumerated(EnumType.STRING)
  @Column(name = "tem_gender", nullable = false, length = 10)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  @Column(name = "tem_age_category", nullable = false, length = 20)
  private AgeCategory ageCategory;

  @NotNull
  @Column(name = "tem_founded", nullable = false)
  private LocalDate founded;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Country country;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "venue_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Venue venue;

  @Column(name = "tem_logo")
  private String logo;

  @Column(name = "tem_primary_color", length = 20)
  private String primaryColor;

  @Column(name = "tem_secondary_color", length = 20)
  private String secondaryColor;

  @Column(name = "tem_website", length = 100)
  private String website;

  @Enumerated(EnumType.STRING)
  @Column(name = "tem_status", length = 20)
  private TeamStatus status;

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        displayName,
        abbreviation,
        gender,
        ageCategory,
        founded,
        country,
        venue,
        logo,
        primaryColor,
        secondaryColor,
        website,
        status);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Team))
      return false;
    Team other = (Team) obj;
    return Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(abbreviation, other.abbreviation)
        && Objects.equals(gender, other.gender)
        && Objects.equals(ageCategory, other.ageCategory)
        && Objects.equals(founded, other.founded)
        && Objects.equals(country, other.country)
        && Objects.equals(venue, other.venue)
        && Objects.equals(logo, other.logo)
        && Objects.equals(primaryColor, other.primaryColor)
        && Objects.equals(secondaryColor, other.secondaryColor)
        && Objects.equals(website, other.website)
        && Objects.equals(status, other.status);
  }
}
