package com.eagle.futbolapi.features.person.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

  private Long id;

  @NotBlank(message = "Unique Registration Key is required")
  private String uniqueRegKey;

  @NotBlank(message = "First name is required")
  private String firstName;

  private String middleName;

  @NotBlank(message = "Last name is required")
  private String lastName;

  private String secondLastName;

  private String displayName;

  @NotNull(message = "Gender is required")
  private String gender;

  @Email(message = "Email should be valid")
  private String email;

  @NotNull(message = "Birth date is required")
  private LocalDate birthDate;

  private String birthPlace;

  @NotNull(message = "Birth country is required")
  private Long birthCountryId;
  private String birthCountryDisplayName;

  @NotNull(message = "Nationality country is required")
  private Long nationalityCountryId;
  private String nationalityCountryDisplayName;

  private BigDecimal height;
  private BigDecimal weight;
  private String photoUrl;

  private LocalDateTime createdAt;
  private String createdBy;

  private LocalDateTime updatedAt;
  private String updatedBy;
}
