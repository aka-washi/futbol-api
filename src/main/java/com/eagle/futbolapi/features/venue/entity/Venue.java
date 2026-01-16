package com.eagle.futbolapi.features.venue.entity;

import java.time.LocalDate;
import java.util.Objects;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.country.entity.Country;

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
@Table(name = "venue")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "vn_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "vn_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "vn_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "vn_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "vn_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Venue extends BaseEntity {

  @NotBlank
  @Column(name = "vn_name", nullable = false)
  private String name;

  @NotBlank
  @Column(name = "vn_display_name", nullable = false, length = 100)
  private String displayName;

  @NotBlank
  @Column(name = "vn_city", nullable = false, length = 100)
  private String city;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @NotNull
  private Country country;

  @NotNull
  @Column(name = "vn_capacity", nullable = false)
  private Integer capacity;

  @Column(name = "vn_address", columnDefinition = "TEXT")
  private String address;

  @Column(name = "vn_image")
  private String image;

  @Column(name = "vn_opened")
  private LocalDate opened;

  @Column(name = "vn_surface", length = 100)
  private String surface; // e.g., "Grass", "Artificial turf"

  @Column(name = "vn_active", nullable = false)
  private boolean active;

  @Override
  public int hashCode() {
    return Objects.hash(name, displayName, city, country);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Venue))
      return false;
    Venue other = (Venue) obj;
    return Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(city, other.city)
        && Objects.equals(country, other.country);
  }

}
