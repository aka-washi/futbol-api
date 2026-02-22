# Bootstrap Endpoint Documentation

The Bootstrap API provides endpoints to bulk-load data into the application from either JSON data in the request body or from files in the `input` directory.

## Endpoints

### Load Data
**POST** `/api/v1/bootstrap/load`

Load data for one or more entity types.

### Health Check
**POST** `/api/v1/bootstrap/health`

Check the health status of the bootstrap service.

## Supported Entity Types

- `country` - Countries
- `organization` - Organizations  
- `pointsystem` / `point-system` - Point Systems
- `season` - Seasons
- `person` - Persons
- `player` - Players
- `team` - Teams

## Request Format

### Option 1: Load from Request Body

```json
{
  "entityType": "country",
  "data": [
    {
      "name": "Mexico",
      "code": "MX",
      "isoCode": "MEX",
      "displayName": "Mexico"
    },
    {
      "name": "Brazil",
      "code": "BR",
      "isoCode": "BRA",
      "displayName": "Brazil"
    }
  ],
  "clearExisting": false
}
```

### Option 2: Load from File

```json
{
  "entityType": "country",
  "dataFilePath": "country.json",
  "clearExisting": false
}
```

If you provide a `dataFilePath`, the system will look for the file in the `input` directory relative to the application root.

### Option 3: Load from Default File

```json
{
  "entityType": "country"
}
```

If you only provide the `entityType`, the system will automatically look for a file named `{entityType}.json` in the `input` directory.

## Request Fields

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `entityType` | string | Yes | The type of entity to load |
| `data` | array/object | No | The data to load (can be single object or array) |
| `dataFilePath` | string | No | Path to data file relative to `input` directory |
| `clearExisting` | boolean | No | Whether to clear existing data before loading (default: false) |

## Response Format

### Success Response

```json
{
  "status": "SUCCESS",
  "entityType": "Country",
  "successCount": 10,
  "failureCount": 0,
  "message": "Loaded 10 Country entities successfully, 0 failed"
}
```

### Partial Success Response

```json
{
  "status": "PARTIAL_SUCCESS",
  "entityType": "Country",
  "successCount": 8,
  "failureCount": 2,
  "message": "Loaded 8 Country entities successfully, 2 failed",
  "errors": [
    "Failed to load Country: Duplicate entity",
    "Failed to load Country: Validation failed"
  ]
}
```

### Error Response

```json
{
  "status": "ERROR",
  "entityType": "country",
  "message": "Bootstrap failed: File not found"
}
```

## Example Usage

### Using cURL

#### Load countries from request body:
```bash
curl -X POST http://localhost:8080/api/v1/bootstrap/load \
  -H "Content-Type: application/json" \
  -d '{
    "entityType": "country",
    "data": [
      {
        "name": "Mexico",
        "code": "MX",
        "isoCode": "MEX",
        "displayName": "Mexico"
      }
    ]
  }'
```

#### Load countries from file:
```bash
curl -X POST http://localhost:8080/api/v1/bootstrap/load \
  -H "Content-Type: application/json" \
  -d '{
    "entityType": "country",
    "dataFilePath": "country.json"
  }'
```

#### Load countries from default file:
```bash
curl -X POST http://localhost:8080/api/v1/bootstrap/load \
  -H "Content-Type: application/json" \
  -d '{
    "entityType": "country"
  }'
```

### Using Postman

1. Create a new POST request to `http://localhost:8080/api/v1/bootstrap/load`
2. Set Headers:
   - `Content-Type: application/json`
3. Set Body (raw JSON):
   ```json
   {
     "entityType": "country",
     "data": [...]
   }
   ```

## File Format Examples

### Country Data (`input/country.json`)

```json
[
  {
    "name": "Mexico",
    "code": "MX",
    "isoCode": "MEX",
    "displayName": "Mexico"
  },
  {
    "name": "Brazil",
    "code": "BR",
    "isoCode": "BRA",
    "displayName": "Brazil"
  }
]
```

### Player Data (`input/player.json`)

```json
[
  {
    "person": {
      "firstName": "Lionel",
      "lastName": "Messi",
      "birthDate": "1987-06-24",
      "gender": "Male",
      "birthPlace": "Rosario",
      "nationalityCountryId": 1,
      "birthCountryId": 1
    },
    "position": "Forward",
    "preferredFoot": "Left",
    "active": true
  }
]
```

## Notes

1. The `data` field can accept either a single object or an array of objects
2. If both `data` and `dataFilePath` are provided, `data` takes precedence
3. File paths are relative to the `input` directory in the application root
4. The bootstrap service uses transactional processing for data integrity
5. Duplicate detection is handled by the individual entity services
6. Validation is performed according to each entity's DTO validation rules

## Error Handling

The bootstrap service will:
- Continue processing even if some records fail
- Collect all errors and return them in the response
- Rollback the entire transaction if a critical error occurs
- Log detailed error information for troubleshooting
