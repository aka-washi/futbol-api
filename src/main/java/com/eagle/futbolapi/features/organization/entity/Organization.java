package com.eagle.futbolapi.features.organization.entity;

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
import jakarta.validation.constraints.NotBlank;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.OrganizationType;
import com.eagle.futbolapi.features.country.entity.Country;

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
@Table(name = "organization")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "org_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "org_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "org_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "org_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "org_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends BaseEntity {

  @NotBlank
  @UniqueField
  @Column(name = "org_name", nullable = false, length = 100)
  private String name;

  @NotBlank
  @UniqueField
  @Column(name = "org_display_name", length = 100, nullable = false)
  private String displayName;

  @Enumerated(EnumType.STRING)
  @Column(name = "org_type", nullable = false, length = 25)
  private OrganizationType type;

  @NotBlank
  @UniqueField
  @Column(name = "org_abbreviation", length = 20, nullable = false)
  private String abbreviation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Country country;

  @Column(name = "org_founded")
  private LocalDate founded;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Organization parentOrganization;

  @Column(name = "org_logo")
  private String logo;

  @Column(name = "org_website")
  private String website;
  @Column(name = "org_headquarters")
  private String headquarters;
  @Column(name = "org_description", length = 500)
  private String description;

  @Column(name = "org_start_date")
  private LocalDate startDate;
  @Column(name = "org_end_date")
  private LocalDate endDate;


  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        displayName,
        type,
        abbreviation,
        country,
        founded,
        parentOrganization,
        logo,
        website,
        headquarters,
        description,
        startDate,
        endDate);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Organization))
      return false;
    Organization other = (Organization) obj;
    return Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(type, other.type)
        && Objects.equals(abbreviation, other.abbreviation)
        && Objects.equals(country, other.country)
        && Objects.equals(founded, other.founded)
        && Objects.equals(parentOrganization, other.parentOrganization)
        && Objects.equals(logo, other.logo)
        && Objects.equals(website, other.website)
        && Objects.equals(headquarters, other.headquarters)
        && Objects.equals(description, other.description)
        && Objects.equals(startDate, other.startDate)
        && Objects.equals(endDate, other.endDate);
  }

}
