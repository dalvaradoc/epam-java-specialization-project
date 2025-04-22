# Gym App

This is a simple app that can store and retreive information about Trainees, Trainers and Training plans using Spring Boot.

For the persistence of the data, it uses an in-memory database (H2). The database can persist the data but in the
application.properties it is set to drop and create every time the app is restarted. It has some mock data that is inside
data.sql file and it is loaded when the app starts.

## Running

It requires java 17 and mvn 3.9.9+ to be able to run the app

```bash
mvn spring-boot:run
```

## Usage

Right now it has no endpoint to access the api outside of the app itself.

However, the test can be used to show loggs of the app working.

To run the tests, the following command can be used:

```bash
mvn test
```