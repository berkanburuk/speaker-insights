# Speaker Insights

**Speaker Insights** provides analytical insights into speaker influence for TedTalks and other conferences. The application calculates speaker influence scores based on conference views, likes, and dates, and exposes REST APIs to fetch influential speakers overall and by year.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Testing](#testing)

---

## Features

- Import TedTalk data from CSV.
- Calculate influence scores for speakers based on likes, views, and logarithmic scaling.
- List all influential speakers sorted by influence.
- Find the most influential speaker per year.
- REST APIs for integration with frontend or external systems.

---

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3
- **Database:** PostgreSQL (dev), PostgreSQL (for tests)
- **ORM:** Spring Data JPA / Hibernate
- **Connection Pool:** HikariCP
- **CSV Parsing:** Apache Commons CSV, OpenCSV
- **Testing:** JUnit 5, Testcontainers
- **Build Tool:** Gradle
- **Containerization**: Docker, Docker Compose

---

## Getting Started

### Prerequisites

- Java 17
- PostgreSQL 15
- Gradle 8+
- Containerization: Docker

---

## Configuration
- Check out [dev configs](src/main/resources/application.properties)
- Check out [test configs](src/test/resources/application-test.properties)

---

## Running The Application

The project provides Docker Compose files for both development and test environments.

#### Development Environment: 
- ```docker compose -f docker-compose-dev.yaml up -d```

#### Test Environment:
- ```docker compose -f docker-compose-test.yaml up -d``` 

Docker will spin up PostgreSQL 15 with correct permissions and initialization. Example logs:

#### Run Application
Use idea to run application 
Click Run Button in idea

#### Run via Gradle
Run via Gradle or run tests directly from your IDE
- ```./gradlew bootRun```
  
## Testing
To run test cases, execute the following command
- ```./gradlew test```