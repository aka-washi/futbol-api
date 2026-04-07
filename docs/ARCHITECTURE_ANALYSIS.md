# Futbol API — Architecture Analysis

> Generated: April 5, 2026 | Branch: `build-fixes-and-model-improvement`

---

## Table of Contents

1. [Technology Stack](#1-technology-stack)
2. [Conceptual Architecture](#2-conceptual-architecture)
3. [Standard Feature Pattern](#3-standard-feature-pattern)
4. [Domain Model & Entity Catalog](#4-domain-model--entity-catalog)
5. [Conformance Matrix](#5-conformance-matrix)
6. [Deviations & Issues](#6-deviations--issues)

---

## 1. Technology Stack

| Concern | Technology |
|---|---|
| Runtime | Java 21, Spring Boot 4.0.5 |
| Persistence | Spring Data JPA, Hibernate |
| Databases | H2 (dev), Microsoft SQL Server (prod) |
| DTO mapping | MapStruct 1.6.3 |
| Validation | Jakarta Bean Validation, custom validators |
| Boilerplate | Lombok |
| Web | Spring MVC REST, context path `/api/v1` |
| Quality | JaCoCo, SonarQube, Spotless |

---

## 2. Conceptual Architecture

The application is a **football (soccer) data management API** that models the full lifecycle of a football competition ecosystem — from national governing bodies and clubs, down to individual match events.

### Domain Hierarchy (top-down)

```
Organization  ──────────────────────────────────── Country
    │                                                  │
    ├─ Tournament (Liga MX, Copa MX…)                  │
    │       │                                          │
    │   TournamentSeason (Liga MX Apertura 2025)       │
    │       │                                          │
    │   Competition (Apertura 2025 – Regular Season)   │
    │       │                                          │
    │      Stage (Group Stage, Liguilla…)              │
    │       ├─ StageFormat (Round Robin, K/O…)         │
    │       ├─ Group (Group A, B…)                     │
    │       ├─ Standing (team table positions)         │
    │       └─ Matchday (Jornada 1…17)                 │
    │               └─ Match                           │
    │                   ├─ Venue                       │
    │                   ├─ Lineup (home + away)        │
    │                   │   └─ LineupMember (Player/Staff)
    │                   └─ MatchEvent (goal, card…)    │
    │                                                  │
    ├─ Team ────────────────────────────────────── Country
    │   ├─ TeamBrand (identity / nickname / colors)    │
    │   ├─ TeamVenue (home / training grounds)         │
    │   └─ Registration (player/staff enrolled)        │
    │                                                  │
Person
    ├─ Player (position, foot, current team)
    └─ Staff (coaching role, current team)

Supporting
    ├─ Season (Apertura 2025, Clausura 2026…)
    ├─ PointSystem (3-1-0 or custom)
    ├─ CompetitionOutcome (final standings per team)
    ├─ QualificationOutcome (which team advanced to which competition)
    ├─ LeagueMembership (org membership / promotion-relegation history)
    └─ OrganizationTransition (mergers, renames, dissolutions)
```

### Layered Package Architecture

Each domain concept lives under `features/<domain>/` and is implemented across six layers:

```
features/
└── <domain>/
    ├── entity/      JPA entity (DB table)
    ├── dto/         API request/response payload
    ├── mapper/      MapStruct entity ↔ DTO conversion
    ├── repository/  Spring Data queries
    ├── service/     Business logic & orchestration
    └── controller/  REST endpoints
```

Cross-cutting concerns live in `features/base/`.

### Bootstrap Mechanism

`features/bootstrap/` provides a bulk-load `POST /bootstrap/load` endpoint that accepts any entity type by name and delegates to the appropriate service. This is the primary way domain data is initially ingested (from JSON files in `input/`).

---

## 3. Standard Feature Pattern

### 3.1 Entity

```java
@Entity
@Table(name = "example")
@AttributeOverrides({
    @AttributeOverride(name = "id",        column = @Column(name = "ex_id")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "ex_created_at")),
    // …
})
@Getter @Setter @NoArgsConstructor
public class Example extends BaseEntity {

    @Column(name = "ex_name", nullable = false, length = 100)
    @NotBlank
    @UniqueField
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_other_id")
    @UniqueField(fieldPath = "other.id")
    private OtherEntity other;
}
```

Key conventions:
- Extends `BaseEntity` (provides `id`, `createdAt`, `createdBy`, `updatedAt`, `updatedBy`).
- Every column name is prefixed with a **3-letter** code (e.g., `cty_` for country, `org_` for organization).
- FK relationships must have active DB-level foreign key constraints — do **not** use `ConstraintMode.NO_CONSTRAINT`.
- `@UniqueField` marks which fields participate in **business-logic duplicate detection** at the service layer (processed by reflection in `BaseCrudService`). It is complementary to DB constraints, not a replacement for them.
- `@GeneratedField` marks fields that are auto-generated on persist and must not be overwritten on update (e.g., `uniqueRegKey` on `Person`).

### 3.2 DTO

```java
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@AtLeastOneNotNull(fields = {"otherId", "otherDisplayName"})
public class ExampleDto {
    private Long id;
    private String name;
    private Long otherId;
    private String otherDisplayName;
    // …audit fields…
}
```

Key conventions:
- Uses `@AtLeastOneNotNull` to require either an ID or a displayName for referenced entities (flexible relationship resolution).
- Carries audit fields mirroring `BaseEntity`.

### 3.3 Mapper

```java
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExampleMapper extends BaseMapper<Example, ExampleDto> {

    @Override
    @Mapping(target = "other", ignore = true)   // resolved in service
    Example toEntity(ExampleDto dto);

    @Override
    @Mapping(target = "otherId",          source = "other.id")
    @Mapping(target = "otherDisplayName", source = "other.displayName")
    ExampleDto toDto(Example entity);
}
```

Key conventions:
- Extends `BaseMapper<Entity, Dto>` which provides `toEntity`, `toDto`, `toDtoList`.
- FK references in `toEntity` are **always ignored** — they are resolved by the service afterward.
- Enum values are mapped via their `.getLabel()` string rather than `.name()`.

### 3.4 Repository

```java
@Repository
public interface ExampleRepository extends BaseRepository<Example, Long> {
    Optional<Example> findByDisplayName(String displayName);
}
```

Key conventions:
- Extends `BaseRepository<E, Long>` which extends both `JpaRepository<E, Long>` and `JpaSpecificationExecutor<E>`.
- The `JpaSpecificationExecutor` is required by `BaseCrudService` for Specification-based uniqueness detection.
- Every feature repository adds at minimum `findByDisplayName`.

### 3.5 Service

```java
@Service
@Transactional
@Validated
public class ExampleService extends BaseCrudService<Example, Long, ExampleDto> {

    public ExampleService(ExampleRepository repo, ExampleMapper mapper) {
        super(repo, mapper);
    }

    @Override
    protected boolean isDuplicate(ExampleDto dto) { /* check displayName */ }

    @Override
    protected boolean isDuplicate(Long id, ExampleDto dto) { /* check excluding self */ }

    @Override
    protected void resolveRelationships(ExampleDto dto, Example entity) {
        // load Other by id or displayName and set entity.setOther(...)
    }
}
```

Key `BaseCrudService` behaviors:
- `create()` → strips incoming ID → `resolveRelationships()` → `validateForCreate()` → `saveNew()`
- `update()` → preserves audit & `@GeneratedField` values → checks equality (`NoChangesDetectedException`) → checks duplicate → `resolveRelationships()` → save
- `UniquenessStrategy.ALL` — all `@UniqueField` values must simultaneously match for a duplicate. `UniquenessStrategy.ANY` — any single match suffices.

### 3.6 Controller

```java
@RestController
@RequestMapping("/examples")
public class ExampleController
        extends BaseCrudController<Example, ExampleDto, ExampleService, ExampleMapper> {

    public ExampleController(ExampleService service, ExampleMapper mapper) {
        super(service, mapper);
    }
}
```

Inherits **six** endpoints from `BaseCrudController`:

| Method | Path | Action |
|---|---|---|
| `GET` | `/examples` | List all (paginated: `page`, `size`, `sortBy`, `sortDir`) |
| `GET` | `/examples/{id}` | Get by ID |
| `POST` | `/examples` | Create |
| `PUT` | `/examples/{id}` | Full update |
| `PATCH` | `/examples/{id}` | Partial update |
| `DELETE` | `/examples/{id}` | Delete |

---

## 4. Domain Model & Entity Catalog

### Foundation

| Entity | Table | Prefix | Purpose |
|---|---|---|---|
| `Country` | `country` | `cty_` | ISO-coded countries used as reference data for people, venues, teams, and organizations. Fields: `name`, `displayName`, `code` (10), `isoCode` (3), `flagUrl`. |
| `Organization` | `organization` | `org_` | Legal entities that own or govern teams/competitions (federations, clubs, etc.). Self-referential `parentOrganization`. Fields: `name`, `displayName`, `abbreviation`, `type` (enum), `country`, `founded`, `startDate`, `endDate`, `logo`, `website`. |
| `Season` | `season` | `ssn_` | A named calendar period used to group tournament editions (e.g., "Apertura 2025"). Simplest entity. |
| `PointSystem` | `point_system` | `pts_` | Configurable point allocation rules (win/draw/loss/forfeit/penalties). Referenced by `StageFormat`. |

### People

| Entity | Table | Prefix | Purpose |
|---|---|---|---|
| `Person` | `person` | `per_` | Physical individual. Auto-generates a unique registration key (`uniqueRegKey`) from name, birthdate, gender, and nationality. Shared by both players and staff. |
| `Player` | `player` | `ply_` | A person in a playing role. `@OneToOne Person`; carries `position`, `preferredFoot`, `currentTeam`, `active`. |
| `Staff` | `staff` | `stf_` | A person in a coaching/technical role. `@OneToOne Person`; carries `role` (enum), `currentTeam`, `active`. Mirrors the player model. |

### Competition Structure

| Entity | Table | Prefix | Purpose |
|---|---|---|---|
| `Tournament` | `tournament` | `trn_` | A recurring competition series (e.g., "Liga MX"). Belongs to an `Organization`; optionally self-references another tournament via `relegationTo` for promotion/relegation linkage. |
| `TournamentSeason` | `tournament_season` | `tsn_` | A specific edition of a tournament (e.g., "Liga MX Apertura 2025"). Ties together a `Tournament` and a `Season`; tracks dates, status, and flags for promotion/relegation. |
| `Competition` | `competition` | `cmp_` | A distinct competition format within a tournament season (e.g., "Regular Season", "Liguilla"). Belongs to `TournamentSeason`; has its own `type`, `status`, and date range. |
| `StageFormat` | `stage_format` | `sgf_` | A reusable template describing how a stage is played (Round Robin, Single Elimination, etc.). References a `PointSystem`; defines team counts, group counts, home-and-away flag, and qualification spots. |
| `Stage` | `stage` | `stg_` | A phase within a `Competition` (e.g., "Group Stage", "Semi-Finals"). References a `StageFormat`; has an ordering `order`, date range, and `status`. |
| `Group` | `team_group` | `grp_` | A named sub-group within a knockout/group-stage `Stage` (e.g., "Group A"). Has a display order and expected team count. |
| `Matchday` | `matchday` | `md_` | A calendar round (jornada) within a `Stage`. Has a sequential number, date range, and `status`. |

### Match & Events

| Entity | Table | Prefix | Purpose |
|---|---|---|---|
| `Match` | `match` | `mtc_` | A single game between two teams on a `Matchday`. Captures full scoreline (normal, half-time, extra-time, penalties), venue, referee, attendance, and weather. |
| `Lineup` | `lineup` | `lu_` | The declared squad/formation for one team in a specific match. One lineup per team per match. |
| `LineupMember` | `lineup_member` | `lm_` | A player or staff member entry within a lineup, with jersey number, role, position, captain flag, and ordering. |
| `MatchEvent` | `match_event` | `me_` | A discrete event during a match (goal, yellow card, red card, substitution, etc.). Tracks team, player, type, period, minute, and optional assist/substitute player. |

### Teams

| Entity | Table | Prefix | Purpose |
|---|---|---|---|
| `Team` | `team` | `tem_` | A competitive squad belonging to an `Organization`. Distinguished by `gender`, `ageCategory`, and `founded` date. Links to `Country`. |
| `TeamBrand` | `team_brand` | `tbr_` | The visual and naming identity of a team at a point in time (name, abbreviation, nicknames via `@ElementCollection`, colors, logo, website, date range). |
| `TeamVenue` | `team_venue` | `tmv_` | Associates a `Team` with a `Venue` and its designated use (home, training), including a date range. |
| `Registration` | `registration` | `reg_` | Enrolls a `Player` or `Staff` member in a `Competition` for a specific `Team`, with jersey number and enrollment dates. Enforces jersey uniqueness per competition+team and requires active player status. |

### Outcomes & History

| Entity | Table | Prefix | Purpose |
|---|---|---|---|
| `CompetitionOutcome` | `competition_outcome` | `cpo_` | Records the final result of a team in a competition (position, points earned, outcome type). |
| `QualificationOutcome` | `qualification_outcome` | `qfo_` | Records which team qualified from one competition to another (source → target competition). |
| `Standing` | `standing` | `sd_` | Live league table entry for a team within a `Stage`. Tracks all statistical columns (played, won, drawn, lost, goals, form, home/away splits). |
| `LeagueMembership` | `league_memberships` | `lmp_` | Records a team's membership of a league (organization) for a period bounded by start and end seasons, with a membership status. |
| `OrganizationTransition` | `organization_transition` | `ogt_` | Documents legal transitions between organizations (merger, rebrand, dissolution, promotion). Tracks from/to organizations, type, and effective date. |
| `SeasonParticipation` | *(unimplemented)* | — | Intended to record which teams participated in a given season. Currently empty placeholder. |

---

## 5. Conformance Matrix

The table below checks each feature against the six required layers and additional quality criteria.

Legend: ✅ Present & correct · ⚠️ Present but flawed · ❌ Missing/empty

| Feature | entity | dto | mapper | repository | service | controller | Notes |
|---|---|---|---|---|---|---|---|
| `base` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | Foundation — also provides annotation/, enums/, exception/, util/, validation/ |
| `bootstrap` | — | ✅ | — | — | ✅ | ✅ | Intentionally operational-only; no entity layer needed |
| `competition` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `competitionoutcome` | ✅ | ✅ | ✅ | ✅ | ⚠️ | ⚠️ | Service & Controller missing `@Service` / `@RestController` / `@RequestMapping` — not Spring beans |
| `country` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `group` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `leaguemembership` | ✅ | ✅ | ✅ | ⚠️ | ⚠️ | ⚠️ | All three layers missing Spring stereotype annotations; also has `endSeason` logic bug |
| `lineup` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `lineupMember` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | Service injects repositories directly (not services) — acceptable pattern deviation |
| `match` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `matchday` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `matchevent` | ✅ | ✅ | ✅ | ⚠️ | ✅ | ✅ | Repository extends `JpaRepository` directly — misses `JpaSpecificationExecutor` |
| `organization` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `organizationtransition` | ✅ | ✅ | ✅ | ⚠️ | ✅ | ✅ | Repository has `findByDisplayName` but entity has no `displayName` field |
| `person` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** — notable `findOrCreate` pattern |
| `player` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `pointsystem` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `qualificationoutcome` | ✅ | ✅ | ⚠️ | ⚠️ | ⚠️ | ⚠️ | **Unimplemented stubs** — mapper, repository, service, controller are empty class/interface bodies |
| `registration` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** — richest custom validation |
| `season` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `seasonParticipation` | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | **Entirely empty** — 6 empty folders, no files at all |
| `staff` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `stage` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `stageFormat` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `standing` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** — richest repository |
| `team` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `teambrand` | ✅ | ✅ | ✅ | ⚠️ | ⚠️ | ❌ | Repository & Service missing `@Repository`/`@Service`; controller folder exists but is empty |
| `teamvenue` | ✅ | ⚠️ | ❌ | ❌ | ❌ | ❌ | Only entity + DTO; DTO has wrong validation annotations (references non-existent fields) |
| `tournament` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `tournamentSeason` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |
| `venue` | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | **Complete** |

**Summary**: 17 features fully complete · 4 features partially implemented with bugs · 3 features incomplete stubs · 1 feature entirely empty.

---

## 6. Deviations & Issues

Issues are classified by severity.

---

### CRITICAL — Will cause runtime failures

#### C-1 · `seasonParticipation` — Fully empty feature
All 6 subfolders exist but contain zero files. The feature has no implementation whatsoever.

**Missing**: `SeasonParticipation` entity, `SeasonParticipationDto`, `SeasonParticipationMapper`, `SeasonParticipationRepository`, `SeasonParticipationService`, `SeasonParticipationController`.

---

#### C-2 · `qualificationoutcome` — All implementation is empty stubs
The folder structure and DTO are present, but:
- `QualificationOutcomeController` — empty class body, no `@RestController`, no `@RequestMapping`
- `QualificationOutcomeService` — empty class body, no `@Service`
- `QualificationOutcomeRepository` — empty interface, does not extend `BaseRepository` or `JpaRepository`
- `QualificationOutcomeMapper` — empty interface, does not extend `BaseMapper`

None of these are Spring-managed beans. The endpoint does not exist.

---

#### C-3 · `competitionoutcome` — Missing Spring stereotype annotations
`CompetitionOutcomeController` lacks `@RestController` and `@RequestMapping`.
`CompetitionOutcomeService` lacks `@Service`.

Neither will be registered as Spring beans. The competition outcome endpoints are unreachable.

---

#### C-4 · `leaguemembership` — Missing Spring stereotype annotations on all operational layers
`LeagueMembershipController` lacks `@RestController` and `@RequestMapping`.
`LeagueMembershipService` lacks `@Service`.
`LeagueMembershipRepository` lacks `@Repository`.

Additionally, `resolveRelationships()` in the service has a logic bug: the `endSeason` resolution block checks `endSeasonDisplayName != null` twice instead of checking `endSeasonId != null` on one branch. This means the `endSeason` can never be set by ID alone.

---

#### C-5 · `teambrand` — Controller missing, Spring annotations missing
`teambrand/controller/` is an empty folder — no controller file was ever created.
`TeamBrandRepository` lacks `@Repository`.
`TeamBrandService` lacks `@Service`.

The team brand endpoints are completely absent.

---

#### C-6 · `teamvenue` — Only entity and DTO exist
`teamvenue/` contains only `entity/TeamVenue.java` and `dto/TeamVenueDto.java`. No mapper, repository, service, or controller.

Additionally, `TeamVenueDto` is annotated with `@AtLeastOneNotNull(fields = {"organizationId", "organizationDisplayName"})` and `@AtLeastOneNotNull(fields = {"countryId", "countryDisplayName"})`, yet the `TeamVenue` entity has **no `organization` or `country` fields** — it only has `team` and `venue`. These validations reference non-existent fields and will either fail silently or throw at runtime.

**Missing**: `TeamVenueMapper`, `TeamVenueRepository`, `TeamVenueService`, `TeamVenueController`.
Also needed: fix `TeamVenueDto` to replace wrong `@AtLeastOneNotNull` annotations with correct `teamId`/`teamDisplayName` and `venueId`/`venueDisplayName` pairs.

---

### HIGH — Incorrect behavior or standard divergence

#### H-1 · `matchevent/MatchEventRepository` — Wrong base interface
```java
// Current (wrong)
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long>

// Required
public interface MatchEventRepository extends BaseRepository<MatchEvent, Long>
```
`BaseRepository` extends both `JpaRepository` and `JpaSpecificationExecutor`. `BaseCrudService` relies on `JpaSpecificationExecutor` for Specification-based uniqueness detection. Without it, the duplicate check for match events will throw `ClassCastException` or `UnsupportedOperationException` at runtime.

---

#### H-2 · `organizationtransition/OrganizationTransitionRepository` — Phantom query method
```java
Optional<OrganizationTransition> findByDisplayName(String displayName);
```
`OrganizationTransition` has no `displayName` field. Spring Data JPA will throw `PropertyReferenceException` at application startup, preventing the context from loading.

---

#### H-3 · `registration` — Jersey uniqueness not DB-enforced
Jersey number uniqueness per competition+team is enforced only inside `validateForCreate()`. There is no `@UniqueConstraint` on the `registration` table. Under concurrent writes, two registrations with the same jersey number could be inserted simultaneously.

Recommended fix: add `@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"reg_competition_id", "reg_team_id", "reg_jersey_number"}))` to the `Registration` entity.

---

### MEDIUM — Design gaps or debt

#### M-1 · `lineupMember/LineupMemberService` — Bypasses service layer for related entities
The service injects `PlayerRepository` and `StaffRepository` directly instead of `PlayerService` and `StaffService`. This bypasses any business logic in those services (e.g., active-status checks) and breaks the convention all other services follow.

---

#### M-2 · Folder naming inconsistency
Four features use camelCase folder names (`lineupMember`, `stageFormat`, `tournamentSeason`, `seasonParticipation`); all others use lowercase (`leaguemembership`, `competitionoutcome`, `matchevent`, `organizationtransition`). No consistent convention is applied.

---

#### M-3 · All enums centralized in `base/enums/`
All 25 domain enums live under `features/base/enums/` rather than in their respective feature packages. While this aids discovery, it creates tight coupling between `base` and every feature, making it impossible to extract a feature as an independent module.

---

#### M-4 · `Person.generateUniqueRegKey()` — business logic embedded in entity
The auto-key generation algorithm lives as a `@PrePersist` method directly inside the `Person` entity. This logic (string formatting, checkdigit calculation) belongs in a service or a dedicated domain service, not in the persistence layer where it cannot be easily unit-tested or replaced.

---

#### M-5 · `Tournament.relegationTo` uses `@OneToOne`
The self-referential `relegationTo` field is `@OneToOne Tournament`, which means only one tournament can reference any given lower-division tournament. In reality, multiple league levels can demote to the same lower division (e.g., two regional leagues both demoting to the same third division). `@ManyToOne` is the correct cardinality.

---

#### M-6 · `standing` — `form` field has no structural validation
The `form` field (e.g., `"WWDLW"`) is a free-form `String(10)`. There is no format validation preventing invalid characters. A `@Pattern(regexp = "^[WDLP]{0,10}$")` constraint would be appropriate.

---

### HIGH — Incorrect behavior or standard divergence

#### H-4 · `@ForeignKey(ConstraintMode.NO_CONSTRAINT)` on all FK columns — violates standard pattern
Every FK `@JoinColumn` in the codebase currently sets `foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)`, fully disabling DB-level referential integrity. This is a **deviation from the project standard**: FK constraints must be active at the DB level. `@UniqueField` exists only to detect business-logic duplicates at the service layer; it does not substitute for FK enforcement.

All entity classes with `@ManyToOne`, `@OneToOne`, or `@OneToMany` FK columns must have their `@ForeignKey(ConstraintMode.NO_CONSTRAINT)` removed so Hibernate generates the proper constraint DDL.

---

### LOW — Minor observations

#### L-2 · `standing/StandingRepository` is the only repository using `@Query` JPQL
All other repositories rely exclusively on Spring Data derived query methods. If complex queries are needed elsewhere in the future, this pattern should be documented as acceptable.

#### L-3 · `Bootstrap` is the only non-CRUD controller
`BootstrapController` does not extend `BaseCrudController` and manually implements its single `POST /bootstrap/load` endpoint. This is correct and intentional but worth noting as the one exception to the controller inheritance pattern.
