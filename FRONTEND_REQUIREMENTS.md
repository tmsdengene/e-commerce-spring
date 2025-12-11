# E-Commerce Frontend Requirements Document (Angular)

**Version:** 1.1  
**Last Updated:** 2025  
**Status:** Ready for Development (Angular)

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Functional Requirements](#2-functional-requirements)
3. [API Integration Requirements](#3-api-integration-requirements)
4. [State Management Requirements](#4-state-management-requirements)
5. [UI/UX Requirements](#5-uiux-requirements)
6. [Performance & Optimization](#6-performance--optimization)
7. [Testing Requirements](#7-testing-requirements)
8. [Deployment & Environment Requirements](#8-deployment--environment-requirements)
9. [Optional Features & Enhancements](#9-optional-features--enhancements)

---

## 1. Project Overview

### 1.1 Backend Summary

The backend is a **Spring Boot e-commerce platform** providing RESTful APIs for:

- **User Authentication & Authorization**: JWT-based login, registration, and role-based access control (RBAC)
- **Product Catalog**: Full CRUD operations, search, filtering, and categorization
- **Shopping Cart**: Add/remove/update items, persistent storage per user
- **Order Management**: Create orders, track status, cancel orders, view order history
- **Payment Processing**: Initiate payments, retrieve payment status
- **Checkout**: Multi-step checkout process with payment method selection
- **Categories**: Product categorization with CRUD operations
- **Audit Logging**: Track user actions for compliance

### 1.2 Frontend Purpose

The frontend is a **customer-facing web application** that enables users to:

- Browse and search products
- Manage shopping cart 
- Complete checkout and payment
- Track orders and view order history
- Manage user profile and preferences

### 1.3 Users & Roles

| Role | Permissions | Key Features |
|------|-------------|--------------|
| **Guest** | View products, categories | Browse catalog, search, view product details |
| **Customer** | All guest + cart, orders, payments | Add to cart, checkout, view order history |
| **Admin** | All customer + product/category management | Create/edit/delete products, manage categories, view analytics |

---

## 2. Functional Requirements

### 2.1 Pages & Screens

#### 2.1.1 Authentication Pages

| Page | Purpose | Key Components |
|------|---------|-----------------|
| **Login** | User authentication | Email input, password input, login button, "Forgot Password" link, register link |
| **Register** | New user account creation | Email input, password input, confirm password, name input, terms checkbox, register button |
| **Forgot Password** | Password reset flow | Email input, reset link sender, confirmation message |
| **Reset Password** | Set new password | New password input, confirm password, reset button |

#### 2.1.2 Product Pages

| Page | Purpose | Key Components |
|------|---------|-----------------|
| **Product Listing** | Browse all products | Product grid/list, filters (category, price range), search bar, pagination, sort options |
| **Product Details** | View single product | Product image, name, price, description, stock status, add to cart, reviews (optional) |
| **Search Results** | Display filtered products | Same as product listing with active filters displayed |
| **Category View** | Browse products by category | Category name, product grid, filters, pagination |

#### 2.1.3 Cart & Checkout Pages

| Page | Purpose | Key Components |
|------|---------|-----------------|
| **Shopping Cart** | Review cart items | Item list with quantity, price, remove button, update quantity, clear cart, proceed to checkout |
| **Checkout** | Multi-step checkout | Shipping address form, billing address form, payment method selection, order review, place order button |
| **Order Confirmation** | Confirm successful order | Order number, order details, estimated delivery, continue shopping button |

#### 2.1.5 Order Pages

| Page | Purpose | Key Components |
|------|---------|-----------------|
| **Order History** | View all user orders | Order list with status, date, total, view details link |
| **Order Details** | View single order | Order items, status timeline, tracking info, cancel button (if applicable) |

#### 2.1.6 User Profile Pages

| Page | Purpose | Key Components |
|------|---------|-----------------|
| **Profile** | View/edit user info | Name, email, phone, address fields, edit button, save button |
| **Addresses** | Manage saved addresses | Address list, add new, edit, delete, set as default |
| **Payment Methods** | Manage saved payment methods | Payment method list, add new, delete, set as default |

#### 2.1.7 Admin Pages (Optional)

| Page | Purpose | Key Components |
|------|---------|-----------------|
| **Product Management** | CRUD products | Product form, image upload, category selection, price/stock fields |
| **Category Management** | CRUD categories | Category form, list view, edit, delete |
| **Order Management** | View/manage orders | Order list, status update, order details |

### 2.2 Features Per Page

#### 2.2.1 Product Listing Features

**Search & Filter:**
- Full-text search by product name
- Filter by category (multi-select)
- Filter by price range (min/max sliders)
- Sort by: name, price (asc/desc), newest, popularity
- Clear all filters button

**Display:**
- Product grid (responsive: 1-4 columns based on screen size)
- Product card showing: image, name, price, stock status, rating (optional)
- Pagination with page size selector (10, 20, 50 items per page)
- "Load More" button alternative to pagination

**Actions:**
- Quick add to cart
- View product details link

#### 2.2.2 Product Details Features

**Display:**
- Large product image with zoom capability
- Image gallery/carousel (if multiple images)
- Product name, price, category
- Stock status (in stock, low stock, out of stock)
- Product description
- Product attributes (size, color, weight)
- Customer reviews and ratings (optional)

**Actions:**
- Quantity selector (1-99)
- Add to cart button (disabled if out of stock)
- Share product (social media, email)
- View similar products

#### 2.2.3 Shopping Cart Features

**Display:**
- List of cart items with:
  - Product image, name, price
  - Quantity selector
  - Item subtotal
  - Remove button
- Cart summary:
  - Subtotal
  - Tax (calculated)
  - Discount (if applicable)
  - Total price
- Empty cart message if no items

**Actions:**
- Update quantity (inline or modal)
- Remove item
- Clear entire cart
- Continue shopping button
- Proceed to checkout button
- Apply coupon/discount code (optional)

#### 2.2.4 Checkout Features

**Step 1: Shipping Address**
- Address form fields: street, city, state, zip, country
- Option to use saved address
- Save address for future use checkbox

**Step 2: Billing Address**
- Same as shipping or different address option
- Address form fields

**Step 3: Payment Method**
- Payment method selection (credit card, debit card, digital wallet, etc.)
- Payment form fields (card number, expiry, CVV)
- Option to save payment method

**Step 4: Order Review**
- Display order items, quantities, prices
- Display shipping address
- Display payment method (masked)
- Display total amount
- Place order button

**Actions:**
- Back/Next buttons between steps
- Edit previous steps
- Apply coupon/discount code
- Place order button



#### 2.2.6 Order History Features

**Display:**
- Table/list of orders with:
  - Order number
  - Order date
  - Total amount
  - Status (pending, processing, shipped, delivered, cancelled)
  - View details link

**Actions:**
- Filter by status
- Sort by date
- View order details
- Cancel order (if applicable)
- Reorder button (add items to cart)

#### 2.2.7 Order Details Features

**Display:**
- Order header: order number, date, status
- Order items: product name, quantity, price, subtotal
- Shipping address
- Billing address
- Payment method (masked)
- Order total breakdown
- Status timeline (ordered → processing → shipped → delivered)
- Tracking information (if available)

**Actions:**
- Cancel order button (if status allows)
- Download invoice (optional)
- Contact support button

#### 2.2.8 User Authentication Flow

**Login Flow:**
1. User enters email and password
2. Frontend validates input (email format, password length)
3. Frontend sends POST request to `/auth/login`
4. Backend validates credentials and returns JWT token
5. Frontend stores token in localStorage/sessionStorage
6. Frontend redirects to dashboard/home page
7. Token included in Authorization header for all subsequent requests

**Registration Flow:**
1. User enters email, password, confirm password, name
2. Frontend validates input (email format, password strength, password match)
3. Frontend sends POST request to `/auth/register`
4. Backend creates user and returns user data
5. Frontend redirects to login page or auto-logs in user
6. Display success message

**Logout Flow:**
1. User clicks logout button
2. Frontend sends POST request to `/auth/logout` with token
3. Frontend clears token from storage
4. Frontend redirects to login page

**Token Refresh Flow:**
1. Frontend detects token expiration (via JWT decode or 401 response)
2. Frontend sends POST request to `/auth/refresh-token` with refresh token
3. Backend returns new access token
4. Frontend updates stored token
5. Frontend retries original request

### 2.3 Special Frontend Logic

#### 2.3.1 Real-Time Updates

- **Cart Updates**: Update cart count in header/navbar in real-time
- **Stock Status**: Show real-time stock availability on product pages
- **Order Status**: Poll order status endpoint every 30 seconds (or use WebSocket if backend supports)

#### 2.3.2 Caching Strategy

- **Product Catalog**: Cache product list for 5-10 minutes
- **Categories**: Cache category list for 1 hour
- **User Profile**: Cache user data for session duration
- **Cart**: Always fetch fresh from backend (no caching)
- **Orders**: Cache for 5 minutes, refresh on user action


#### 2.3.4 Form Validation

- **Client-side validation**: Email format, password strength, required fields
- **Server-side validation**: Backend validates all inputs
- **Error messages**: Display specific validation errors to user
- **Real-time validation**: Validate email availability on blur

#### 2.3.5 Loading & Error States

- **Loading indicators**: Show spinners/skeletons while fetching data
- **Error messages**: Display user-friendly error messages
- **Retry logic**: Provide retry button for failed requests
- **Timeout handling**: Show timeout error after 30 seconds

---

## 3. API Integration Requirements

### 3.1 Base Configuration

```ts
// Angular environment configuration
// src/environments/environment.ts
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080',
  apiVersion: '/api/v1'
};

// Usage example in a service
@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly base = environment.apiBaseUrl + environment.apiVersion;
  constructor(private http: HttpClient) {}

  getProducts(params?: HttpParams) {
    return this.http.get<PageResponse<ProductResponse>>(`${this.base}/products`, { params });
  }
}
```

Example: http://localhost:8080/api/v1/products

### 3.2 Authentication Endpoints

#### 3.2.1 Login

```http
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "user-uuid",
    "email": "user@example.com",
    "name": "John Doe",
    "role": "CUSTOMER"
  }
}
```

**Error Responses:**
- `400 Bad Request`: Invalid email format or missing fields
- `401 Unauthorized`: Invalid credentials
- `500 Internal Server Error`: Server error

#### 3.2.2 Register

```http
POST /auth/register
Content-Type: application/json

{
  "email": "newuser@example.com",
  "password": "password123",
  "name": "Jane Doe"
}
```

**Response (201 Created):**
```json
{
  "id": "user-uuid",
  "email": "newuser@example.com",
  "name": "Jane Doe",
  "role": "CUSTOMER"
}
```

**Error Responses:**
- `400 Bad Request`: Invalid input or email already exists
- `409 Conflict`: Email already registered
- `500 Internal Server Error`: Server error

#### 3.2.3 Refresh Token

```http
POST /auth/refresh-token
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 3.2.4 Logout

```http
POST /auth/logout
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response (200 OK):**
```json
{
  "message": "Logout successful"
}
```

#### 3.2.5 Validate Token

```http
GET /auth/validate-token?token=<JWT_TOKEN>
```

**Response (200 OK):**
```json
{
  "valid": true
}
```

#### 3.2.6 Extract User ID from Token

```http
GET /auth/user-id?token=<JWT_TOKEN>
```

**Response (200 OK):**
```json
{
  "userId": "user-uuid"
}
```

### 3.3 Product Endpoints

#### 3.3.1 Get All Products (Paginated)

```http
GET /products?page=0&size=10
Authorization: Bearer <JWT_TOKEN>
```

**Query Parameters:**
- `page` (int, default: 0): Page number (0-indexed)
- `size` (int, default: 10): Items per page

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "productUuid": "uuid-1",
      "name": "iPhone 15",
      "categoryName": "Electronics",
      "price": 999.99,
      "stockQuantity": 50,
      "description": "Latest iPhone model",
      "imageUrl": "/uploads/products/iphone.jpg",
      "weight": "170g",
      "size": "6.1 inches",
      "color": "Black",
      "active": true,
      "lowStock": false
    }
  ],
  "totalElements": 100,
  "totalPages": 10,
  "currentPage": 0,
  "pageSize": 10
}
```

#### 3.3.2 Search Products

```http
GET /products/search?name=iPhone&category=Electronics&minPrice=500&maxPrice=1500&page=0&size=10&sortBy=price&sortDir=asc
Authorization: Bearer <JWT_TOKEN>
```

**Query Parameters:**
- `name` (string, optional): Product name search
- `category` (string, optional): Category name filter
- `minPrice` (double, optional): Minimum price filter
- `maxPrice` (double, optional): Maximum price filter
- `page` (int, default: 0): Page number
- `size` (int, default: 10): Items per page
- `sortBy` (string, default: "id"): Sort field (id, name, price, createdAt)
- `sortDir` (string, default: "asc"): Sort direction (asc, desc)

**Response (200 OK):** Same as Get All Products

#### 3.3.3 Get Product by ID

```http
GET /products/{id}
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "productUuid": "uuid-1",
  "name": "iPhone 15",
  "categoryName": "Electronics",
  "price": 999.99,
  "stockQuantity": 50,
  "description": "Latest iPhone model",
  "imageUrl": "/uploads/products/iphone.jpg",
  "weight": "170g",
  "size": "6.1 inches",
  "color": "Black",
  "active": true,
  "lowStock": false
}
```

**Error Responses:**
- `404 Not Found`: Product not found

#### 3.3.4 Create Product (Admin Only)

```http
POST /products
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "name": "New Product",
  "categoryId": 1,
  "price": 99.99,
  "stockQuantity": 100,
  "description": "Product description",
  "imageUrl": "/uploads/products/image.jpg",
  "weight": "500g",
  "size": "10x10x10",
  "color": "Red"
}
```

**Response (200 OK):** Product object

#### 3.3.5 Update Product (Admin Only)

```http
PUT /products/{id}
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "name": "Updated Product",
  "categoryId": 1,
  "price": 89.99,
  "stockQuantity": 80,
  "description": "Updated description",
  "imageUrl": "/uploads/products/image.jpg",
  "weight": "500g",
  "size": "10x10x10",
  "color": "Blue"
}
```

**Response (200 OK):** Updated product object

#### 3.3.6 Deactivate Product (Admin Only)

```http
PATCH /products/{id}/deactivate
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):** Empty response

#### 3.3.7 Delete Product (Admin Only)

```http
DELETE /products/{id}
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):** Empty response

#### 3.3.8 Upload Product Image

```http
POST /products/upload-image
Authorization: Bearer <JWT_TOKEN>
Content-Type: multipart/form-data

file: <image_file>
```

**Response (200 OK):**
```json
{
  "imageUrl": "/uploads/products/image-uuid.jpg"
}
```

### 3.4 Category Endpoints

#### 3.4.1 Get All Categories

```http
GET /categories?page=0&size=10
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Electronics",
      "description": "Electronic devices"
    }
  ],
  "totalElements": 10,
  "totalPages": 1,
  "currentPage": 0,
  "pageSize": 10
}
```

#### 3.4.2 Get Category by ID

```http
GET /categories/{id}
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Electronics",
  "description": "Electronic devices"
}
```

#### 3.4.3 Create Category (Admin Only)

```http
POST /categories
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "name": "New Category",
  "description": "Category description"
}
```

**Response (200 OK):** Category object

#### 3.4.4 Update Category (Admin Only)

```http
PUT /categories/{id}
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "name": "Updated Category",
  "description": "Updated description"
}
```

**Response (200 OK):** Updated category object

#### 3.4.5 Delete Category (Admin Only)

```http
DELETE /categories/{id}
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):** Empty response

### 3.5 Cart Endpoints

#### 3.5.1 Get Cart

```http
GET /api/v1/cart
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "userId": "user-uuid",
  "items": [
    {
      "id": 1,
      "productId": 1,
      "productName": "iPhone 15",
      "price": 999.99,
      "quantity": 2,
      "subtotal": 1999.98
    }
  ],
  "totalPrice": 1999.98,
  "tax": 199.99,
  "discount": 0
}
```

#### 3.5.2 Add Item to Cart

```http
POST /api/v1/cart/add
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

**Response (200 OK):** Updated cart object

**Error Responses:**
- `400 Bad Request`: Invalid product ID or quantity
- `404 Not Found`: Product not found
- `409 Conflict`: Insufficient stock

#### 3.5.3 Update Cart Item

```http
PUT /api/v1/cart/update
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "productId": 1,
  "quantity": 3
}
```

**Response (200 OK):** Updated cart object

#### 3.5.4 Remove Item from Cart

```http
DELETE /api/v1/cart/remove
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "productId": 1
}
```

**Response (200 OK):** Updated cart object

#### 3.5.5 Clear Cart

```http
DELETE /api/v1/cart/clear
Authorization: Bearer <JWT_TOKEN>
```

**Response (204 No Content):** Empty response

### 3.7 Order Endpoints

#### 3.7.1 Create Order

```http
POST /api/v1/orders/create
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "shippingAddress": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zip": "10001",
    "country": "USA"
  },
  "billingAddress": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zip": "10001",
    "country": "USA"
  },
  "paymentMethod": "CREDIT_CARD"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "orderUuid": "order-uuid",
  "userId": "user-uuid",
  "items": [
    {
      "id": 1,
      "productId": 1,
      "productName": "iPhone 15",
      "quantity": 2,
      "price": 999.99,
      "subtotal": 1999.98
    }
  ],
  "status": "PENDING",
  "createdAt": "2025-01-10T12:00:00Z",
  "totalAmount": 1999.98
}
```

#### 3.7.2 Get Order by UUID

```http
GET /api/v1/orders/{orderUuid}
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):** Order object

#### 3.7.3 Update Order Status (Admin Only)

```http
PUT /api/v1/orders/update-status
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "orderUuid": "order-uuid",
  "status": "SHIPPED"
}
```

**Response (200 OK):** Updated order object

**Valid Status Values:** PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED

#### 3.7.4 Cancel Order

```http
DELETE /api/v1/orders/cancel/{orderUuid}
Authorization: Bearer <JWT_TOKEN>
```

**Response (204 No Content):** Empty response

#### 3.7.5 Get Order History

```http
GET /api/v1/orders/history
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "orderUuid": "order-uuid",
  "userId": "user-uuid",
  "items": [...],
  "status": "DELIVERED",
  "createdAt": "2025-01-10T12:00:00Z",
  "totalAmount": 1999.98
}
```

### 3.8 Payment Endpoints

#### 3.8.1 Initiate Payment

```http
POST /api/payment/initiate
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "orderId": 1,
  "amount": 1999.98,
  "paymentMethod": "CREDIT_CARD",
  "cardNumber": "4111111111111111",
  "expiryDate": "12/25",
  "cvv": "123"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "orderId": 1,
  "amount": 1999.98,
  "status": "SUCCESS",
  "transactionId": "txn-uuid",
  "paymentMethod": "CREDIT_CARD",
  "createdAt": "2025-01-10T12:00:00Z"
}
```

**Error Responses:**
- `400 Bad Request`: Invalid payment details
- `402 Payment Required`: Payment declined
- `500 Internal Server Error`: Payment gateway error

#### 3.8.2 Get Payment by Order

```http
GET /api/payment/order/{orderId}
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):** Payment object

#### 3.8.3 Get Latest Payment for User

```http
GET /api/payment/user/latest
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):** Payment object

### 3.9 Checkout Endpoints

#### 3.9.1 Checkout

```http
POST /api/checkout/{orderId}?paymentMethod=CREDIT_CARD
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "orderId": 1,
  "userId": "user-uuid",
  "paymentMethod": "CREDIT_CARD",
  "status": "COMPLETED",
  "createdAt": "2025-01-10T12:00:00Z"
}
```

#### 3.9.2 Get Checkout by Order

```http
GET /api/checkout/order/{orderId}
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):** Checkout object

#### 3.9.3 Get Latest Checkout for User

```http
GET /api/checkout/user/{userId}/latest
Authorization: Bearer <JWT_TOKEN>
```

**Response (200 OK):** Checkout object

### 3.10 Authentication & Token Usage

#### 3.10.1 JWT Token Structure

```
Header: {
  "alg": "HS256",
  "typ": "JWT"
}

Payload: {
  "sub": "user-uuid",
  "email": "user@example.com",
  "role": "CUSTOMER",
  "iat": 1704873600,
  "exp": 1704960000
}

Signature: HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
```

#### 3.10.2 Token Usage in Requests

All authenticated requests must include the JWT token in the Authorization header. Implement this via an Angular HttpInterceptor:

```ts
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('access_token');
    const authReq = token ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } }) : req;
    return next.handle(authReq).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 401) {
          // Optionally trigger refresh flow
        }
        return throwError(() => err);
      })
    );
  }
}

// app.module.ts
providers: [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
];
```

#### 3.10.3 Token Expiration & Refresh

- **Access Token Expiration**: 1 hour (configurable)
- **Refresh Token Expiration**: 7 days (configurable)
- **Refresh Strategy**: 
  - Decode token to check expiration
  - If expired, call `/auth/refresh-token` endpoint
  - Update stored token with new access token
  - Retry original request

### 3.11 Pagination & Filtering Rules

#### 3.11.1 Pagination

- **Default Page Size**: 10 items
- **Max Page Size**: 100 items
- **Page Numbering**: 0-indexed (first page is 0)
- **Response Format**:
  ```json
  {
    "content": [...],
    "totalElements": 100,
    "totalPages": 10,
    "currentPage": 0,
    "pageSize": 10
  }
  ```

#### 3.11.2 Filtering

- **Product Filters**:
  - Category (string, exact match)
  - Price Range (minPrice, maxPrice)
  - Stock Status (in stock, low stock, out of stock)
  - Active Status (active/inactive)

- **Order Filters**:
  - Status (PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED)
  - Date Range (optional)

#### 3.11.3 Sorting

- **Sort Fields**: id, name, price, createdAt, updatedAt
- **Sort Direction**: asc (ascending), desc (descending)
- **Default Sort**: id asc

### 3.12 Error Handling

#### 3.12.1 Standard Error Response Format

```json
{
  "timestamp": "2025-01-10T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed: Email is required",
  "path": "/api/v1/products"
}
```

#### 3.12.2 HTTP Status Codes

| Status | Meaning | Action |
|--------|---------|--------|
| 200 | OK | Request successful |
| 201 | Created | Resource created successfully |
| 204 | No Content | Request successful, no content to return |
| 400 | Bad Request | Invalid input, show validation errors |
| 401 | Unauthorized | Token missing/invalid, redirect to login |
| 403 | Forbidden | User lacks permission, show error message |
| 404 | Not Found | Resource not found, show 404 page |
| 409 | Conflict | Resource conflict (e.g., email exists), show error |
| 429 | Too Many Requests | Rate limited, show retry message |
| 500 | Internal Server Error | Server error, show generic error message |

#### 3.12.3 Frontend Error Handling Strategy

```javascript
// Example error handling
try {
  const response = await fetch('/api/v1/products', {
    headers: { 'Authorization': `Bearer ${token}` }
  });

  if (!response.ok) {
    const error = await response.json();
    
    if (response.status === 401) {
      // Token expired, refresh and retry
      await refreshToken();
      return retryRequest();
    } else if (response.status === 400) {
      // Validation error, show to user
      showValidationError(error.message);
    } else if (response.status === 500) {
      // Server error, show generic message
      showErrorMessage('Something went wrong. Please try again later.');
    }
  }
} catch (error) {
  // Network error
  showErrorMessage('Network error. Please check your connection.');
}
```

---

## 4. State Management Requirements

- **NgRx** 

### 4.2 Key Global States to Manage

#### 4.2.1 Authentication State

```javascript
{
  auth: {
    isAuthenticated: boolean,
    user: {
      id: string,
      email: string,
      name: string,
      role: string
    },
    token: string,
    refreshToken: string,
    loading: boolean,
    error: string | null
  }
}
```

**Actions:**
- `LOGIN_REQUEST` / `LOGIN_SUCCESS` / `LOGIN_FAILURE`
- `REGISTER_REQUEST` / `REGISTER_SUCCESS` / `REGISTER_FAILURE`
- `LOGOUT`
- `REFRESH_TOKEN`
- `VALIDATE_TOKEN`

#### 4.2.2 Product State

```javascript
{
  products: {
    items: ProductResponse[],
    currentProduct: ProductResponse | null,
    loading: boolean,
    error: string | null,
    pagination: {
      page: number,
      size: number,
      totalElements: number,
      totalPages: number
    },
    filters: {
      name: string,
      category: string,
      minPrice: number,
      maxPrice: number,
      sortBy: string,
      sortDir: string
    }
  }
}
```

**Actions:**
- `FETCH_PRODUCTS_REQUEST` / `FETCH_PRODUCTS_SUCCESS` / `FETCH_PRODUCTS_FAILURE`
- `FETCH_PRODUCT_BY_ID_REQUEST` / `FETCH_PRODUCT_BY_ID_SUCCESS` / `FETCH_PRODUCT_BY_ID_FAILURE`
- `SEARCH_PRODUCTS`
- `SET_FILTERS`
- `SET_PAGINATION`

#### 4.2.3 Cart State

```javascript
{
  cart: {
    items: CartItemDTO[],
    totalPrice: number,
    tax: number,
    discount: number,
    loading: boolean,
    error: string | null
  }
}
```

**Actions:**
- `FETCH_CART_REQUEST` / `FETCH_CART_SUCCESS` / `FETCH_CART_FAILURE`
- `ADD_TO_CART_REQUEST` / `ADD_TO_CART_SUCCESS` / `ADD_TO_CART_FAILURE`
- `UPDATE_CART_ITEM_REQUEST` / `UPDATE_CART_ITEM_SUCCESS` / `UPDATE_CART_ITEM_FAILURE`
- `REMOVE_FROM_CART_REQUEST` / `REMOVE_FROM_CART_SUCCESS` / `REMOVE_FROM_CART_FAILURE`
- `CLEAR_CART_REQUEST` / `CLEAR_CART_SUCCESS` / `CLEAR_CART_FAILURE`



#### 4.2.5 Order State

```javascript
{
  orders: {
    items: OrderDTO[],
    currentOrder: OrderDTO | null,
    loading: boolean,
    error: string | null,
    pagination: {
      page: number,
      size: number,
      totalElements: number,
      totalPages: number
    }
  }
}
```

**Actions:**
- `FETCH_ORDERS_REQUEST` / `FETCH_ORDERS_SUCCESS` / `FETCH_ORDERS_FAILURE`
- `FETCH_ORDER_BY_ID_REQUEST` / `FETCH_ORDER_BY_ID_SUCCESS` / `FETCH_ORDER_BY_ID_FAILURE`
- `CREATE_ORDER_REQUEST` / `CREATE_ORDER_SUCCESS` / `CREATE_ORDER_FAILURE`
- `UPDATE_ORDER_STATUS_REQUEST` / `UPDATE_ORDER_STATUS_SUCCESS` / `UPDATE_ORDER_STATUS_FAILURE`
- `CANCEL_ORDER_REQUEST` / `CANCEL_ORDER_SUCCESS` / `CANCEL_ORDER_FAILURE`

#### 4.2.6 Category State

```javascript
{
  categories: {
    items: CategoryResponse[],
    loading: boolean,
    error: string | null
  }
}
```

**Actions:**
- `FETCH_CATEGORIES_REQUEST` / `FETCH_CATEGORIES_SUCCESS` / `FETCH_CATEGORIES_FAILURE`

#### 4.2.7 UI State

```javascript
{
  ui: {
    notifications: {
      message: string,
      type: 'success' | 'error' | 'warning' | 'info',
      visible: boolean
    },
    modals: {
      [modalName]: boolean
    },
    loading: boolean,
    sidebarOpen: boolean
  }
}
```

**Actions:**
- `SHOW_NOTIFICATION`
- `HIDE_NOTIFICATION`
- `OPEN_MODAL`
- `CLOSE_MODAL`
- `SET_LOADING`
- `TOGGLE_SIDEBAR`

### 4.3 API Response & Caching Strategy

#### 4.3.1 Caching Implementation

```ts
// NgRx + HttpClient + Effects example
@Injectable({ providedIn: 'root' })
export class ProductsService {
  private readonly base = environment.apiBaseUrl + environment.apiVersion;
  constructor(private http: HttpClient) {}
  getProducts(page = 0, size = 10, params?: Record<string, string | number>) {
    const httpParams = new HttpParams({ fromObject: { page, size, ...params } as any });
    return this.http.get<PageResponse<ProductResponse>>(`${this.base}/products`, { params: httpParams });
  }
}

export const loadProducts = createAction('[Products] Load', props<{ page?: number; size?: number; params?: any }>());
export const loadProductsSuccess = createAction('[Products] Load Success', props<{ page: PageResponse<ProductResponse> }>());
export const loadProductsFailure = createAction('[Products] Load Failure', props<{ error: string }>());

@Injectable()
export class ProductsEffects {
  load$ = createEffect(() => this.actions$.pipe(
    ofType(loadProducts),
    switchMap(({ page = 0, size = 10, params }) =>
      this.api.getProducts(page, size, params).pipe(
        map((pageRes) => loadProductsSuccess({ page: pageRes })),
        catchError((err) => of(loadProductsFailure({ error: err.message || 'Error' })))
      )
    )
  ));
  constructor(private actions$: Actions, private api: ProductsService) {}
}
```

#### 4.3.2 Cache Invalidation

```javascript
// Invalidate cache when data changes
export const productsApi = createApi({
  // ... config
  endpoints: (builder) => ({
    // ... queries
    addProduct: builder.mutation({
      query: (product) => ({
        url: '/products',
        method: 'POST',
        body: product
      }),
      // Invalidate products list after adding
      invalidatesTags: ['Products']
    })
  })
});
```

#### 4.3.3 Error Handling in State

```javascript
// Store error messages for display
{
  errors: {
    auth: string | null,
    products: string | null,
    cart: string | null,
    orders: string | null
  }
}
```

---

## 5. UI/UX Requirements

### 5.1 Forms & Input Validation

#### 5.1.1 Login Form

| Field | Type | Validation | Error Message |
|-------|------|-----------|----------------|
| Email | Text | Required, valid email format | "Please enter a valid email" |
| Password | Password | Required, min 6 chars | "Password must be at least 6 characters" |

**Behavior:**
- Real-time validation on blur
- Submit button disabled until form is valid
- Show loading spinner on submit
- Clear password field on error

#### 5.1.2 Registration Form

| Field | Type | Validation | Error Message |
|-------|------|-----------|----------------|
| Name | Text | Required, min 2 chars | "Name must be at least 2 characters" |
| Email | Text | Required, valid format, unique | "Email already registered" |
| Password | Password | Required, min 8 chars, strong | "Password must be at least 8 characters with uppercase, lowercase, number" |
| Confirm Password | Password | Required, must match password | "Passwords do not match" |
| Terms | Checkbox | Required | "You must accept terms" |

**Behavior:**
- Real-time validation on blur
- Password strength indicator
- Show/hide password toggle
- Submit button disabled until form is valid

#### 5.1.3 Checkout Form

**Shipping Address:**
- Street (required)
- City (required)
- State/Province (required)
- Zip/Postal Code (required)
- Country (required, dropdown)

**Billing Address:**
- Same as shipping (checkbox)
- Or separate address fields

**Payment Information:**
- Card Number (required, 16 digits)
- Expiry Date (required, MM/YY format)
- CVV (required, 3-4 digits)
- Cardholder Name (required)

**Behavior:**
- Real-time validation
- Format card number with spaces (1234 5678 9012 3456)
- Mask CVV input
- Show card type icon based on number
- Save payment method checkbox

### 5.2 Tables & Data Display

#### 5.2.1 Product Table

| Column | Content | Sortable | Filterable |
|--------|---------|----------|-----------|
| Image | Product image | No | No |
| Name | Product name | Yes | Yes |
| Category | Category name | Yes | Yes |
| Price | Product price | Yes | Yes |
| Stock | Stock quantity | Yes | No |
| Status | Active/Inactive | No | Yes |
| Actions | Edit, Delete | No | No |

**Behavior:**
- Clickable rows to view details
- Hover effects on rows
- Checkbox for bulk actions
- Responsive: stack columns on mobile

#### 5.2.2 Order History Table

| Column | Content | Sortable | Filterable |
|--------|---------|----------|-----------|
| Order # | Order number | Yes | No |
| Date | Order date | Yes | Yes |
| Total | Order total | Yes | Yes |
| Status | Order status | Yes | Yes |
| Actions | View, Cancel | No | No |

**Behavior:**
- Status badge with color coding
- Clickable rows to view details
- Filter by status dropdown
- Date range picker for filtering

### 5.3 Modals & Dialogs

#### 5.3.1 Confirmation Modal

```
Title: "Confirm Action"
Message: "Are you sure you want to [action]?"
Buttons: [Cancel] [Confirm]
```

**Use Cases:**
- Delete product
- Cancel order
- Clear cart


#### 5.3.2 Error Modal

```
Title: "Error"
Message: "[Error message from server]"
Buttons: [Close] [Retry]
```

#### 5.3.3 Success Modal

```
Title: "Success"
Message: "[Success message]"
Buttons: [Close] [Continue]
```

### 5.4 Loading & Skeleton States

#### 5.4.1 Loading Indicators

- **Page Loading**: Full-page spinner overlay
- **Component Loading**: Skeleton screens for product cards, tables
- **Button Loading**: Spinner inside button, button disabled
- **Inline Loading**: Small spinner next to text

#### 5.4.2 Skeleton Screens

```
Product Card Skeleton:
┌─────────────────┐
│   [Image]       │
│   [Title]       │
│   [Price]       │
│   [Button]      │
└─────────────────┘

Table Row Skeleton:
┌──────┬──────┬──────┬──────┐
│ [  ] │ [  ] │ [  ] │ [  ] │
└──────┴──────┴──────┴──────┘
```

### 5.5 Error Messages & Alerts

#### 5.5.1 Toast Notifications

**Types:**
- **Success**: Green background, checkmark icon, auto-dismiss after 3 seconds
- **Error**: Red background, X icon, manual dismiss
- **Warning**: Yellow background, warning icon, auto-dismiss after 5 seconds
- **Info**: Blue background, info icon, auto-dismiss after 3 seconds

**Position:** Top-right corner, stack vertically

**Example:**
```
✓ Product added to cart
✗ Failed to add product. Please try again.
⚠ Low stock available
ℹ Order shipped successfully
```

#### 5.5.2 Inline Error Messages

- Display below form fields
- Red text color
- Show validation errors immediately on blur
- Clear on successful input

#### 5.5.3 Page-Level Error Messages

```
┌─────────────────────────────────┐
│ ✗ Error                         │
│ Failed to load products.        │
│ [Retry]                         │
└─────────────────────────────────┘
```

### 5.6 Responsive Design Requirements

#### 5.6.1 Breakpoints

| Device | Width | Columns | Layout |
|--------|-------|---------|--------|
| Mobile | < 640px | 1 | Stack vertically |
| Tablet | 640px - 1024px | 2 | Two columns |
| Desktop | > 1024px | 3-4 | Multi-column grid |

#### 5.6.2 Responsive Components

**Product Grid:**
- Mobile: 1 column
- Tablet: 2 columns
- Desktop: 3-4 columns

**Navigation:**
- Mobile: Hamburger menu
- Tablet: Horizontal menu
- Desktop: Full horizontal menu

**Tables:**
- Mobile: Horizontal scroll or card view
- Tablet: Horizontal scroll
- Desktop: Full table

**Forms:**
- Mobile: Full width, single column
- Tablet: Full width, single column
- Desktop: Multi-column layout

## 6. Performance & Optimization

### 6.1 Lazy Loading

#### 6.1.1 Route-Based Code Splitting

```ts
// Angular Router with lazy-loaded modules
const routes: Routes = [
  { path: 'products', loadChildren: () => import('./features/products/products.module').then(m => m.ProductsModule) },
  { path: 'orders', loadChildren: () => import('./features/orders/orders.module').then(m => m.OrdersModule) },
  { path: 'checkout', loadChildren: () => import('./features/checkout/checkout.module').then(m => m.CheckoutModule) },
  { path: '', pathMatch: 'full', redirectTo: 'products' },
  { path: '**', loadComponent: () => import('./shared/pages/not-found/not-found.component').then(c => c.NotFoundComponent) }
];
```

#### 6.1.2 Image Lazy Loading

```html
<!-- Native lazy loading in Angular templates -->
<img [src]="product.imageUrl" loading="lazy" [alt]="product.name" />

<!-- Directive approach with IntersectionObserver -->
<img appLazyImage [lazySrc]="product.imageUrl" [alt]="product.name" />
```

#### 6.1.3 Infinite Scroll / Virtual Scrolling

- Implement virtual scrolling for large product lists
- Load next page when user scrolls near bottom
- Show loading indicator while fetching

### 6.2 Code Splitting

- Split by route (lazy-loaded modules)
- Split by feature modules (cart, checkout, admin)
- Use standalone components where appropriate to reduce module overhead
- Enforce Angular CLI budgets; target main bundle < 200KB (gzipped)

### 6.3 Caching Strategy

#### 6.3.1 Browser Caching

- **Static Assets**: Cache for 1 year (with versioning)
- **API Responses**: Cache for 5-10 minutes
- **User Data**: Cache for session duration

#### 6.3.2 Service Worker Caching (Angular PWA)

```bash
ng add @angular/pwa
```

```json
// ngsw-config.json (snippet)
{
  "index": "/index.html",
  "assetGroups": [
    { "name": "app", "installMode": "prefetch", "resources": { "files": ["/*.css", "/*.js", "/index.html"] } },
    { "name": "assets", "installMode": "lazy", "updateMode": "prefetch", "resources": { "files": ["/assets/**", "/*.(png|jpg|svg)"] } }
  ],
  "dataGroups": [
    { "name": "api", "urls": ["/api/**"], "cacheConfig": { "strategy": "freshness", "maxSize": 100, "maxAge": "10m", "timeout": "10s" } }
  ]
}
```

### 6.4 API Call Optimization

#### 6.4.1 Debouncing

```ts
// Debounce search input with RxJS in Angular
searchCtrl = new FormControl('');

ngOnInit() {
  this.searchCtrl.valueChanges.pipe(
    debounceTime(300),
    distinctUntilChanged(),
    tap(q => this.store.dispatch(loadProducts({ params: { name: q } })))
  ).subscribe();
}
```

#### 6.4.2 Throttling

```ts
// Throttle scroll events with RxJS
fromEvent(window, 'scroll').pipe(
  throttleTime(500),
  filter(() => isNearBottom()),
  tap(() => this.loadMore())
).subscribe();
```

#### 6.4.3 Request Batching

- Combine multiple API calls into single request
- Use GraphQL for flexible data fetching (if backend supports)

#### 6.4.4 Request Cancellation

```ts
// Cancel previous request using RxJS switchMap in Angular
this.searchCtrl.valueChanges.pipe(
  debounceTime(300),
  distinctUntilChanged(),
  switchMap(q => this.api.searchProducts(q))
).subscribe();
```

### 6.5 Reusable Components

#### 6.5.1 Component Library

**Common Components:**
- Button (primary, secondary, danger)
- Input (text, email, password, number)
- Select (dropdown)
- Checkbox
- Radio
- Modal
- Toast
- Spinner
- Skeleton
- Card
- Table
- Pagination
- Breadcrumb
- Badge
- Avatar

#### 6.5.2 Component Composition

```javascript
// Reusable ProductCard component
<ProductCard
  product={product}
  onAddToCart={handleAddToCart}
  loading={loading}
/>

// Reusable FormField component
<FormField
  label="Email"
  type="email"
  value={email}
  onChange={handleEmailChange}
  error={emailError}
  required
/>
```


---

## 7. Testing Requirements

### 7.1 Unit Tests

#### 7.1.1 Component Tests

**Tools:** Jasmine/Karma or Jest, Angular Testing Library

**Coverage Target:** > 80%

**Example Test:**

```ts
import { render, screen } from '@testing-library/angular';
import { Component, Input, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-product-card',
  template: `
  <div>
    <h3>{{product?.name}}</h3>
    <span>{{product?.price | currency}}</span>
    <button (click)="add.emit(product)">Add to cart</button>
  </div>`
})
class ProductCardComponent {
  @Input() product!: { id: number; name: string; price: number };
  add = new EventEmitter<any>();
}

describe('ProductCardComponent', () => {
  it('renders product name and price', async () => {
    await render(ProductCardComponent, {
      componentProperties: { product: { id: 1, name: 'iPhone 15', price: 999.99 } }
    });
    expect(screen.getByText('iPhone 15')).toBeTruthy();
    expect(screen.getByText('$999.99')).toBeTruthy();
  });
});
```

#### 7.1.2 Utility Function Tests

```javascript
describe('formatPrice', () => {
  it('formats price with currency symbol', () => {
    expect(formatPrice(999.99)).toBe('$999.99');
    expect(formatPrice(1000)).toBe('$1,000.00');
  });
});

describe('validateEmail', () => {
  it('validates email format', () => {
    expect(validateEmail('user@example.com')).toBe(true);
    expect(validateEmail('invalid-email')).toBe(false);
  });
});
```

#### 7.1.3 Redux/State Management Tests

```javascript
describe('authSlice', () => {
  it('handles login success', () => {
    const initialState = { isAuthenticated: false, user: null };
    const action = {
      type: 'auth/loginSuccess',
      payload: { id: '1', email: 'user@example.com' }
    };
    
    const newState = authReducer(initialState, action);
    
    expect(newState.isAuthenticated).toBe(true);
    expect(newState.user.email).toBe('user@example.com');
  });
});
```

### 7.2 Integration Tests

#### 7.2.1 API Integration Tests

**Tools:** Jest + HttpClientTestingModule

```ts
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

it('fetches products successfully', () => {
  TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
  const http = TestBed.inject(HttpClient);
  const httpMock = TestBed.inject(HttpTestingController);

  let payload: any;
  http.get('/api/v1/products').subscribe((d) => (payload = d));

  const req = httpMock.expectOne('/api/v1/products');
  req.flush({ content: [{ id: 1, name: 'Product 1', price: 99.99 }] });
  httpMock.verify();

  expect(payload.content.length).toBe(1);
});
```

#### 7.2.2 User Flow Tests

```ts
// Use Cypress or Playwright for Angular app user flows; selectors should match Angular templates
```

### 7.4 Test Coverage Goals

| Category | Target Coverage |
|----------|-----------------|
| Components | > 80% |
| Utilities | > 90% |
| State Management | > 85% |
| API Integration | > 75% |
| Overall | > 80% |

### 7.5 Testing Best Practices

- **Test Behavior, Not Implementation**: Test what users see and do
- **Use Data Attributes**: Use `data-testid` for element selection
- **Mock External APIs**: Use MSW or similar for API mocking
- **Test Error Cases**: Test both success and failure scenarios
- **Keep Tests Isolated**: Each test should be independent
- **Use Descriptive Names**: Test names should clearly describe what they test

---

## 8. Deployment & Environment Requirements

### 8.1 Environment Variables

#### 8.1.1 Development Environment

```ts
// src/environments/environment.ts
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080',
  apiVersion: '/api/v1',
  logLevel: 'debug',
  enableMockApi: false
};
```


### 8.2 Build Configuration

#### 8.2.1 Development Build

```bash
ng serve
# or
ng serve -c development
```

**Features:**
- Hot module reloading
- Source maps for debugging
- Mock API (optional)
- Verbose logging

#### 8.2.2 Production Build

```bash
ng build --configuration production
```

**Optimizations:**
- Minification
- Code splitting
- Tree shaking
- Asset optimization
- Source map generation (optional)

**Output:** `dist/<project-name>/` directory

#### 8.2.3 Build Configuration Example

```json
// angular.json (snippet)
{
  "projects": {
    "app": {
      "architect": {
        "build": {
          "configurations": {
            "production": {
              "budgets": [
                { "type": "initial", "maximumWarning": "200kb", "maximumError": "300kb" }
              ],
              "optimization": true,
              "sourceMap": false
            }
          }
        }
      }
    }
  }
}
```

---

### 9.4 Admin Features

#### 9.4.1 Dashboard

- **Sales Overview**: Total sales, revenue, orders
- **Top Products**: Best-selling products
- **Recent Orders**: Latest orders
- **User Activity**: Active users, new registrations

#### 9.4.2 Product Management

- **Bulk Upload**: Upload multiple products via CSV
- **Inventory Management**: Track and update inventory
- **Product Variants**: Manage product sizes, colors, etc.
- **Product Bundles**: Create product bundles

#### 9.4.3 Order Management

- **Order Fulfillment**: Manage order processing
- **Shipping Labels**: Generate shipping labels
- **Returns Management**: Handle product returns
- **Refunds**: Process refunds

#### 9.4.4 User Management

- **User List**: View all users
- **User Details**: View user profile and order history
- **User Roles**: Manage user roles and permissions
- **User Bans**: Ban/suspend users

### 9.5 Mobile App Considerations
- **Mobile-First Design**: Design for mobile first
- **Touch-Friendly**: Large buttons and touch targets
- **Mobile Navigation**: Bottom navigation or hamburger menu
- **Mobile Forms**: Optimized forms for mobile


---

## Appendix: API Quick Reference

### Authentication

| Method | Endpoint | Auth | Purpose |
|--------|----------|------|---------|
| POST | `/auth/login` | No | Login user |
| POST | `/auth/register` | No | Register user |
| POST | `/auth/refresh-token` | No | Refresh JWT token |
| POST | `/auth/logout` | Yes | Logout user |
| GET | `/auth/validate-token` | No | Validate token |
| GET | `/auth/user-id` | No | Extract user ID |

### Products

| Method | Endpoint | Auth | Purpose |
|--------|----------|------|---------|
| GET | `/products` | Yes | Get all products |
| GET | `/products/search` | Yes | Search products |
| GET | `/products/{id}` | Yes | Get product by ID |
| POST | `/products` | Yes | Create product |
| PUT | `/products/{id}` | Yes | Update product |
| PATCH | `/products/{id}/deactivate` | Yes | Deactivate product |
| DELETE | `/products/{id}` | Yes | Delete product |
| POST | `/products/upload-image` | Yes | Upload image |

### Categories

| Method | Endpoint | Auth | Purpose |
|--------|----------|------|---------|
| GET | `/categories` | Yes | Get all categories |
| GET | `/categories/{id}` | Yes | Get category by ID |
| POST | `/categories` | Yes | Create category |
| PUT | `/categories/{id}` | Yes | Update category |
| DELETE | `/categories/{id}` | Yes | Delete category |

### Cart

| Method | Endpoint | Auth | Purpose |
|--------|----------|------|---------|
| GET | `/api/v1/cart` | Yes | Get cart |
| POST | `/api/v1/cart/add` | Yes | Add to cart |
| PUT | `/api/v1/cart/update` | Yes | Update cart item |
| DELETE | `/api/v1/cart/remove` | Yes | Remove from cart |
| DELETE | `/api/v1/cart/clear` | Yes | Clear cart |


### Orders

| Method | Endpoint | Auth | Purpose |
|--------|----------|------|---------|
| POST | `/api/v1/orders/create` | Yes | Create order |
| GET | `/api/v1/orders/{orderUuid}` | Yes | Get order |
| PUT | `/api/v1/orders/update-status` | Yes | Update status |
| DELETE | `/api/v1/orders/cancel/{orderUuid}` | Yes | Cancel order |
| GET | `/api/v1/orders/history` | Yes | Get order history |

### Payment

| Method | Endpoint | Auth | Purpose |
|--------|----------|------|---------|
| POST | `/api/payment/initiate` | Yes | Initiate payment |
| GET | `/api/payment/order/{orderId}` | Yes | Get payment by order |
| GET | `/api/payment/user/latest` | Yes | Get latest payment |

### Checkout

| Method | Endpoint | Auth | Purpose |
|--------|----------|------|---------|
| POST | `/api/checkout/{orderId}` | Yes | Checkout |
| GET | `/api/checkout/order/{orderId}` | Yes | Get checkout |
| GET | `/api/checkout/user/{userId}/latest` | Yes | Get latest checkout |

---

## Document Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0 | 2025-01-10 | Initial document creation |
| 1.1 | 2025-12-11 | Converted to Angular-specific guidance (HttpClient, Interceptors, NgRx, Angular Router, environments, Angular builds/deploys) |

