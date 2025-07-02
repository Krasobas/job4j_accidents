# Traffic Accident Reporting System

## Project Overview
The "Accidents" system is a Spring Boot application designed for reporting and managing traffic accident cases. It facilitates communication between citizens and traffic authorities through a structured workflow.

## System Roles
1. **Citizens**:
    - Submit accident reports
    - Upload supporting evidence
    - Track case progress

2. **Traffic Officers**:
    - Review accident submissions
    - Process cases through workflow stages
    - Generate official reports

## Core Features
- Accident case creation with:
    - Location details (address/GPS coordinates)
    - Involved vehicle information
    - Accident description
    - Photographic evidence
- Case status lifecycle:
    - New
    - In Review
    - Requires Additional Info
    - Closed
- Dashboard interface with:
    - Case management tools
    - Advanced filtering
    - Statistical overviews

## Technical Architecture
- **Framework**: Spring Boot 3.2
- **Authentication**: Spring Security with OAuth2
- **Persistence**: JPA/Hibernate with PostgreSQL
- **Storage**: DigitalOcean Spaces for media
- **API Documentation**: OpenAPI 3.0

## REST API Structure
```
/api
  /accidents [GET, POST]
  /accidents/{id} [GET, PUT]
  /accidents/{id}/status [PATCH]
  /accidents/search [GET]
  /accidents/statistics [GET]
```

## Quick Start
1. Configure environment:
```bash
export DB_URL=jdbc:postgresql://localhost:5432/accidents
export SPRING_PROFILES_ACTIVE=dev
```

2. Launch application:
```bash
mvn spring-boot:run
```

## Configuration Options
`application.yml` example:
```yaml
storage:
  max-file-size: 5MB
  allowed-types: [image/jpeg, image/png]

notification:
  email-enabled: true
  sms-enabled: false
```

## Security Implementation
- Endpoint authorization
- Data validation
- CSRF protection
- Rate limiting
- Audit trails

## Roadmap
- V1.0: Basic reporting functionality
- V1.1: Officer mobile interface
- V1.2: Automated document generation
- V1.3: Integration with vehicle registration databases