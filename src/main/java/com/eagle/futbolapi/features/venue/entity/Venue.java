package com.eagle.futbolapi.features.venue.entity;

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
import jakarta.validation.constraints.NotBlank;

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.country.entity.Country;

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
    @AttributeOverride(name = "id", column = @Column(name = "ven_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "ven_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "ven_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "ven_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "ven_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Venue extends BaseEntity {

  @NotBlank
  @UniqueField
  @Column(name = "ven_name", nullable = false, length = 100)
  private String name;

  @NotBlank
  @UniqueField
  @Column(name = "ven_display_name", length = 100, nullable = false)
  private String displayName;

  @UniqueField
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Country country;

  @Column(name = "ven_capacity")
  private Integer capacity;

  @Column(name = "ven_image_url", length = 255)
  private String imageUrl;

  @Column(name = "ven_surface", length = 100)
  private String surface;

  @Column(name = "ven_address", length = 255)
  private String address;

  @Column(name = "ven_city", length = 100)
  private String city;

  @Column(name = "ven_opened")
  private LocalDate opened;

  @Column(name = "ven_active")
  private Boolean active;

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        displayName,
        country,
        capacity,
        imageUrl,
        surface,
        address,
        city,
        opened,
        active);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Venue venue = (Venue) o;
    return Objects.equals(name, venue.name)
        && Objects.equals(displayName, venue.displayName)
        && Objects.equals(country, venue.country)
        && Objects.equals(capacity, venue.capacity)
        && Objects.equals(imageUrl, venue.imageUrl)
        && Objects.equals(surface, venue.surface)
        && Objects.equals(address, venue.address)
        && Objects.equals(city, venue.city)
        && Objects.equals(opened, venue.opened)
        && Objects.equals(active, venue.active);
  }
}
