# Auth-service

This project is a REST API built on Java, Spring, PostgreSQL. Spring Security and JWT were used for authentication control. RabbitMQ broker configured for asynchronous connection.

🎯 Objective: Study microservices, design patterns, connection, lifecycle and security.

## Technologies

- Java 17
- Spring Boot 3.0
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- JPA/Hibernate
- Docker (containerization)
- RabbitMQ

## Features

- User registration and authentication
- User update
- Role-based access control
- CRUD operations
- Send confirmation email

## How to run

### Prerequisites

- [JDK 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/) (optional)

### Database configuration

Create a PostgreSQL database and update the `application.properties` file with your credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Running

Clone this repository:

```sh
git clone https://github.com/login_authAPI.git
cd
mvn clean install
mvn spring-boot:run
```

The API will be accessible at `http://localhost:8080`

# Endpoints
### Public Access
`POST` /users/register - Register an user in the database. Request Body:

```json
{
  "name": "String",
  "birthDate": "LocalDate",
  "email": "String",
  "password": "String"
}
```

Response (201 CREATED)

###
`POST` /auth/login - Login with user credentials. Request Body:
```json
{
    "login": "String",
    "password": "String"
}
```

Response (200 OK):
```json
{
    "token": "<token>"
} 
```

### Authorized access
Requirements:
- Valid registered user;
- `Authorization: Bearer <token>` in request headers;

    Errors: 
    - 403 FORBIDDEN (Insufficient permissions);
    - 401 UNAUTHORIZED (expired, missing or invalid token);

`GET` /users/list - List all registered users. Response (200 OK):
```json
[
    {
        "id": "UUID",
        "name": "String",
        "birthDate": "LocalDate",
        "email": "String",
        "role": "ENUM{USER, ADMIN}"
    }
]
```
`DELETE` users/delete/{uuid} - Delete an user by the UUID.

Response (204 NO CONTENT)
