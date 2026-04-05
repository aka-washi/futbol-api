#!/bin/bash
# Script to generate CSV files with headers based on Java DTO fields
#
# Usage:
#   ./generate-csv-from-dto.sh           # Generate all CSV files
#   ./generate-csv-from-dto.sh <entity>  # Generate only the specified entity's CSV file
#
# Example:
#   ./generate-csv-from-dto.sh venue
#   ./generate-csv-from-dto.sh team
#
# Available entities:

# Directory containing DTOs (update if needed)
DTO_DIR="/workspaces/futbol-api/src/main/java/com/eagle/futbolapi/features"
# Output CSV directory
CSV_DIR="/workspaces/futbol-api/input/csv"

# Map entity to DTO file (add more as needed)
declare -A ENTITY_DTO_MAP=(
  [competition]="competition/dto/CompetitionDto.java"
  [competitionoutcome]="competitionoutcome/dto/CompetitionOutcomeDto.java"
  [country]="country/dto/CountryDto.java"
  [group]="group/dto/GroupDto.java"
  [leaguemembership]="leaguemembership/dto/LeagueMembershipDto.java"
  [lineup]="lineup/dto/LineupDto.java"
  [lineupmember]="lineupMember/dto/LineupMemberDto.java"
  [match]="match/dto/MatchDto.java"
  [matchday]="matchday/dto/MatchdayDto.java"
  [matchevent]="matchevent/dto/MatchEventDto.java"
  [organization]="organization/dto/OrganizationDto.java"
  [organizationtransition]="organizationtransition/dto/OrganizationTransitionDto.java"
  [person]="person/dto/PersonDto.java"
  [player]="player/dto/PlayerDto.java"
  [pointsystem]="pointsystem/dto/PointSystemDto.java"
  [qualificationoutcome]="qualificationoutcome/dto/QualificationOutcomeDto.java"
  [registration]="registration/dto/RegistrationDto.java"
  [season]="season/dto/SeasonDto.java"
  [seasonparticipation]="seasonParticipation/dto/SeasonParticipationDto.java"
  [staff]="staff/dto/StaffDto.java"
  [stage]="stage/dto/StageDto.java"
  [stageformat]="stageFormat/dto/StageFormatDto.java"
  [standing]="standing/dto/StandingDto.java"
  [team]="team/dto/TeamDto.java"
  [teambrand]="teambrand/dto/TeamBrandDto.java"
  [teamvenue]="teamvenue/dto/TeamVenueDto.java"
  [tournament]="tournament/dto/TournamentDto.java"
  [tournamentseason]="tournamentSeason/dto/TournamentSeasonDto.java"
  [venue]="venue/dto/VenueDto.java"
)


# If an argument is provided, only generate that CSV
if [[ $# -ge 1 ]]; then
  entity="$1"
  dto_path="$DTO_DIR/${ENTITY_DTO_MAP[$entity]}"
  csv_path="$CSV_DIR/${entity}.csv"
  if [[ -z "${ENTITY_DTO_MAP[$entity]}" ]]; then
    echo "Unknown entity: $entity"
    echo "Available entities: ${!ENTITY_DTO_MAP[@]}"
    exit 1
  fi
  if [[ -f "$dto_path" ]]; then
    fields=$(grep -Eo 'private [^;]+;' "$dto_path" | sed -E 's/private [^ ]+ ([a-zA-Z0-9_]+);/\1/' | grep -v '^$' | grep -v -E '^(id|createdAt|createdBy|updatedAt|updatedBy)$' | grep -v -E 'Id$' | tr '\n' ',')
    fields=${fields%,}
    echo "$fields" > "$csv_path"
    echo "Generated $csv_path with fields: $fields (base fields and Id references excluded)"
  else
    echo "DTO not found for $entity: $dto_path"
    exit 1
  fi
else
  for entity in "${!ENTITY_DTO_MAP[@]}"; do
    dto_path="$DTO_DIR/${ENTITY_DTO_MAP[$entity]}"
    csv_path="$CSV_DIR/${entity}.csv"
    if [[ -f "$dto_path" ]]; then
      fields=$(grep -Eo 'private [^;]+;' "$dto_path" | sed -E 's/private [^ ]+ ([a-zA-Z0-9_]+);/\1/' | grep -v '^$' | grep -v -E '^(id|createdAt|createdBy|updatedAt|updatedBy)$' | grep -v -E 'Id$' | tr '\n' ',')
      fields=${fields%,}
      echo "$fields" > "$csv_path"
      echo "Generated $csv_path with fields: $fields (base fields and Id references excluded)"
    else
      echo "DTO not found for $entity: $dto_path"
    fi
  done
fi
