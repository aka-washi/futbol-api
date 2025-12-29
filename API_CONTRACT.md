# REST API Contract Documentation

## Overview

This document serves as a detailed contract specification for the REST API, defining what external applications can expect when integrating with this service. It includes comprehensive endpoint specifications, response formats, error handling, and integration guidelines.

**Base URL:** `http://localhost:8080`
**Version:** v1
**Content-Type:** `application/json`
**CORS:** Enabled for all origins

---

## Standard Response Format

All API responses follow a consistent structure:

### Single Resource Response
```json
{
  "success": boolean,
  "message": string,
  "data": T | null,
  "error": {
    "code": string,
    "message": string
  } | null,
  "timestamp": "YYYY-MM-DDTHH:mm:ss"
}
```

### Pageable Resource Response
```json
{
  "success": boolean,
  "message": string,
  "data": {
    "content": T[],
    "sort": {
      "sorted": boolean,
      "empty": boolean,
      "unsorted": boolean
    },
    "pageable": {
      "sort": {
        "sorted": boolean,
        "empty": boolean,
        "unsorted": boolean
      },
      "pageNumber": number,
      "pageSize": number,
      "offset": number,
      "paged": boolean,
      "unpaged": boolean
    },
    "totalElements": number,
    "totalPages": number,
    "first": boolean,
    "last": boolean,
    "hasNext": boolean,
    "hasPrevious": boolean,
    "numberOfElements": number,
    "size": number,
    "number": number,
    "empty": boolean
  },
  "error": {
    "code": string,
    "message": string
  } | null,
  "timestamp": "YYYY-MM-DDTHH:mm:ss",
  "pagination": {
    "page": number,
    "size": number,
    "totalElements": number,
    "totalPages": number,
    "first": boolean,
    "last": boolean,
    "hasNext": boolean,
    "hasPrevious": boolean
  }
}
```

**Field Descriptions:**
- `success`: Indicates if the operation was successful
- `message`: Human-readable message describing the operation result
- `data`: The actual response data (null for errors)
  - For pageable responses: Contains Spring Data Page object with `content` array and pagination metadata
  - For single resources: Contains the resource object directly
- `error`: Error details when `success` is false (null for successful operations)
- `timestamp`: ISO 8601 formatted timestamp of the response
- `pagination`: Simplified pagination metadata (only present for pageable endpoints)

---

## Data Models

### Core Principles

**Entity DTOs:** All entities are represented as Data Transfer Objects (DTOs) that encapsulate the data structure for API communication.

**Standard Fields:** All entities include common audit fields (`id`, `createdAt`, `updatedAt`) and follow consistent validation patterns.

**Nested Objects:** Some entities may contain nested objects representing relationships to other entities.

**Note:** Refer to the API responses for complete field specifications and validation rules for each entity type.

---

## API Endpoint Patterns

All entity endpoints follow consistent RESTful patterns. Replace `{entity}` with the actual entity name (e.g., `persons`, `players`).

### 1. Get All Entities

**Endpoint:** `GET /api/{entity}`

**Query Parameters:**
| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `page` | integer | No | 0 | Page number (0-indexed) |
| `size` | integer | No | 20 | Number of items per page (max 100) |
| `sortBy` | string | No | "id" | Field name to sort by |
| `sortDir` | string | No | "asc" | Sort direction: "asc" or "desc" |
| `{field}` | string | No | - | Filter by specific field (partial match for strings) |
| `{field}Min` | number/date | No | - | Minimum value filter for numeric/date fields |
| `{field}Max` | number/date | No | - | Maximum value filter for numeric/date fields |
| `{field}From` | date | No | - | Filter by date field from (YYYY-MM-DD) |
| `{field}To` | date | No | - | Filter by date field to (YYYY-MM-DD) |

**Note:** Available filter fields vary by entity. Check endpoint documentation for specific field names.

**Response:**
- **200 OK:** Returns paginated list of entities
- **400 Bad Request:** Invalid query parameters

**Example Request:**
```
GET /api/{entity}?page=0&size=10&sortBy={field}&sortDir=asc&{filterField}={value}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "content": [
      {
        "id": 1,
        "field1": "value1",
        "field2": "value2",
        "createdAt": "2025-12-01T10:30:00",
        "updatedAt": "2025-12-01T10:30:00"
      }
    ],
    "sort": {
      "sorted": true,
      "empty": false,
      "unsorted": false
    },
    "pageable": {
      "sort": {
        "sorted": true,
        "empty": false,
        "unsorted": false
      },
      "pageNumber": 0,
      "pageSize": 10,
      "offset": 0,
      "paged": true,
      "unpaged": false
    }
  },
  "timestamp": "2025-12-01T10:30:00",
  "pagination": {
    "page": 0,
    "size": 10,
    "totalElements": 25,
    "totalPages": 3,
    "first": true,
    "last": false,
    "hasNext": true,
    "hasPrevious": false
  }
}
```

### 2. Get Entity by ID

**Endpoint:** `GET /api/{entity}/{id}`

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | long | Yes | Entity ID |

**Response:**
- **200 OK:** Returns the entity
- **404 Not Found:** Entity not found
- **400 Bad Request:** Invalid ID format

### 3. Get Entity by Unique Field

**Endpoint:** `GET /api/{entity}/{field}/{value}`

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `field` | string | Yes | Unique field name |
| `value` | string | Yes | Field value |

**Response:**
- **200 OK:** Returns the entity
- **404 Not Found:** Entity not found
- **400 Bad Request:** Invalid field or value

### 4. Create Entity

**Endpoint:** `POST /api/{entity}`

**Request Body:** Entity DTO object with required and optional fields as per entity specification

**Response:**
- **201 Created:** Entity created successfully
- **400 Bad Request:** Validation errors or duplicate unique fields
- **500 Internal Server Error:** Server error

### 5. Update Entity

**Endpoint:** `PUT /api/{entity}/{id}`

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | long | Yes | Entity ID |

**Request Body:** Same as Create Entity

**Response:**
- **200 OK:** Entity updated successfully
- **404 Not Found:** Entity not found
- **400 Bad Request:** Validation errors
- **500 Internal Server Error:** Server error

### 6. Delete Entity

**Endpoint:** `DELETE /api/{entity}/{id}`

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | long | Yes | Entity ID |

**Response:**
- **200 OK:** Entity deleted successfully
- **404 Not Found:** Entity not found
- **400 Bad Request:** Invalid ID or constraint violation
- **500 Internal Server Error:** Server error

### 7. Check Entity Exists

**Endpoint:** `GET /api/{entity}/{id}/exists`

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | long | Yes | Entity ID |

**Response:**
- **200 OK:** Returns boolean indicating existence

**Example Response:**
```json
{
  "success": true,
  "message": "Operation successful",
  "data": true,
  "timestamp": "2025-12-01T10:30:00"
}
```

### 8. Get Filtered Entities (Custom Endpoints)

**Pattern:** `GET /api/{entity}/{filter-criteria}`

Some entities may provide additional filtering endpoints for common use cases.

**Examples:**
- `GET /api/{entity}/active` - Get active entities
- `GET /api/{entity}/{category}/{value}` - Get entities by category

**Response:**
- **200 OK:** Returns list or paginated list of entities
- **400 Bad Request:** Invalid parameters

---

## Error Handling

### Standard Error Codes

| Code | HTTP Status | Description |
|------|-------------|-------------|
| `FETCH_ERROR` | 400 | Error retrieving resource |
| `CREATION_ERROR` | 400 | Error creating resource |
| `UPDATE_ERROR` | 400 | Error updating resource |
| `DELETE_ERROR` | 400 | Error deleting resource |
| `SEARCH_ERROR` | 400 | Error searching resources |
| `CHECK_ERROR` | 400 | Error checking resource existence |
| `DUPLICATE_RESOURCE` | 409 | Resource already exists |
| `RESOURCE_NOT_FOUND` | 404 | Resource not found |
| `VALIDATION_ERROR` | 400 | Input validation failed |
| `SERVER_ERROR` | 500 | Internal server error |

### Validation Error Response Example

```json
{
  "success": false,
  "message": "Validation failed for object='entityDTO'. Error count: 2",
  "data": null,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "field1: Field1 is required; field2: Field2 should be valid"
  },
  "timestamp": "2025-12-01T10:30:00"
}
```

### Resource Not Found Example

```json
{
  "success": false,
  "message": "Entity not found with id: 999",
  "data": null,
  "error": {
    "code": "RESOURCE_NOT_FOUND",
    "message": "Entity not found with id: 999"
  },
  "timestamp": "2025-12-01T10:30:00"
}
```

### Duplicate Resource Example

```json
{
  "success": false,
  "message": "Unique field already exists: value123",
  "data": null,
  "error": {
    "code": "DUPLICATE_RESOURCE",
    "message": "Unique field already exists: value123"
  },
  "timestamp": "2025-12-01T10:30:00"
}
```

---

## Integration Guidelines

### Content-Type Requirements
- **Request:** `Content-Type: application/json` for POST/PUT operations
- **Response:** Always `Content-Type: application/json`

### Date Format Standards
- **Date:** `YYYY-MM-DD` (e.g., "2025-12-01")
- **DateTime:** `YYYY-MM-DDTHH:mm:ss` (e.g., "2025-12-01T10:30:00")

### Pagination Guidelines

1. **Page Numbers:** 0-indexed (first page is 0)
2. **Default Page Size:** 20 items
3. **Maximum Page Size:** 100 items
4. **Sort Fields:** Use actual field names from DTOs
5. **Sort Direction:** "asc" or "desc" (case-insensitive)

**Example Pagination Request:**
```
GET /api/{entity}?page=2&size=25&sortBy={field}&sortDir=desc
```

### Filtering Best Practices

1. **String Filters:** Partial matches, case-insensitive
2. **Date Ranges:** Use `From` and `To` parameters for inclusive ranges
3. **Numeric Ranges:** Use `Min` and `Max` parameters for inclusive ranges
4. **Boolean Filters:** Use `true`/`false` values
5. **Multiple Filters:** All filters are combined with AND logic

### HTTP Status Codes

| Status Code | Usage |
|-------------|-------|
| 200 OK | Successful GET, PUT, DELETE operations |
| 201 Created | Successful POST operations |
| 400 Bad Request | Validation errors, invalid parameters |
| 404 Not Found | Resource not found |
| 409 Conflict | Duplicate resource |
| 500 Internal Server Error | Unexpected server errors |

### Client Implementation Examples

#### JavaScript/TypeScript

```javascript
// API Client Interface
interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T | null;
  error?: {
    code: string;
    message: string;
  };
  timestamp: string;
  pagination?: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    first: boolean;
    last: boolean;
    hasNext: boolean;
    hasPrevious: boolean;
  };
}

// Example API call
async function getEntities(entityType: string, page = 0, size = 20): Promise<ApiResponse<Page<EntityDTO>>> {
  const response = await fetch(`/api/${entityType}?page=${page}&size=${size}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  });

  return response.json();
}

// Create entity example
async function createEntity(entityType: string, entity: EntityDTO): Promise<ApiResponse<EntityDTO>> {
  const response = await fetch(`/api/${entityType}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(entity)
  });

  return response.json();
}
```

#### Python

```python
import requests
from typing import Optional, Dict, Any

class ApiClient:
    def __init__(self, base_url: str = "http://localhost:8080"):
        self.base_url = base_url

    def get_entities(self, entity_type: str, page: int = 0, size: int = 20, **filters) -> Dict[str, Any]:
        params = {"page": page, "size": size}
        params.update(filters)

        response = requests.get(f"{self.base_url}/api/{entity_type}", params=params)
        return response.json()

    def create_entity(self, entity_type: str, entity_data: Dict[str, Any]) -> Dict[str, Any]:
        headers = {"Content-Type": "application/json"}
        response = requests.post(
            f"{self.base_url}/api/{entity_type}",
            json=entity_data,
            headers=headers
        )
        return response.json()
```

#### Java

```java
// Using RestTemplate or WebClient
@Service
public class ApiClient {

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://localhost:8080";

    public <T> ApiResponse<Page<T>> getEntities(String entityType, int page, int size, Class<T> entityClass) {
        String url = baseUrl + "/api/" + entityType + "?page=" + page + "&size=" + size;

        return restTemplate.getForObject(url, ApiResponse.class);
    }

    public <T> ApiResponse<T> createEntity(String entityType, T entity, Class<T> entityClass) {
        String url = baseUrl + "/api/" + entityType;

        return restTemplate.postForObject(url, entity, ApiResponse.class);
    }
}
```

---

## Testing and Validation

### cURL Examples

**Get All Entities:**
```bash
curl -X GET "http://localhost:8080/api/{entity}?page=0&size=10" \
     -H "Content-Type: application/json"
```

**Create Entity:**
```bash
curl -X POST "http://localhost:8080/api/{entity}" \
     -H "Content-Type: application/json" \
     -d '{
       "field1": "value1",
       "field2": "value2",
       "field3": "value3"
     }'
```

**Search Entities with Filters:**
```bash
curl -X GET "http://localhost:8080/api/{entity}?{filterField}={value}&{booleanField}=true" \
     -H "Content-Type: application/json"
```

### Health Check

The API includes Spring Boot Actuator for health monitoring:

**Endpoint:** `GET /actuator/health`

**Response:**
```json
{
  "status": "UP"
}
```

---

## Rate Limiting and Performance

### Current Limitations
- **No rate limiting implemented** (consider implementing for production)
- **Maximum page size:** 100 items per request
- **Default timeout:** Standard Spring Boot defaults (30 seconds)

### Performance Considerations
- Use pagination for large datasets
- Implement proper filtering to reduce response sizes
- Consider caching for frequently accessed data
- Monitor response times and adjust page sizes accordingly

---

## Security Considerations

### Current Security Features
- **CORS:** Enabled for all origins (`*`)
- **Input Validation:** Comprehensive validation on all input fields
- **SQL Injection Protection:** JPA/Hibernate provides protection

### Security Recommendations for Production
1. Implement authentication (JWT tokens recommended)
2. Restrict CORS to specific domains
3. Add rate limiting
4. Implement HTTPS
5. Add request/response logging
6. Consider field-level permissions

---

## Versioning Strategy

**Current Version:** v1 (implicit in base path)

For future versions, consider:
- Path versioning: `/api/v2/persons`
- Header versioning: `Accept: application/vnd.eagle.v2+json`
- Parameter versioning: `/api/persons?version=2`

---

## Support and Troubleshooting

### Common Issues

1. **400 Bad Request - Validation Error**
   - Check required fields are provided
   - Verify data format (especially dates)
   - Ensure field length limits are respected

2. **404 Not Found**
   - Verify the resource ID exists
   - Check the endpoint URL is correct

3. **409 Conflict - Duplicate Resource**
   - Check for unique field constraints specific to the entity type
   - Verify that unique identifiers are not already in use

### Debug Information

Enable debug logging by setting:
```yaml
logging:
  level:
    org.springframework.web: DEBUG
    org.hibernate.SQL: DEBUG
```

### Contact Information

For API support and questions:
- **Documentation:** This file and `API_DOCUMENTATION.md`
- **Source Code:** Check the controller classes for implementation details
- **Configuration:** See `application.yml` for database and server settings

---

*Last Updated: December 1, 2025*
*API Version: v1*
*Document Version: 1.0*
