package com.eagle.futbolapi.features.season.entity;

import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Entity class representing a Season in the football database.
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "season")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ssn_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "ssn_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "ssn_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "ssn_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "ssn_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Season extends BaseEntity {
  @NotBlank
  @UniqueField
  @Column(name = "ssn_name", nullable = false, length = 100)
  private String name;
  @NotBlank
  @UniqueField
  @Column(name = "ssn_display_name", length = 100, nullable = false)
  private String displayName;

  @Override
  public int hashCode() {
    return Objects.hash(name, displayName);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Season other = (Season) obj;
    return Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName);
  }
}
