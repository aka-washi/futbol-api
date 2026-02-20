package com.eagle.futbolapi.features.tournament.entity;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.AgeCategory;
import com.eagle.futbolapi.features.base.enums.TournamentType;
import com.eagle.futbolapi.features.organization.entity.Organization;

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
@Table(name = "tournament")
@AttributeOverrides({
  @AttributeOverride(name = "id", column = @Column(name = "trn_id")),
  @AttributeOverride(name = "createdAt", column = @Column(name = "trn_created_at", nullable = false, updatable = false)),
  @AttributeOverride(name = "createdBy", column = @Column(name = "trn_created_by", length = 100, updatable = false)),
  @AttributeOverride(name = "updatedAt", column = @Column(name = "trn_updated_at")),
  @AttributeOverride(name = "updatedBy", column = @Column(name = "trn_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Tournament extends BaseEntity {

  @UniqueField(fieldPath = "organization.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Organization organization;

  @NotBlank
  @UniqueField
  @Column(name = "trn_name", nullable = false)
  private String name;

  @NotBlank
  @UniqueField
  @Column(name = "trn_display_name", nullable = false, length = 150)
  private String displayName;

  @Enumerated(EnumType.STRING)
  @UniqueField
  @Column(name = "trn_type", nullable = false, length = 50)
  @NotNull
  private TournamentType type;

  @Enumerated(EnumType.STRING)
  @UniqueField
  @Column(name = "trn_category", nullable = false, length = 20)
  @NotNull
  private AgeCategory ageCategory;

  @Column(name = "trn_level")
  private Integer level; // 1 = First Division, 2 = Second Division, etc.

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "relegation_to_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Tournament relegationTo;

  @Column(name = "trn_description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "trn_logo")
  private String logo;

  @Column(name = "trn_founded_year")
  private Integer foundedYear;

  @Column(name = "trn_website")
  private String website;

  @Column(name = "trn_active", nullable = false)
  @Builder.Default
  private Boolean active = true;

  @Override
  public int hashCode() {
    return Objects.hash(
        organization,
        name,
        displayName,
        type,
        ageCategory,
        level,
        relegationTo,
        description,
        logo,
        foundedYear,
        website,
        active);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Tournament))
      return false;
    Tournament other = (Tournament) obj;
    return Objects.equals(organization, other.organization)
        && Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(type, other.type)
        && Objects.equals(ageCategory, other.ageCategory)
        && Objects.equals(level, other.level)
        && Objects.equals(relegationTo, other.relegationTo)
        && Objects.equals(description, other.description)
        && Objects.equals(logo, other.logo)
        && Objects.equals(foundedYear, other.foundedYear)
        && Objects.equals(website, other.website)
        && Objects.equals(active, other.active);
  }

}
