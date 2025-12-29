package com.eagle.futbolapi.features.team.entity;

import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.shared.BaseEntity;
import com.eagle.futbolapi.features.venue.entity.Venue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Team Entity
 * Represents a football team/club
 */
@Entity
@Table(name = "team")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "tm_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "tm_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "tm_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "tm_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "tm_updated_by", length = 100))
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team extends BaseEntity {

    @NotBlank
    @Column(name = "tm_name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "tm_display_name", nullable = false, length = 100)
    private String displayName;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "tm_code", nullable = false, unique = true, length = 3)
    private String code; // 3-letter code (e.g., "AME" for América)

    @NotNull
    @Column(name = "tm_founded", nullable = false)
    private LocalDate founded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_venue_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Venue homeVenue;

    @Column(name = "tm_logo")
    private String logo;

    @Column(name = "tm_primary_color", length = 7)
    private String primaryColor;

    @Column(name = "tm_secondary_color", length = 7)
    private String secondaryColor;

    @Column(name = "tm_website")
    private String website;

    @Column(name = "tm_is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

}
