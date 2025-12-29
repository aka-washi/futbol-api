package com.eagle.futbolapi.features.organization.entity;

import java.time.LocalDate;

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
import lombok.experimental.SuperBuilder;


/**
 * Organization Entity
 * Represents a football federation/organization (e.g., FMF - Federación
 * Mexicana de Fútbol)
 */
@Entity
@Table(name = "organization")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "og_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "og_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "og_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "og_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "og_updated_by", length = 100))
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
    @JoinColumn(name = "country_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
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
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((founded == null) ? 0 : founded.hashCode());
        result = prime * result + ((parentOrganization == null) ? 0 : parentOrganization.hashCode());
        result = prime * result + ((logo == null) ? 0 : logo.hashCode());
        result = prime * result + ((website == null) ? 0 : website.hashCode());
        result = prime * result + ((headquarters == null) ? 0 : headquarters.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Organization other = (Organization) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (displayName == null) {
            if (other.displayName != null)
                return false;
        } else if (!displayName.equals(other.displayName))
            return false;
        if (abbreviation == null) {
            if (other.abbreviation != null)
                return false;
        } else if (!abbreviation.equals(other.abbreviation))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (founded == null) {
            if (other.founded != null)
                return false;
        } else if (!founded.equals(other.founded))
            return false;
        if (parentOrganization == null) {
            if (other.parentOrganization != null)
                return false;
        } else if (!parentOrganization.equals(other.parentOrganization))
            return false;
        if (logo == null) {
            if (other.logo != null)
                return false;
        } else if (!logo.equals(other.logo))
            return false;
        if (website == null) {
            if (other.website != null)
                return false;
        } else if (!website.equals(other.website))
            return false;
        if (headquarters == null) {
            if (other.headquarters != null)
                return false;
        } else if (!headquarters.equals(other.headquarters))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return type == other.type;
    }

}
