# E-Commerce Spring Backend

## 1. Project Overview

This backend powers a full-featured e-commerce platform, providing RESTful APIs for product management, user authentication, cart, checkout, order processing, payments, and more. It is designed for both backend developers (extending, maintaining, or deploying the service) and frontend developers (integrating with the APIs).

## 2. Features

- **User Authentication**: JWT-based login, registration, and role-based access control
- **Product Catalog**: CRUD for products, categories, and inventory
- **Cart**: Add/remove items, persist user selections
- **Checkout & Orders**: Order creation, payment integration, order history
- **Payment Processing**: Secure payment endpoints
- **Rate Limiting**: Configurable request throttling (if enabled)
- **Caching**: Product/category caching for performance (if enabled)
- **Messaging/Queue**: Order events, email notifications (if enabled)
- **Audit Logging**: Tracks key actions for compliance

## 3. Architecture / System Design Overview

```
[Frontend] → [API Gateway] → [Spring Boot Backend]
                                 ↓
                        [Database]
                        [Cache]
                        [Queue]
```
- **Request Flow**: Client → Auth (JWT) → Controller → Service → Repository → DB/Cache/Queue
- **Layered Structure**: Controllers (API), Services (business logic), Repositories (data access)

## 4. Tech Stack

| Layer         | Technology         |
|--------------|--------------------|
| Language     | Java               |
| Framework    | Spring Boot        |
| Database     | PostgreSQL/MySQL   |
| Cache        | Redis (optional)   |
| Queue        | RabbitMQ/Kafka (optional) |
| Testing      | JUnit, Mockito     |

## 5. Project Structure

```
src/main/java/com/gibesystems/ecommerce/
├── auth/         # Authentication & user management
├── cart/         # Cart logic
├── category/     # Product categories
├── checkout/     # Checkout & payment
├── config/       # App configuration
├── exception/    # Error handling
├── order/        # Order management
├── payment/      # Payment processing
├── product/      # Product catalog
├── shared/       # Common utilities
└── EcommerceApplication.java # Main entry point
```
- **controllers/**: REST API endpoints
- **service/**: Business logic
- **repository/**: Data access (JPA)
- **dto/**: Data transfer objects
- **entity/**: Database models

## 6. Installation & Setup Instructions

```bash
# Clone the repo
$ git clone https://github.com/temesgenmhr/e-commerce-spring.git
$ cd e-commerce-spring

# Install dependencies
$ ./mvnw clean install
```

## 7. Database Setup & Migrations

- Configure DB in `src/main/resources/application.yml`
- Initialize DB:
  ```bash
  # Start your DB (PostgreSQL/MySQL)
  # DB schema auto-creates on first run (Spring JPA)
  ```
- Migrations (if using Flyway/Liquibase):
  ```bash
  # Example for Flyway
  $ ./mvnw flyway:migrate
  ```
- Seeding: Add initial data via SQL scripts or service methods

## 8. Running the Project

```bash
# Start in development mode
$ ./mvnw spring-boot:run
```

## 9. API Documentation

- **Postman Collection**: See [`e-commerce-spring-api.postman_collection.json`](./e-commerce-spring-api.postman_collection.json)
- **Swagger UI**: (if enabled) `/swagger-ui.html`

### Example: Auth Flow

**Login Request**
```http
POST /api/auth/login
Content-Type: application/json
{
  "email": "user@example.com",
  "password": "yourpassword"
}
```
**Response**
```json
{
  "token": "<JWT_TOKEN>",
  "user": { ... }
}
```

### Example: CRUD (Product)
```http
GET /api/products
Authorization: Bearer <JWT_TOKEN>
```
```json
[
  { "id": 1, "name": "Product A", ... },
  ...
]
```

## 10. API Integration Guide for Frontend Developers

### Authentication
- Obtain JWT via `/api/auth/login`
- Include `Authorization: Bearer <JWT_TOKEN>` in all protected requests

### Sending Requests
- **CORS**: Enabled for frontend origins (see `application.yml`)
- **Headers**:
  - `Content-Type: application/json`
  - `Authorization: Bearer <JWT_TOKEN>`

### Pagination & Filtering
- Use query params: `/api/products?page=1&size=20&category=shoes`

### Error Responses
```json
{
  "timestamp": "2025-12-10T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/products"
}
```

### Rate Limiting
- 429 status code if exceeded
- `Retry-After` header included

### Sample Axios Request
```js
import axios from 'axios';
const token = 'YOUR_JWT_TOKEN';
axios.get('/api/products', {
  headers: { Authorization: `Bearer ${token}` }
})
.then(res => console.log(res.data));
```

### Sample Fetch Request
```js
fetch('/api/products', {
  headers: { 'Authorization': `Bearer ${token}` }
})
.then(res => res.json())
.then(data => console.log(data));
```

## 11. Testing Instructions

```bash
# Run all tests
$ ./mvnw test
```
- Integration tests: Located in `src/test/java/com/gibesystems/ecommerce/`
- Use mocks for external services

## 12. Troubleshooting / FAQ

### Common Setup Issues
- **DB Connection**: Check `application.yml` for correct DB URL, user, password
- **Port Conflicts**: Default port is 8080; change in `application.yml` if needed
- **Migration Issues**: Ensure DB is running and accessible; check migration logs
- **CORS Errors**: Confirm frontend origin is allowed in backend config

---

For more details, see the [Postman collection](./e-commerce-spring-api.postman_collection.json) or contact the maintainers.
