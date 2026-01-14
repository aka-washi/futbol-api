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
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.shared.BaseEntity;

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
    @AttributeOverride(name = "id", column = @Column(name = "og_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "og_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "og_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "og_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "og_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends BaseEntity {

    @NotBlank
    @Column(name = "og_name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "og_display_name", length = 100, nullable = false)
    private String displayName;

    @Column(name = "og_abbreviation", length = 20)
    private String abbreviation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Country country;

    @NotNull
    @Column(name = "og_founded", nullable = false)
    private LocalDate founded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_organization_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Organization parentOrganization;

    @Column(name = "og_logo")
    private String logo;

    @Column(name = "og_website")
    private String website;

    @Column(name = "og_headquarters")
    private String headquarters;

    @Column(name = "og_description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "og_type", nullable = false, length = 20)
    private OrganizationType type;

    @Override
    public int hashCode() {
        return Objects.hash(
            name,
            displayName,
            abbreviation,
            country,
            founded,
            parentOrganization,
            logo,
            website,
            headquarters,
            description,
            type
        );
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
            && Objects.equals(abbreviation, other.abbreviation)
            && Objects.equals(country, other.country)
            && Objects.equals(founded, other.founded)
            && Objects.equals(parentOrganization, other.parentOrganization)
            && Objects.equals(logo, other.logo)
            && Objects.equals(website, other.website)
            && Objects.equals(headquarters, other.headquarters)
            && Objects.equals(description, other.description)
            && Objects.equals(type, other.type);
    }

}
