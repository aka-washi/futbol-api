package com.eagle.futbolapi.features.season.entity;

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

import com.eagle.futbolapi.features.shared.BaseEntity;
import com.eagle.futbolapi.features.tournament.entity.Tournament;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Season Entity
 * Represents a football season (e.g., 2025-2026)
 */
@Entity
@Table(name = "season")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "sn_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "sn_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "sn_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "sn_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "sn_updated_by", length = 100))
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Season extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotNull
    private Tournament tournament;

    @NotBlank
    @Column(name = "sn_name", nullable = false, length = 100)
    private String name; // e.g., "2025-2026"

    @NotBlank
    @Column(name = "sn_display_name", length = 100, nullable = false)
    private String displayName;

    @NotNull
    @Column(name = "sn_start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "sn_end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "sn_is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = false;

    @Column(name = "sn_has_relegation", nullable = false)
    @Builder.Default
    private Boolean hasRelegation = true;

    @Column(name = "sn_description", columnDefinition = "TEXT")
    private String description;

}
