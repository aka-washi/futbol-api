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
  @NotBlank
  @UniqueField
  @Column(name = "cty_name", nullable = false, length = 100)
  private String name;
  @NotBlank
  @UniqueField
  @Column(name = "cty_display_name", length = 100, nullable = false)
  private String displayName;
  @UniqueField
  @Column(name = "cty_code", unique = true, length = 10)
  private String code;
  @NotBlank
  @UniqueField
  @Column(name = "cty_iso_code", unique = true, length = 3)
  private String isoCode;
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
