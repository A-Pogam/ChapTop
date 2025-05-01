# ChapTop (back-end)
This repository contains the **backend API** for **Chatop**, a peer-to-peer rental platform. The application is built using **Spring Boot 3.1**, offers secure and stateless **RESTful endpoints**, and provides interactive API documentation via **Swagger (Springdoc OpenAPI)**.

---

## Key Features

- **JWT-based Authentication**: Secure signup and login system
- **User Management**: Retrieve authenticated user profile
- **Rental Listings**: Create and list rental ads
- **Messaging System**: Send and retrieve messages between users
- **Swagger UI**: Explore and test the API directly via Swagger
- **MySQL Integration**: Persist data with JPA and Hibernate ORM

---

## 📚 API Overview

| Endpoint | Method(s) | Access |
|----------|-----------|--------|
| `/api/auth/login` | `POST` | Public |
| `/api/auth/register` | `POST` | Public |
| `/api/user/me` | `GET` | Protected |
| `/api/rentals` | `GET`, `POST` | Protected |
| `/api/messages` | `GET`, `POST` | Protected |

---

## 🔒 Security Highlights

- Stateless architecture powered by **Spring Security**
- **JWT Tokens** for secure API access
- Passwords hashed using **BCrypt**
- Swagger routes remain public for development/testing

---

## Tech Stack

- **Java 17**
- **Spring Boot 3.1**
- **Spring Security** + **OAuth2 Resource Server**
- **Spring Data JPA** + **Hibernate**
- **MySQL**
- **Springdoc OpenAPI** (v2.3)
- **Maven**

---

## Getting Started

### Prerequisites

- Java 17
- Maven
- MySQL (8.x recommended)

### Configuration

Create a MySQL database named `chatop` and update your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatop
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword
spring.jpa.hibernate.ddl-auto=update
```

### Run the Application

```
mvn spring-boot:run
```

Once running, access the API at:

Base URL: http://localhost:3001

Swagger UI: http://localhost:3001/swagger-ui/index.html
