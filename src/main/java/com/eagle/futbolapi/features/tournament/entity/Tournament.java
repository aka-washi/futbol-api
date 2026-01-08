package com.eagle.futbolapi.features.tournament.entity;

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

import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.shared.BaseEntity;

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
    @AttributeOverride(name = "id", column = @Column(name = "tn_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "tn_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "tn_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "tn_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "tn_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Tournament extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Organization organization;

  @NotBlank
  @Column(name = "tn_name", nullable = false)
  private String name;

  @NotBlank
  @Column(name = "tn_display_name", nullable = false, length = 150)
  private String displayName;

  @Enumerated(EnumType.STRING)
  @Column(name = "tn_type", nullable = false, length = 50)
  @NotNull
  private TournamentType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "tn_category", nullable = false, length = 20)
  @NotNull
  private Category category;

  @Column(name = "tn_level", nullable = false)
  private Integer level; // 1 = First Division, 2 = Second Division, etc.

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "relegation_to_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Tournament relegationTo;

  @Column(name = "tn_description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "tn_logo")
  private String logo;

  @Column(name = "tn_founded_year", nullable = false)
  private Integer foundedYear;

  @Column(name = "to_active", nullable = false)
  @Builder.Default
  private Boolean active = true;

}
