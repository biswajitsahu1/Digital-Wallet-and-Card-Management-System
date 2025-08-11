# User Service

User Service is a microservice responsible for user management and authentication in the Digital Wallet and Card Management System. It handles user registration, authentication, and profile management with JWT-based security.

## Features

- User registration and authentication
- JWT-based authentication
- User profile management
- Role-based access control
- Integration with Eureka Service Discovery
- Comprehensive API documentation with Swagger/OpenAPI

## Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security** - For authentication and authorization
- **JWT** - For token-based authentication
- **Spring Cloud Netflix Eureka** - Service discovery
- **MapStruct** - For object mapping
- **Lombok** - For reducing boilerplate code
- **Springdoc OpenAPI** - API documentation
- **Maven** - Dependency management

## Prerequisites

- Java 17 or later
- Maven 3.6.0 or later
- Running Eureka Server (for service discovery)
- MySQL/PostgreSQL (configured in `application.yml`)

## Getting Started

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd user-service
   ```

2. **Configure the application**
   Update the `application.yml` with your database and JWT configuration:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/user_db
       username: your_username
       password: your_password
   
   jwt:
     secret: your_jwt_secret_key
     expiration: 86400000  # 24 hours in milliseconds
   ```

3. **Build the application**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## API Documentation

Once the application is running, you can access the API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate user and get JWT token
- `POST /api/auth/refresh-token` - Refresh JWT token

### User Management
- `GET /api/users/me` - Get current user profile
- `PUT /api/users/me` - Update current user profile
- `GET /api/users` - Get all users (Admin only)

## Security

The service uses JWT (JSON Web Tokens) for authentication. Include the JWT token in the `Authorization` header for authenticated requests:

```
Authorization: Bearer <your_jwt_token>
```

## Error Handling

The service provides detailed error responses in the following format:

```json
{
  "timestamp": "2025-07-31T10:30:00Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password",
  "path": "/api/auth/login"
}
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any queries, please contact the development team at [your-email@example.com](mailto:your-email@example.com)