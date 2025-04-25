# Gym App

The **Gym App** is a Spring Boot application designed to manage information about trainees, trainers, and training plans. It uses an in-memory H2 database for data persistence and includes mock data for testing purposes.

## Features

- **Trainee Management**: Create, update, retrieve, and delete trainee information.
- **Custom Validation**: Includes custom validation annotations for fields like `username` and `name`.
- **Aspect-Oriented Programming (AOP)**: Implements security checks using custom annotations like `@CheckCredentials`.
- **In-Memory Database**: Uses H2 for persistence with mock data preloaded via `data.sql`.
- **Logging**: Logs application activity for better debugging and monitoring.

## Prerequisites

To run the application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.9.9** or higher

## Running the Application

To start the application, use the following command:

```bash
mvn spring-boot:run
```

This will start the application and initialize the H2 database with mock data.

### Database Configuration

The application uses an H2 in-memory database. By default, the database schema is dropped and recreated on every restart. You can modify this behavior in the `application.properties` file.

## Testing

The application includes unit tests to validate its functionality. To run the tests, use:

```bash
mvn test
```

Logs will display the application's behavior during the tests.

## Validation

The application uses **Jakarta Bean Validation** for validating input data. Custom annotations like `@UsernameConstraint` and `@NameLikeStringConstraint` ensure that fields meet specific requirements.

### Example Validation Rules

- **Username**: Must follow the pattern `firstName.lastName(#number)?`.
- **NameLike**: Must be only letters, spaces and apotrophes with length between 3 and 30.

## Usage

Currently, the application does not expose any REST API endpoints for external access. However, the functionality can be tested using the provided unit tests.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **H2 Database**: In-memory database for data persistence.
- **Jakarta Validation**: For input validation.
- **Lombok**: To reduce boilerplate code.
- **Maven**: For dependency management and build automation.

## Author

Diego Alejandro Alvarado Chaparro