
# Online Shopping Application – Java Full Stack Project

## Project Overview
This project is an end-to-end online shopping platform designed to manage product listings, orders, and user management.  
It consists of an **Angular 14 frontend** and a **Spring Boot backend** split into two services: **Authentication Service** and **Content Service**.  

The application supports both **users (buyers)** and **admins (sellers)** with secure authentication, order management, and product management.

---
### Architecture Overview
shoppingapp/
│
├── SpringSecurityAuth       (Authentication Service – JWT)
├── SpringSecurityContent    (Content Service – Products & Orders)
└── frontend/shopping-app    (Angular 14 Frontend)

Services
Service	Description
Auth Service	Handles login, signup, JWT generation
Content Service	Product management, orders, watchlist, admin tools
Frontend	Angular UI for users/admin

Each backend service is deployed separately.
---

## Technologies Used

**Backend:**  
- Java, Spring Boot  
- Hibernate (HQL, Criteria API)  
- MySQL  
- Spring Security + JWT  
- Spring AOP  
- Spring Validation  
- Exception Handling (`@ControllerAdvice`)  
- Transaction Management  

**Frontend:**  
- Angular 14  
- Angular Material (or other UI libraries)  
- Reactive Forms with validation  
- Local Storage for shopping cart  

---

## Backend Features

### User (Customer/Buyer)
- **Registration & Login**
  - Prevents duplicate username/email
  - Custom exception `InvalidCredentialsException` for incorrect login
- **Home Page**
  - View all in-stock products
  - View product details (description, retail price only)
- **Purchasing**
  - Place orders (multiple items) with automatic stock deduction
  - Cancel order (stock restored, only if not completed)
  - Exception `NotEnoughInventoryException` for insufficient stock
- **Watchlist**
  - Add/remove products
  - View in-stock products in watchlist
- **Order Summary**
  - View all orders
  - View order detail (items, prices, placement time, status)
  - Top X recently bought products (excluding canceled orders)

### Admin (Seller)
- **Home Page**
  - Dashboard with orders (paginated, 5 per page)
  - Top 3 products by popularity
  - Product with the most profit
- **Product Management**
  - Add, edit, view products
  - Update description, retail & wholesale price, quantity
- **Order Management**
  - Complete or cancel orders (with stock adjustments)
  - View order details

---

## Frontend Features

### User
- Login/Register pages with validation
- User Home Page: table of orders, Cancel/View actions
- Order Detail Page: item details & cancel option
- Products Page: table of all products, Add to Cart/Add to Watchlist
- Product Detail Page

### Admin
- Admin Home Page: order table with Complete/Cancel/View
- Product Management Page: add/edit/view products
- Order Management Page: table with order actions
- Dashboard: most popular products, most profitable product, total sold items

---

## Additional Features
- DTOs for secure data transfer  
- JWT authentication for secure APIs  
- Layered architecture: `@RestController`, `@Service`, `@Repository`  
- HQL & Criteria queries for database access  
- Full transaction management for orders  
- Spring Validation & Exception Handling  

---

## Installation & Running

### Backend

1. Clone the repository:

   ```bash
   git clone https://github.com/jsnmui/shoppingapp.git
   cd shoppingapp/SpringSecurityAuth
   ```

2. Set environment variables for sensitive data before running the application:

   **Linux / macOS (bash/zsh):**

   ```bash
   export DB_URL=jdbc:mysql://localhost:3306/shopping_app
   export DB_USERNAME=<your-db-username>
   export DB_PASSWORD=<your-db-password>
   export JWT_SECRET=<your-jwt-secret>
   export SERVER_PORT=8888
   ```

   **Windows (Command Prompt):**

   ```cmd
   set DB_URL=jdbc:mysql://localhost:3306/shopping_app
   set DB_USERNAME=<your-db-username>
   set DB_PASSWORD=<your-db-password>
   set JWT_SECRET=<your-jwt-secret>
   set SERVER_PORT=8888
   ```

   **Windows (PowerShell):**

   ```powershell
   $env:DB_URL="jdbc:mysql://localhost:3306/shopping_app"
   $env:DB_USERNAME="<your-db-username>"
   $env:DB_PASSWORD="<your-db-password>"
   $env:JWT_SECRET="<your-jwt-secret>"
   $env:SERVER_PORT="8888"
   ```

3. Build and run the Spring Boot application:

   ```bash
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```

4. Use Postman (or any REST client) to test the endpoints.
---
### Angular Frontend

  ```bash
  cd frontend/shopping-app
  npm install
  ng serve
  ```

---

## Project Structure
```
/backend
    /auth-service
    /content-service
/frontend
    /shopping-app
```

---
## Live Deployment Link

**Frontend (Angular App)**

**User/Admin Login Page:**
https://shoppingapp-frontend-g8e2.onrender.com/login

**Test Accounts**

**Admin Account**
```
username: admin
password: 123456
```
**User Account**
```
username: user
password: 123456
```

---

