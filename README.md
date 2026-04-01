# Futbol API

This project is a Java-based API for managing football (soccer) data, built with Spring Boot and Gradle.

## Prerequisites

- Java 17 or higher (ensure JAVA_HOME is set)
- Gradle (wrapper included, so no need to install manually)
- (Optional) Docker, if you want to run a database locally

## Running the Project

### 1. Clone the Repository

```
git clone <repository-url>
cd futbol-api
```

### 2. Configure the Database

The project supports multiple database configurations. By default, it uses the settings in `config/application.yml`.

- For H2 (in-memory, default for development): No extra setup needed.
- For MSSQL: Update `config/application-mssql.yml` with your connection details and set the active profile to `mssql`.

To change the active profile, set the `SPRING_PROFILES_ACTIVE` environment variable:

```
set SPRING_PROFILES_ACTIVE=mssql   # On Windows
export SPRING_PROFILES_ACTIVE=mssql # On Linux/Mac
```

### 3. Build the Project

Use the Gradle wrapper to build:

```
./gradlew build   # On Linux/Mac
./gradlew.bat build # On Windows
```

### 4. Run the Application


#### To run with the `mssql` profile:

```
# On Linux/Mac
SPRING_PROFILES_ACTIVE=mssql ./gradlew bootRun
# On Windows (cmd)
set SPRING_PROFILES_ACTIVE=mssql && ./gradlew.bat bootRun
# On Windows (PowerShell)
$env:SPRING_PROFILES_ACTIVE="mssql"; ./gradlew.bat bootRun
```

#### To run with the default profile:

```
./gradlew bootRun   # On Linux/Mac
./gradlew.bat bootRun # On Windows
```

The API will start on [http://localhost:8080](http://localhost:8080) by default.

### 5. API Documentation

Check the API contract in `API_CONTRACT.md` for available endpoints and usage.

## Running Tests

```
./gradlew test   # On Linux/Mac
./gradlew.bat test # On Windows
```

## Code Quality

To check code formatting and quality:

```
./gradlew spotlessCheck
./gradlew check
```

## SonarQube Analysis

To run SonarQube analysis:

```
./run-sonar.bat
```

## Additional Notes

- Configuration files are in the `config/` directory.
- Logs are written to the `logs/` directory.
- Sample data can be found in the `input/` directory.

## Troubleshooting

### Database Issues

**Multiple Identity Columns Error**

If you encounter an error like "Multiple identity columns specified for table", this happens when switching between H2 and MSSQL profiles or when the database schema is inconsistent.

**Solutions:**
1. Drop the existing database/tables and restart the application
2. Use `create-drop` DDL strategy in your application configuration (this will recreate tables on each startup)
3. For production, use proper database migrations instead of auto DDL

**Note:** The current MSSQL configuration uses `create-drop` to avoid schema conflicts during development.

## Contact

For questions or support, please contact the project maintainer.

## Entity Dependency Table

| Entity           | Depends On                                     |
|------------------|------------------------------------------------|
| country          | (none)                                         |
| point_system     | (none)                                         |
| season           | (none)                                         |
| organization     | country (optional), organization (parent, optional) |
| person           | country (birth_country, optional), country (nationality, optional) |
| stage_format     | point_system (optional)                        |
| venue            | country (optional)                             |
| league_membership | organization (league), organization (member)  |
| organization_transition | organization (from), organization (to)   |
| team             | organization, country                          |
| tournament       | organization, tournament (relegation_to, optional) |
| player           | person, team (current_team, optional)          |
| season_participation | season, team                               |
| staff            | person, team (current_team, optional)          |
| team_brand       | team                                           |
| team_venue       | team, venue                                    |
| tournament_season | season, tournament                            |
| competition      | tournament_season                              |
| competition_outcome | competition, team                           |
| qualification_outcome | competition (source), competition (target), team |
| registration     | competition, team, player (optional), staff (optional) |
| stage            | competition, stage_format (optional)           |
| group            | stage                                          |
| matchday         | stage                                          |
| standing         | stage, team                                    |
| match            | matchday, team (home_team), team (away_team), venue (optional), person (referee, optional) |
| lineup           | match, team                                    |
| match_event      | match, team, player, player (assist_player, optional), player (substitute_player, optional) |
| lineup_member    | lineup, player (optional), staff (optional)    |
