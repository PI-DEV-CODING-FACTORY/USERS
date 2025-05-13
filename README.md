# Users Management Microservice

## Description
This microservice is part of the Coding Factory website, handling all user-related operations and management. It's built using Spring Boot framework and provides RESTful APIs for user authentication, authorization, and profile management.

### Key Features
- User registration and authentication
- Profile management for students and instructors
- Role-based access control (Admin, Student, Instructor)
- User data persistence with Spring Data JPA
- Secure password handling
- Integration with other microservices

## Technology Stack
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL (hosted on Neon DB)
- Maven
- JWT for authentication

## Database
The application's PostgreSQL database is hosted on Neon DB, providing:
- Serverless deployment
- Automatic scaling
- Built-in backups
- High availability

## Class Diagram
![Class Diagram](./diagrams/digram_class.png)

## Screenshots
![User Registration Interface](./screenshots/userMS_screenshot.png)

## Setup and Installation
1. Clone the repository
2. Configure application.properties with your Neon DB credentials
3. Run `mvn clean install`
4. Start the application with `mvn spring-boot:run`

## API Documentation
The API documentation is available at `http://localhost:8080/swagger-ui.html` when running the application locally.

## Dependencies
- Java 17 or higher
- Maven 3.8+
- PostgreSQL 17