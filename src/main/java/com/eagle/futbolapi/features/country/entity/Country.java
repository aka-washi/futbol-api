package com.eagle.futbolapi.features.country.entity;

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
 * Entity class representing a Country in the football database.
 * Stores information about countries including their names, codes, and flag
 * URLs.
 * 
 * <p>
 * This entity enforces uniqueness constraints on name, display name, code, and
 * ISO code
 * to prevent duplicate country entries. The {@link UniqueField} annotation is
 * used
 * to enable automatic duplicate detection.
 * 
 * <p>
 * Column names follow the naming convention with 'cty_' prefix.
 * All base entity fields (id, timestamps, audit fields) are mapped with
 * attribute overrides.
 * 
 * @see BaseEntity
 * @see com.eagle.futbolapi.features.country.dto.CountryDto
 */
@Getter
@Setter
@Accessors(chain = false)
@Entity
@Table(name = "country")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "cty_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cty_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "cty_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "cty_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "cty_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Country extends BaseEntity {

  /**
   * The official name of the country.
   * This field is required and must be unique across all countries.
   */
  @NotBlank
  @UniqueField
  @Column(name = "cty_name", nullable = false, length = 100)
  private String name;

  /**
   * The display name used for UI presentation.
   * This field is required and must be unique across all countries.
   */
  @NotBlank
  @UniqueField
  @Column(name = "cty_display_name", length = 100, nullable = false)
  private String displayName;

  /**
   * Short country code (e.g., "US", "GB").
   * This field is optional but must be unique if provided.
   */
  @UniqueField
  @Column(name = "cty_code", unique = true, length = 10)
  private String code;

  /**
   * ISO 3166-1 alpha-3 country code (e.g., "USA", "GBR").
   * This field is required and must be unique across all countries.
   */
  @NotBlank
  @UniqueField
  @Column(name = "cty_iso_code", unique = true, length = 3)
  private String isoCode;

  /**
   * URL to the country's flag image.
   * This field is optional.
   */
  @Column(name = "cty_flag_url", length = 500)
  private String flagUrl;

  @Override
  public int hashCode() {
    return Objects.hash(name, code, isoCode, displayName, flagUrl);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Country))
      return false;
    Country other = (Country) obj;
    return Objects.equals(name, other.name)
        && Objects.equals(code, other.code)
        && Objects.equals(isoCode, other.isoCode)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(flagUrl, other.flagUrl);
  }

}
