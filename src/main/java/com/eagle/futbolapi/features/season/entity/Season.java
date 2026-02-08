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
 * Stores information about football seasons, which are time periods
 * during which competitions are organized.
 * 
 * <p>
 * This entity enforces uniqueness constraints on both name and display name
 * to prevent duplicate season entries. The {@link UniqueField} annotation is
 * used
 * to enable automatic duplicate detection.
 * 
 * <p>
 * Column names follow the naming convention with 'ssn_' prefix.
 * All base entity fields (id, timestamps, audit fields) are mapped with
 * attribute overrides.
 * 
 * <p>
 * Examples of season names: "2023-2024", "2024/25", "Summer 2024"
 * 
 * @see BaseEntity
 * @see com.eagle.futbolapi.features.season.dto.SeasonDto
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

  /**
   * The official name of the season.
   * This field is required and must be unique across all seasons.
   * Typically represents the time period, e.g., "2023-2024" or "2024/25".
   */
  @NotBlank
  @UniqueField
  @Column(name = "ssn_name", nullable = false, length = 100)
  private String name;

  /**
   * The display name used for UI presentation.
   * This field is required and must be unique across all seasons.
   * Can be formatted differently from name for better readability.
   */
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
