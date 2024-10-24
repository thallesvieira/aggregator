# Aggregator
This project is an application developed with Java, Spring Boot, JPA, H2, Spring Security, Flyway, SQL, GitHub Actions, Docker, and SonarQube. The system connects to a third-party service to fetch contact information and provides an API to consume this data.

## Technologies Used
* Java
* Spring Boot
* JPA
* H2 Database
* Spring Security
* Flyway
* SQL
* GitHub Actions
* Docker
* SonarQube 

## Requirements to Run Locally
To run the application locally, you can choose between two approaches: using environment variables or running Docker Compose.

## 1. Running with Environment Variables
   Set the following environment variables in your local environment:

API_CONTACT_URL=https://k-messages-api.herokuapp.com/api/v1
API_CONTACT_TOKEN=Bearer J7ybt6jv6pdJ4gyQP9gNonsY
DATABASE_URL=jdbc:h2:mem:testdb
DATABASE_USERNAME=sa
DATABASE_PASSWORD=password


With the variables set, you can run the project locally via your IDE or using Gradle:
* ./gradlew bootRun

The application will be available on port 8080.

## 2. Running with Docker Compose
   Alternatively, you can use Docker Compose to bring up the application and all its dependencies with a single command:

* docker-compose up

This command will start the application on port 8080, and the H2 database will be automatically configured.

## Accessing the API Documentation
You can access the API documentation and explore the available endpoints using Swagger. Once the application is running, visit the following address:

* http://localhost:8080/swagger-ui/index.htm

## Authentication
To access protected endpoints such as /contacts, an authentication token is required. There are two ways to obtain this token:

### 1. Service Token
   You can use the following service token to directly access protected endpoints:

* eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBcGkgQWdncmVnYXRvciIsInN1YiI6IjEifQ.0shfB3b1OJnSFl0PXPff9D8eB5kJjQmH4Pq6_YGDXt4


Simply include it in the request headers like:

* Authorization: Bearer TOKEN


## 2. Generating a Token via Login
   A default user is already created in the system with the following credentials:

* Username: user
* Password: password

You can generate a user token using the /auth/login endpoint. To do so, make a POST request with the following payload:

{
"username": "user",
"password": "password"
}

This will return a token that can be used to access protected endpoints such as /contacts.

## Endpoints
* /contacts: List of contacts (authentication token required)
* /auth/login: User authentication

## Database
The project uses an in-memory H2 database to facilitate local development. 
Database access variables are in environment variables, making it easy to switch to a different database.
