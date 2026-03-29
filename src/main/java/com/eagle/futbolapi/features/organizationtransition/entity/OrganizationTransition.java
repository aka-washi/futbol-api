package com.eagle.futbolapi.features.organizationtransition.entity;

import java.time.LocalDate;
import java.util.Objects;

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
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.TransitionType;
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
@Table(name = "organization_transition")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ogt_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "ogt_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "ogt_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "ogt_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "ogt_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationTransition extends BaseEntity {

  @NotNull
  @UniqueField(fieldPath = "fromOrganization.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Organization fromOrganization;
  @NotNull
  @UniqueField(fieldPath = "toOrganization.id")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Organization toOrganization;

  @UniqueField
  @Column(name = "ogt_type")
  private TransitionType transitionType;

  @UniqueField
  @Column(name = "ogt_effective_date")
  private LocalDate effectiveDate;

  @Override
  public int hashCode() {
    return Objects.hash(fromOrganization, toOrganization, transitionType, effectiveDate);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof OrganizationTransition))
      return false;
    OrganizationTransition other = (OrganizationTransition) obj;
    return Objects.equals(fromOrganization, other.fromOrganization)
        && Objects.equals(toOrganization, other.toOrganization)
        && Objects.equals(transitionType, other.transitionType)
        && Objects.equals(effectiveDate, other.effectiveDate);
  }
}
