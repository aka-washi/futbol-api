# Entity Relationship Hierarchy - Futbol API

This document provides a comprehensive view of all entities in the Futbol API and their hierarchical relationships.

## Entity Hierarchy Overview

```
Country (Root)
├── Organization
│   ├── Tournament
│   │   └── Season
│   │       ├── Competition
│   │       │   ├── Stage ──→ [requires Structure]
│   │       │   │   ├── Matchday
│   │       │   │   │   └── Match
│   │       │   │   │       ├── Lineup
│   │       │   │   │       └── MatchEvent
│   │       │   │   └── Standing
│   │       │   └── Outcome
│   │       ├── SeasonTeam
│   │       └── RosterEntry
│   └── [Parent Organization] (self-reference)
├── Team
│   ├── Venue (home venue)
│   └── [Referenced by multiple entities]
├── Venue
└── Person
    ├── Player
    │   └── [Referenced by multiple entities]
    └── Staff
        └── [Referenced by multiple entities]


PointSystem (Configuration Root - Independent)
└── Structure
    └── [Required by Stage]

```

## Detailed Entity Relationships

### 1. Geographic and Organizational Hierarchy

#### Country (Root Entity)
- **Description**: Represents countries with ISO codes
- **Relationships**:
  - Has many Organizations
  - Has many Teams
  - Has many Venues
  - Has many Persons (birthCountry, nationality)
- **No Dependencies**

#### Organization
- **Description**: Football federations/organizations (e.g., FMF)
- **Parent**: Country (required)
- **Children**:
  - Has many Tournaments
- **Special**: Can have parent Organization (self-reference)

#### Tournament
- **Description**: Tournaments within organizations (e.g., Liga MX)
- **Parent**: Organization (required)
- **Children**:
  - Has many Seasons
- **Special**: Can reference another Tournament for relegation
- **Properties**: type (LEAGUE, CUP, etc.), category (MALE, FEMALE), level (division)

---

### 2. Competition Hierarchy

#### Season
- **Description**: Football season (e.g., 2025-2026)
- **Parent**: Tournament (required)
- **Children**:
  - Has many Competitions
  - Has many SeasonTeams (teams participating in season)
  - Has many RosterEntries (players/staff rosters for season)
- **Properties**: startDate, endDate, isActive, hasRelegation

#### Competition
- **Description**: Specific competition within a season (e.g., Apertura 2025)
- **Parent**: Season (required)
- **Children**:
  - Has many Stages
  - Has many Outcomes (competition results)
- **Properties**: type (APERTURA, CLAUSURA, FULL_SEASON), startDate, endDate, currentMatchday
- **Note**: Each stage within the competition can have its own structure

#### Stage
- **Description**: Stage within a competition (e.g., Regular Season, Playoffs)
- **Parents**:
  - Competition (required)
  - Structure (required) - defines the format
- **Children**:
  - Has many Matchdays
  - Has many Standings
- **Properties**: order, startDate, endDate, status (NOT_STARTED, IN_PROGRESS, COMPLETED)

#### Matchday
- **Description**: Matchday/round/jornada in a competition stage
- **Parent**: Stage (required)
- **Children**:
  - Has many Matches
- **Properties**: number, startDate, endDate, status

#### Match
- **Description**: Single football match
- **Parents**:
  - Matchday (required)
  - Team homeTeam (required)
  - Team awayTeam (required)
  - Venue (optional)
  - Person (referee, optional)
- **Children**:
  - Has many Lineups
  - Has many MatchEvents
- **Properties**: scheduledDate, kickoffTime, status, scores (home/away, halftime, extratime, penalties)

---

### 3. Match-Related Entities

#### Lineup
- **Description**: Starting lineup and substitutes for a match
- **Parents**:
  - Match (required)
  - Team (required)
  - Player (required)
- **Properties**: type (STARTER, SUBSTITUTE), position, jerseyNumber, captain, orderNum

#### MatchEvent
- **Description**: Events during a match (goals, cards, substitutions)
- **Parents**:
  - Match (required)
  - Team (required)
  - Player (required)
  - Player assistPlayer (optional)
  - Player substitutePlayer (optional - for substitutions)
- **Properties**: type (GOAL, YELLOW_CARD, etc.), period (FIRST_HALF, SECOND_HALF, etc.), minute, extraMinute

---

### 4. Team and Person Hierarchy

#### Team
- **Description**: Football team/club
- **Parents**:
  - Country (required)
  - Venue homeVenue (optional)
- **Referenced By**:
  - Match (homeTeam, awayTeam)
  - SeasonTeam
  - RosterEntry
  - Standing
  - Lineup
  - MatchEvent
  - Outcome
  - Player (currentTeam)
  - Staff (currentTeam)
- **Properties**: code (3-letter), founded, colors, logo, website

#### Venue
- **Description**: Football stadium/venue
- **Parents**:
  - Country (required)
- **Referenced By**:
  - Team (homeVenue)
  - Match
- **Properties**: name, city, capacity, surface, opened

#### Person (Base for Players and Staff)
- **Description**: Individual person
- **Parents**:
  - Country birthCountry (optional)
  - Country nationality (optional)
- **Children**:
  - Player (one-to-one)
  - Staff (one-to-one)
- **Properties**: names, gender, birthDate, birthPlace, height, weight, uniqueRegKey

#### Player
- **Description**: Football player
- **Parents**:
  - Person (required, one-to-one)
  - Team currentTeam (optional)
- **Referenced By**:
  - Lineup
  - MatchEvent
  - RosterEntry
  - Outcome
- **Properties**: position, preferredFoot, isActive

#### Staff
- **Description**: Team staff (coaches, trainers, etc.)
- **Parents**:
  - Person (required, one-to-one)
  - Team currentTeam (optional)
- **Referenced By**:
  - RosterEntry
  - Outcome
- **Properties**: role (HEAD_COACH, ASSISTANT_COACH, etc.), isActive

---

### 5. Association Entities

#### SeasonTeam
- **Description**: Many-to-many relationship between seasons and teams
- **Parents**:
  - Season (required)
  - Team (required)
- **Properties**: joinedDate, leftDate, isActive
- **Purpose**: Track team participation in seasons (handles relegation/promotion)

#### RosterEntry
- **Description**: Roster membership for team in specific season
- **Parents**:
  - Season (required)
  - Team (required)
  - Player OR Staff (exactly one required)
- **Properties**: jerseyNumber, joinedDate, leftDate, isActive
- **Purpose**: Track player/staff membership in team rosters per season

#### Standing
- **Description**: Team standings/table for a competition stage
- **Parents**:
  - Stage (required)
  - Team (required)
- **Properties**: position, played, won, drawn, lost, goalsFor, goalsAgainst, goalDifference, points, form
- **Purpose**: Computed standings from match results

---

### 6. Competition Format Entities

#### Structure
- **Description**: Defines competition structure format
- **Parents**:
  - PointSystem (optional)
- **Referenced By**:
  - Stage
- **Properties**: type (ROUND_ROBIN, GROUP_STAGE, etc.), rules (JSON), numberOfTeams, numberOfGroups, hasHomeAndAway
- **Note**: Can be reused across multiple stages and competitions

#### PointSystem
- **Description**: Point system used in competitions
- **Referenced By**:
  - Structure
- **Properties**: pointsForWin, pointsForDraw, pointsForLoss, pointsForWinOnPenalties, pointsForLossOnPenalties

---

### 7. Outcome Entities

#### Outcome
- **Description**: Final outcome/result of a competition
- **Parents**:
  - Competition (required)
  - Team OR Player OR Staff (exactly one required)
- **Properties**: outcomeType (CHAMPION, RUNNER_UP, TOP_SCORER, etc.), position, value, notes
- **Purpose**: Record competition winners, individual awards, etc.

---

## Entity Dependencies (Top-Down)

### Level 0 (No Dependencies)
- **Country** - Root entity
- **PointSystem** - Independent configuration entity

### Level 1 (Depends on Level 0)
- **Organization** → Country
- **Team** → Country, Venue
- **Venue** → Country
- **Person** → Country

### Level 2 (Depends on Level 1)
- **Tournament** → Organization
- **Structure** → PointSystem (optional)
- **Player** → Person, Team
- **Staff** → Person, Team

### Level 3 (Depends on Level 2)
- **Season** → Tournament

### Level 4 (Depends on Level 3)
- **Competition** → Season
- **SeasonTeam** → Season, Team
- **RosterEntry** → Season, Team, Player, Staff

### Level 5 (Depends on Level 4)
- **Stage** → Competition, Structure
- **Outcome** → Competition, Team, Player, Staff

### Level 6 (Depends on Level 5)
- **Matchday** → Stage
- **Standing** → Stage, Team

### Level 7 (Depends on Level 6)
- **Match** → Matchday, Team, Venue, Person

### Level 8 (Depends on Level 7)
- **Lineup** → Match, Team, Player
- **MatchEvent** → Match, Team, Player

---

## Key Relationship Patterns

### One-to-Many Relationships
- Country → Organizations
- Country → Teams
- Country → Venues
- Organization → Tournaments
- Tournament → Seasons
- Season → Competitions
- Competition → Stages
- Stage → Matchdays
- Matchday → Matches
- Match → Lineups
- Match → MatchEvents
- Stage → Standings
- Team → Players (currentTeam)
- Team → Staff (currentTeam)

### Many-to-One Relationships
- Organization → Country
- Tournament → Organization
- Season → Tournament
- Competition → Season
- Stage → Competition
- Stage → Structure
- Matchday → Stage
- Match → Matchday
- Match → Team (homeTeam, awayTeam)
- Match → Venue
- Lineup → Match, Team, Player
- MatchEvent → Match, Team, Player
- Standing → Stage, Team

### One-to-One Relationships
- Player → Person (one player per person)
- Staff → Person (one staff per person)

### Many-to-Many Relationships (via join entities)
- **Season ↔ Team** (via SeasonTeam)
- **Season + Team ↔ Player/Staff** (via RosterEntry)

### Self-Referencing Relationships
- Organization → parentOrganization (Organization)
- Tournament → relegationTo (Tournament)

---

## Entity Categories

### 1. Core Domain Entities
- Country, Organization, Tournament, Season, Competition, Stage, Matchday, Match

### 2. Participant Entities
- Team, Person, Player, Staff

### 3. Location Entities
- Venue, Country

### 4. Match Detail Entities
- Lineup, MatchEvent

### 5. Results & Statistics Entities
- Standing, Outcome

### 6. Association/Join Entities
- SeasonTeam, RosterEntry

### 7. Configuration Entities
- Structure, PointSystem

---

## Database Constraints

### Unique Constraints
- Country: isoCode, code
- Team: code (3-letter)
- Person: uniqueRegKey, email
- SeasonTeam: (season_id, team_id)
- Standing: (stage_id, team_id)
- Player: person_id
- Staff: person_id

### Validation Rules
- RosterEntry: Must have either Player OR Staff (not both)
- Outcome: Must have either Team, Player, OR Staff
- Team code: Must be exactly 3 characters

---

## Typical Query Paths

### Get Matches for a Competition
```
Competition → Stage → Matchday → Match
```

### Get Team Roster for a Season
```
Season + Team → RosterEntry → Player/Staff → Person
```

### Get Match Details
```
Match → Lineup → Player → Person
Match → MatchEvent → Player → Person
Match → Team
Match → Venue → Country
```

### Get Competition Standings
```
Competition → Stage → Standing → Team
```

### Get Competition Structure
```
Competition → Stage → Structure → PointSystem
OR
Tournament → Season → Competition → Stage → Structure → PointSystem
```

### Get Organization Hierarchy
```
Country → Organization → Tournament → Season
Organization → parentOrganization (recursive)
```

---

## Summary Statistics


---

## Recommended Entity/Database Table Creation Order

To ensure all foreign key and dependency constraints are satisfied, create your database tables in the following order:

1. **Country**
2. **PointSystem**
3. **Organization** (references Country)
4. **Venue** (references Country)
5. **Team** (references Country, Venue)
6. **Person** (references Country)
7. **Tournament** (references Organization)
8. **Structure** (references PointSystem)
9. **Player** (references Person, Team)
10. **Staff** (references Person, Team)
11. **Season** (references Tournament)
12. **Competition** (references Season)
13. **SeasonTeam** (references Season, Team)
14. **RosterEntry** (references Season, Team, Player, Staff)
15. **Stage** (references Competition, Structure)
16. **Outcome** (references Competition, Team, Player, Staff)
17. **Matchday** (references Stage)
18. **Standing** (references Stage, Team)
19. **Match** (references Matchday, Team, Venue, Person)
20. **Lineup** (references Match, Team, Player)
21. **MatchEvent** (references Match, Team, Player)

> **Note:**
> - This order ensures that all referenced tables exist before any table that depends on them.
> - For join/association tables (e.g., SeasonTeam, RosterEntry), ensure both parent tables are created first.
> - For self-referencing tables (e.g., Organization, Tournament), you may need to allow NULL for the parent reference or use deferred constraints for initial data loads.
