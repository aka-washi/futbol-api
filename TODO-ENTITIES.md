# Entity Fixes TODO

This document lists entity-model inconsistencies discovered (compared to the `Team` pattern), with suggested changes for review.

- **Fix OrganizationTransition duplicate JoinColumn names**: [src/main/java/com/eagle/futbolapi/features/organizationtransition/entity/OrganizationTransition.java](src/main/java/com/eagle/futbolapi/features/organizationtransition/entity/OrganizationTransition.java)
  - Problem: both `fromOrganization` and `toOrganization` use the same `organization_id` JoinColumn.
  - Suggested change: rename the join columns to `ogt_from_organization_id` and `ogt_to_organization_id` (or `from_organization_id` / `to_organization_id`) and update DB constraints accordingly.

- **Fix `Team` unique constraint column name mismatch**: [src/main/java/com/eagle/futbolapi/features/team/entity/Team.java](src/main/java/com/eagle/futbolapi/features/team/entity/Team.java)
  - Problem: table-level unique constraint references `organization_id` while the `organization` JoinColumn is `tem_organization_id`.
  - Suggested change: update the `@UniqueConstraint` to use `tem_organization_id` (or change the JoinColumn to `organization_id`) so column names are consistent.

- **Convert `TeamBrand` start/end dates to LocalDate and fix collection join column name**: [src/main/java/com/eagle/futbolapi/features/teambrand/entity/TeamBrand.java](src/main/java/com/eagle/futbolapi/features/teambrand/entity/TeamBrand.java)
  - Problem: `startDate`/`endDate` are `String`; `@ElementCollection` uses join column `_tbr_team_brand_id` (leading underscore / inconsistent with prefix style).
  - Suggested change: change `startDate` and `endDate` to `LocalDate`; change join column to `tbr_team_brand_id` and ensure consistent prefixing (`tbr_`).

- **Normalize equals/hashCode implementations to match `Team` pattern**: multiple files, examples:
  - [src/main/java/com/eagle/futbolapi/features/tournamentSeason/entity/TournamentSeason.java](src/main/java/com/eagle/futbolapi/features/tournamentSeason/entity/TournamentSeason.java)
  - [src/main/java/com/eagle/futbolapi/features/stage/entity/Stage.java](src/main/java/com/eagle/futbolapi/features/stage/entity/Stage.java)
  - [src/main/java/com/eagle/futbolapi/features/organization/entity/Organization.java](src/main/java/com/eagle/futbolapi/features/organization/entity/Organization.java)
  - Problem: inconsistent use of `instanceof` vs `getClass()` and occasionally comparing IDs vs fields.
  - Suggested change: standardize to the `Team` approach (use `instanceof` and compare the natural fields used for uniqueness), and ensure `hashCode()` matches `equals()` fields.

- **Fix `LeagueMembership` package casing and path**: [src/main/java/com/eagle/futbolapi/features/LeagueMembership/entity/LeagueMembership.java](src/main/java/com/eagle/futbolapi/features/LeagueMembership/entity/LeagueMembership.java)
  - Problem: package directory `LeagueMembership` uses uppercase; Java package names should be lowercase.
  - Suggested change: move package to `leaguemembership` (lowercase) and update the `package` declaration and imports.

- **Change `LineupMember.captain` primitive to `Boolean` and set default**: [src/main/java/com/eagle/futbolapi/features/lineupMember/entity/LineupMember.java](src/main/java/com/eagle/futbolapi/features/lineupMember/entity/LineupMember.java)
  - Problem: `captain` is `boolean` (primitive) and not annotated with `@Builder.Default` consistently.
  - Suggested change: make it `Boolean` with `@Builder.Default` and a default of `false` to match other entity patterns.

- **Annotate `TeamVenue` fields with prefixed `@Column` names and enums**: [src/main/java/com/eagle/futbolapi/features/teamvenue/entity/TeamVenue.java](src/main/java/com/eagle/futbolapi/features/teamvenue/entity/TeamVenue.java)
  - Problem: `designation`, `startDate`, `endDate` lack `@Column` with `tmv_` prefixes; `designation` (enum) lacks `@Enumerated`.
  - Suggested change: add `@Column(name = "tmv_designation")` + `@Enumerated(EnumType.STRING)`, and `@Column(name = "tmv_start_date")`, `@Column(name = "tmv_end_date")` with appropriate types.

- **Other minor consistency suggestions**:
  - Ensure all JoinColumn names follow the entity prefix pattern (e.g., `ply_person_id`, `stf_person_id`, `tmv_team_id`).
  - Where `@UniqueConstraint` or `@UniqueField` references columns, confirm the referenced column names use the same prefixes as their `@Column`/`@JoinColumn` declarations.

- **Run build and tests after changes**
  - Suggested command to verify: 

```bash
./gradlew clean build
```

---

Status: these items are in the workspace TODO list and ready for implementation. Reply with which item(s) you'd like me to implement first and I'll start applying the code changes (I'll update the tracked todo list as I progress).