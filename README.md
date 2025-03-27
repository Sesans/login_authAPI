# User management API

This project is a REST API built on Java, Spring, PostgreSQL. Spring Security and JWT were used for authentication control. RabbitMQ broker configured for asynchronous connection.

🎯 Objective: Study microservices design patterns, connection, lifecycle and security.

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
- Send e-mails to users

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

## Endpoints

### User role access

- `GET /users/list` - List all registered users
- `POST /auth/register` - Register an user in the database
- `POST /auth/login`- Login with user credentials (Email and password)

### Manager role access
- `DELETE users/delete/{uuid}` - Delete an user by the UUID
