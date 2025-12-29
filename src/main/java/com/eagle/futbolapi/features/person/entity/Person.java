
package com.eagle.futbolapi.features.person.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.shared.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "person")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ps_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "ps_created_at", nullable = false, updatable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "ps_created_by", length = 100, updatable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "ps_updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "ps_updated_by", length = 100))
})
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity {

    private static final String VOWELS = "AEIOU";
    private static final String DEFAULT_CONSONANT = "X";
    private static final String DEFAULT_COUNTRY_CODE = "XX";
    private static final int MIN_NAME_LENGTH_FOR_CONSONANT = 2;
    private static final int COUNTRY_CODE_LENGTH = 2;
    private static final int FIRST_CHAR_INDEX = 0;
    private static final int SECOND_CHAR_INDEX = 1;
    private static final int YEAR_FORMAT_LENGTH = 4;
    private static final int MONTH_DAY_FORMAT_LENGTH = 2;
    private static final int MODULO_DIVISOR = 10;
    private static final char LETTER_A = 'A';
    private static final int LETTER_OFFSET = 1;

    @Column(name = "ps_unique_reg_key", unique = true, nullable = false, length = 20)
    private String uniqueRegKey;

    @Column(name = "ps_first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "ps_middle_name", length = 50)
    private String middleName;

    @Column(name = "ps_last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "ps_second_last_name", length = 50)
    private String secondLastName;

    @Column(name = "ps_display_name", length = 100)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ps_gender", length = 10)
    private Gender gender;

    @Column(name = "ps_email", unique = true, nullable = true, length = 100)
    private String email;

    @Column(name = "ps_birth_date")
    private LocalDate birthDate;

    @NotNull
    @Column(name = "ps_birth_place", length = 100)
    private String birthPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_country_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Country birthCountry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nationality_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Country nationality;

    @Column(name = "ps_height", precision = 5, scale = 2)
    private BigDecimal height; // in meters

    @Column(name = "ps_weight", precision = 5, scale = 2)
    private BigDecimal weight; // in kilograms

    @Column(name = "ps_photo_url", length = 500)
    private String photoUrl;

    @PrePersist
    public void generateKeyBeforePersist() {
        if (uniqueRegKey == null || uniqueRegKey.isEmpty()) {
            uniqueRegKey = generateUniqueRegKey();
        }
    }

    /**
     * Generates a unique registration key based on personal information
     * Format:
     * [LastName1][LastNameConsonant][YYYY][FirstNameConsonant][FirstName1][MM][DD][Gender][CountryCode][CheckDigit]
     */
    public String generateUniqueRegKey() {
        validateRequiredFields();

        StringBuilder regKey = new StringBuilder();
        appendLastNameParts(regKey);
        appendBirthDateParts(regKey);
        appendFirstNameParts(regKey);
        appendGenderCode(regKey);
        appendCountryCode(regKey);
        appendCheckDigit(regKey);

        return regKey.toString();
    }

    private void validateRequiredFields() {
        if (firstName == null || lastName == null || birthDate == null || gender == null || nationality == null) {
            throw new IllegalStateException(
                    "Required fields for uniqueRegKey generation are missing: firstName, lastName, birthDate, gender, nationality");
        }
    }

    private void appendLastNameParts(StringBuilder regKey) {
        regKey.append(lastName.substring(FIRST_CHAR_INDEX, SECOND_CHAR_INDEX).toUpperCase());
        regKey.append(getFirstInternalConsonant(lastName));
    }

    private void appendBirthDateParts(StringBuilder regKey) {
        regKey.append(String.format("%0" + YEAR_FORMAT_LENGTH + "d", birthDate.getYear()));
    }

    private void appendFirstNameParts(StringBuilder regKey) {
        regKey.append(getFirstInternalConsonant(firstName));
        regKey.append(firstName.substring(FIRST_CHAR_INDEX, SECOND_CHAR_INDEX).toUpperCase());
        regKey.append(String.format("%0" + MONTH_DAY_FORMAT_LENGTH + "d", birthDate.getMonthValue()));
        regKey.append(String.format("%0" + MONTH_DAY_FORMAT_LENGTH + "d", birthDate.getDayOfMonth()));
    }

    private void appendGenderCode(StringBuilder regKey) {
        regKey.append(gender == Gender.MALE ? "M" : "F");
    }

    private void appendCountryCode(StringBuilder regKey) {
        String countryCode = extractCountryCode();
        regKey.append(countryCode);
    }

    private String extractCountryCode() {
        if (nationality != null && nationality.getCode() != null && nationality.getCode().length() >= COUNTRY_CODE_LENGTH) {
            return nationality.getCode().substring(FIRST_CHAR_INDEX, COUNTRY_CODE_LENGTH).toUpperCase();
        }
        return DEFAULT_COUNTRY_CODE;
    }

    private void appendCheckDigit(StringBuilder regKey) {
        regKey.append(calculateCheckDigit(regKey.toString()));
    }

    /**
     * Finds the first internal consonant in a name (excluding first and last
     * characters)
     *
     * @param name the name to search
     * @return the first internal consonant or 'X' if none found
     */
    private String getFirstInternalConsonant(String name) {
        if (name == null || name.length() <= MIN_NAME_LENGTH_FOR_CONSONANT) {
            return DEFAULT_CONSONANT;
        }

        String nameUpper = name.toUpperCase();

        // Check internal characters (excluding first and last)
        for (int i = SECOND_CHAR_INDEX; i < nameUpper.length() - SECOND_CHAR_INDEX; i++) {
            char c = nameUpper.charAt(i);
            if (Character.isLetter(c) && !VOWELS.contains(String.valueOf(c))) {
                return String.valueOf(c);
            }
        }
        return DEFAULT_CONSONANT;
    }

    /**
     * Calculates a check digit using a simple modulo algorithm
     *
     * @param regKeyPart the registration key part without check digit
     * @return single digit checksum
     */
    private String calculateCheckDigit(String regKeyPart) {
        int sum = 0;
        for (int i = 0; i < regKeyPart.length(); i++) {
            char c = regKeyPart.charAt(i);
            if (Character.isDigit(c)) {
                sum += Character.getNumericValue(c) * (i + LETTER_OFFSET);
            } else if (Character.isLetter(c)) {
                // Convert letter to number (A=1, B=2, etc.)
                sum += (c - LETTER_A + LETTER_OFFSET) * (i + LETTER_OFFSET);
            }
        }
        return String.valueOf(sum % MODULO_DIVISOR);
    }
}
