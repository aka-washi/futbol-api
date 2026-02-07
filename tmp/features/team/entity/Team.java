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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.AgeCategory;
import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.venue.entity.Venue;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
        "tm_name",
        "tm_gender",
        "tm_age_category",
        "organization_id"
    })
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "tm_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "tm_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "tm_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "tm_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "tm_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Team extends BaseEntity {

  @NotBlank
  @Column(name = "tm_name", nullable = false)
  private String name;

  @NotBlank
  @Column(name = "tm_display_name", nullable = false, length = 100)
  private String displayName;

  @NotBlank
  @Size(min = 3, max = 5)
  @Column(name = "tm_code", nullable = false, length = 5)
  private String code; // 3-letter code (e.g., "AME" for América)

  @Enumerated(EnumType.STRING)
  @Column(name = "tm_gender", nullable = false, length = 10)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  @Column(name = "tm_age_category", nullable = false, length = 20)
  private AgeCategory ageCategory;

  @NotNull
  @Column(name = "tm_founded", nullable = false)
  private LocalDate founded;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Organization organization;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Country country;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "venue_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Venue venue;

  @Column(name = "tm_logo")
  private String logo;

  @Column(name = "tm_primary_color", length = 7)
  private String primaryColor;

  @Column(name = "tm_secondary_color", length = 7)
  private String secondaryColor;

  @Column(name = "tm_website")
  private String website;

  @Column(name = "tm_active", nullable = false)
  @Builder.Default
  private Boolean active = true;

  @Override
  public int hashCode() {
    return Objects.hash(name, displayName, gender, ageCategory, founded, country);
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
        && Objects.equals(gender, other.gender)
        && Objects.equals(ageCategory, other.ageCategory)
        && Objects.equals(founded, other.founded)
        && Objects.equals(country, other.country);
  }

}
