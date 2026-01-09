# Team Tasks API

A lightweight task management REST API built with Spring Boot and Java 17. This application provides a straightforward backend solution for tracking and organizing tasks within teams or personal projects.

## Features

- **Complete CRUD Operations**: Create, read, update, and delete tasks
- **Smart Filtering**: Filter tasks by status, priority, or search by keywords
- **Priority Management**: Organize tasks with Low, Medium, and High priority levels
- **Status Tracking**: Track task progress through Todo, Doing, and Done states
- **Deadline Management**: Set and track task deadlines
- **Automatic Timestamps**: Automatic creation and update timestamp tracking
- **Request Validation**: Built-in validation for all API requests
- **Centralized Exception Handling**: Consistent error responses across all endpoints

## Tech Stack

- **Java 17**
- **Spring Boot 3.3.4**
- **Spring Data JPA** with Hibernate
- **MySQL 8.0**
- **Maven** for dependency management
- **Docker Compose** for local database setup

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose (for database)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Team-Tasks-Api
```

### 2. Start the Database

```bash
docker-compose up -d
```

This will start a MySQL container with the following configuration:
- **Database**: teamtasks
- **Username**: teamtasks
- **Password**: teamtasks-password
- **Port**: 3306

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

Or if using Windows:
```bash
mvnw.cmd spring-boot:run
```

The API will be available at `http://localhost:8080`

## API Endpoints

### Create a Task

```http
POST /tasks
Content-Type: application/json

{
  "title": "Finish lab report",
  "description": "Lab 4, update diagrams",
  "priority": "High",
  "status": "Todo",
  "deadline": "2026-01-10"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Finish lab report",
  "description": "Lab 4, update diagrams",
  "status": "Todo",
  "priority": "High",
  "deadline": "2026-01-10",
  "createdAt": "2026-01-07T19:20:00Z",
  "updatedAt": "2026-01-07T19:20:00Z"
}
```

### List All Tasks

```http
GET /tasks
```

**Optional Query Parameters:**
- `status` - Filter by status (TODO, Doing, DONE)
- `priority` - Filter by priority (LOW, MEDIUM, HIGH)
- `q` - Search in title and description (case-insensitive)

**Example:**
```http
GET /tasks?status=Todo&priority=High&q=report
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "title": "Finish lab report",
    "description": "Lab 4, update diagrams",
    "status": "Todo",
    "priority": "High",
    "deadline": "2026-01-10",
    "createdAt": "2026-01-07T19:20:00Z",
    "updatedAt": "2026-01-07T19:20:00Z"
  }
]
```

### Get Task by ID

```http
GET /tasks/{id}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Finish lab report",
  "description": "Lab 4, update diagrams",
  "status": "Todo",
  "priority": "High",
  "deadline": "2026-01-10",
  "createdAt": "2026-01-07T19:20:00Z",
  "updatedAt": "2026-01-07T19:20:00Z"
}
```

**Response (404 Not Found):**
```json
{
  "message": "Task not found"
}
```

### Update a Task

```http
PATCH /tasks/{id}
Content-Type: application/json

{
  "status": "Done",
  "description": "Lab 4, updated all diagrams and submitted"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Finish lab report",
  "description": "Lab 4, updated all diagrams and submitted",
  "status": "Done",
  "priority": "High",
  "deadline": "2026-01-10",
  "createdAt": "2026-01-07T19:20:00Z",
  "updatedAt": "2026-01-08T10:30:00Z"
}
```

### Delete a Task

```http
DELETE /tasks/{id}
```

**Response (204 No Content)**

## Data Model

### Task Entity

| Field       | Type         | Description                           |
|-------------|--------------|---------------------------------------|
| id          | Long         | Auto-generated unique identifier      |
| title       | String       | Task title (3-120 characters)         |
| description | String       | Task description (max 1000 characters)|
| status      | Enum         | TODO, IN_PROGRESS, DONE               |
| priority    | Enum         | LOW, MEDIUM, HIGH                     |
| deadline    | LocalDate    | Optional deadline                     |
| createdAt   | Instant      | Automatically set on creation         |
| updatedAt   | Instant      | Automatically updated on modification |

## Validation Rules

- **Title**: Required, 3-120 characters
- **Description**: Optional, max 1000 characters
- **Status**: Must be one of: TODO, IN_PROGRESS, DONE
- **Priority**: Must be one of: LOW, MEDIUM, HIGH

## Project Structure

```
src/main/java/md/igor/teamtasks/
├── controller/          # REST controllers
├── dto/                 # Data Transfer Objects
│   ├── request/        # Request DTOs
│   └── response/       # Response DTOs
├── entity/             # JPA entities
├── exception/          # Exception handlers
├── repository/         # Spring Data repositories
└── service/            # Business logic layer
```

## Configuration

Database configuration can be modified in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/teamtasks
    username: teamtasks
    password: teamtasks-password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## Future Enhancements

- Authentication and authorization
- Role-based access control
- Task assignment to users
- Task comments and attachments
- Email notifications for deadlines
- Task categories and tags

## Contributing

Feel free to submit issues and pull requests.

## License

This project is open source and available under the [MIT License](LICENSE).
