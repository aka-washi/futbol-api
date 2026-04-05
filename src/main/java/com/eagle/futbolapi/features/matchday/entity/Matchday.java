package com.eagle.futbolapi.features.matchday.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.MatchdayStatus;
import com.eagle.futbolapi.features.stage.entity.Stage;

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
@Table(name = "matchday")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "mdy_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "mdy_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "mdy_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "mdy_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "mdy_updated_by", length = 100))
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Matchday extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mdy_stage_id", nullable = false)
  @NotNull
  private Stage stage;

  @Column(name = "mdy_number", nullable = false)
  private Integer number; // Matchday/Jornada number (1, 2, 3, etc.)

  @NotBlank
  @Column(name = "mdy_name", length = 100, nullable = false)
  private String name; // Required name (e.g., "Jornada 1", "Matchday 1")

  @NotBlank
  @Column(name = "mdy_display_name", length = 100, nullable = false)
  private String displayName;

  @NotNull
  @Column(name = "mdy_start_date", nullable = false)
  private LocalDate startDate;

  @NotNull
  @Column(name = "mdy_end_date", nullable = false)
  private LocalDate endDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "mdy_status", nullable = false, length = 50)
  @Builder.Default
  private MatchdayStatus status = MatchdayStatus.NOT_STARTED;

  @Override
  public int hashCode() {
    return Objects.hash(
        stage,
        number,
        name,
        displayName,
        startDate,
        endDate,
        status);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Matchday))
      return false;
    Matchday other = (Matchday) obj;
    return Objects.equals(stage, other.stage)
        && Objects.equals(number, other.number)
        && Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(startDate, other.startDate)
        && Objects.equals(endDate, other.endDate)
        && Objects.equals(status, other.status);
  }

}
