package com.eagle.futbolapi.features.team.entity;

import java.time.LocalDate;
import java.util.Objects;

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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.AgeCategory;
import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.base.enums.TeamStatus;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.organization.entity.Organization;

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
        "tem_organization_id"
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
  @JoinColumn(name = "tem_organization_id", nullable = false)
  private Organization organization;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tem_country_id", nullable = false)
  private Country country;

  @Column(name = "tem_name", nullable = false)
  private String name;

  @Column(name = "tem_display_name", nullable = false, length = 100)
  private String displayName;

  @Enumerated(EnumType.STRING)
  @Column(name = "tem_gender", nullable = false, length = 10)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  @Column(name = "tem_age_category", nullable = false, length = 20)
  private AgeCategory ageCategory;

  @NotNull
  @Column(name = "tem_founded", nullable = false)
  private LocalDate founded;

  @Enumerated(EnumType.STRING)
  @Column(name = "tem_status", length = 20)
  private TeamStatus status;

  @Override
  public int hashCode() {
    return Objects.hash(
        organization,
        country,
        name,
        displayName,
        gender,
        ageCategory,
        founded,
        status);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Team))
      return false;
    Team other = (Team) obj;
    return Objects.equals(organization, other.organization)
        && Objects.equals(country, other.country)
        && Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(gender, other.gender)
        && Objects.equals(ageCategory, other.ageCategory)
        && Objects.equals(founded, other.founded)
        && Objects.equals(status, other.status);
  }
}
