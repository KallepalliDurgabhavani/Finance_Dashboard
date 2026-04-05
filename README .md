# Finance Dashboard System

A full-stack web application for managing financial records with role-based access control, built as a Backend Developer Internship assignment for Zorvyn FinTech Pvt. Ltd.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend Framework | Spring Boot 4.0.5 |
| Language | Java 21 |
| Database | MySQL 8 |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security 7 |
| Frontend | Thymeleaf (server-side templates) |
| Build Tool | Maven |
| IDE | Spring Tool Suite (STS) |

---

## Features

### User and Role Management
- Create and manage user accounts
- Three roles with distinct permissions: Viewer, Analyst, Admin
- Activate or deactivate user accounts
- Assign or change roles via the admin panel
- Passwords stored as BCrypt hashes — never plain text

### Financial Records Management
- Add income and expense transactions with amount, type, category, date, and notes
- View all active records sorted newest first
- Edit existing records
- Soft delete — records are flagged as deleted, not permanently removed
- Filter records by type (Income / Expense) or category

### Dashboard Summary
- Total income, total expenses, and net balance as stat cards
- Category-wise breakdown table
- Monthly income and expense trends

### Access Control
- URL-level security via Spring Security configuration
- Method-level security via @PreAuthorize annotations
- UI-level security via Thymeleaf sec:authorize tags
- Inactive accounts are blocked at login

---

## Role Permissions

| Feature | Viewer | Analyst | Admin |
|---------|--------|---------|-------|
| View dashboard | ✅ | ✅ | ✅ |
| View records | ✅ | ✅ | ✅ |
| Filter records | ✅ | ✅ | ✅ |
| Add new record | ❌ | ✅ | ✅ |
| Edit record | ❌ | ✅ | ✅ |
| Delete record | ❌ | ❌ | ✅ |
| View admin panel | ❌ | ❌ | ✅ |
| Manage users | ❌ | ❌ | ✅ |
| Assign roles | ❌ | ❌ | ✅ |
| Toggle user status | ❌ | ❌ | ✅ |

---

## Project Structure

src/main/java/com/example/finance_dashboard/
├── config/
│   ├── SecurityConfig.java              Spring Security configuration
│   └── DataInitializer.java             Seeds roles and default admin on startup
│
├── controller/
│   ├── AuthController.java              Login and registration
│   ├── DashboardController.java         Dashboard summary page
│   ├── RecordController.java            CRUD for financial records
│   └── AdminController.java             User management (admin only)
│
├── model/
│   ├── User.java                        User entity
│   ├── Role.java                        Role entity
│   └── FinancialRecord.java             Financial transaction entity
│
├── repository/
│   ├── UserRepository.java
│   ├── RoleRepository.java
│   └── FinancialRecordRepository.java   Custom JPQL aggregation queries
│
├── service/
│   ├── UserService.java                 Interface
│   ├── UserServiceImpl.java             Implements UserDetailsService for Spring Security
│   ├── FinancialRecordServiceImpl.java
│   └── DashboardService.java            Summary and aggregation logic
│
└── FinanceDashboardApplication.java
src/main/resources/
├── templates/
│   ├── auth/login.html
│   ├── auth/register.html
│   ├── dashboard.html
│   ├── records/list.html
│   ├── records/form.html
│   └── admin/users.html
├── static/css/style.css
└── application.properties

---

## Database Schema

| Table | Key Columns | Notes |
|-------|-------------|-------|
| users | id, username, email, password, active | Stores all user accounts |
| roles | id, name | ROLE_VIEWER, ROLE_ANALYST, ROLE_ADMIN |
| user_roles | user_id, role_id | Many-to-many join table |
| financial_records | id, amount, type, category, date, deleted, user_id | Soft delete via deleted flag |

Hibernate auto-creates and updates all tables on startup via `spring.jpa.hibernate.ddl-auto=update`.

---

## Prerequisites

Make sure the following are installed before running the project:

- Java 21 (JDK)
- MySQL 8.0 or higher
- Maven 3.8 or higher
- Spring Tool Suite (STS) 4 or any Java IDE

---

## Setup and Installation

### Step 1 — Clone or download the project
```bash
git clone https://github.com/your-username/finance-dashboard.git
cd finance-dashboard
```

### Step 2 — Create the MySQL database

Open MySQL Workbench or the MySQL terminal and run:
```sql
CREATE DATABASE finance_db;
```

### Step 3 — Configure application.properties

Open `src/main/resources/application.properties` and update the MySQL credentials:
```properties
# Server
server.port=8080

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/finance_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password_here
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.open-in-view=false

# Thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```

---

## Running the Application

### Option A — Run from STS

1. Open STS and go to **File → Import → Existing Maven Projects**
2. Select the project root folder and click **Finish**
3. Wait for Maven to download dependencies
4. Right-click the project → **Run As → Spring Boot App**
5. Watch the console — wait for: `Started FinanceDashboardApplication`
6. Open browser and go to: **http://localhost:8080/login**

### Option B — Run from Terminal
```bash
mvn spring-boot:run
```

Or build and run a JAR:
```bash
mvn clean package
java -jar target/finance-dashboard-0.0.1-SNAPSHOT.jar
```

### What Happens on First Startup

The `DataInitializer` class runs automatically and:
1. Creates three roles: `ROLE_VIEWER`, `ROLE_ANALYST`, `ROLE_ADMIN`
2. Creates the default admin account
3. Prints confirmation in the console

You will see this in the console log:
Default admin created: admin / admin123

---

## Default Login Credentials

| Username | Password | Role | Access |
|----------|----------|------|--------|
| admin | admin123 | ROLE_ADMIN | Full access to everything |

> New users who register via /register are assigned ROLE_VIEWER by default.
> The admin can upgrade their role from the User Management panel.

---

## URL Reference

| URL | Method | Access | Description |
|-----|--------|--------|-------------|
| /login | GET, POST | Public | Login page |
| /register | GET, POST | Public | Register new account |
| /dashboard | GET | All roles | Summary stats and charts |
| /records | GET | All roles | List all records with filter |
| /records/new | GET, POST | Admin, Analyst | Add new financial record |
| /records/edit/{id} | GET, POST | Admin, Analyst | Edit existing record |
| /records/delete/{id} | POST | Admin only | Soft delete a record |
| /admin/users | GET | Admin only | View all users |
| /admin/users/{id}/toggle | POST | Admin only | Activate or deactivate user |
| /admin/users/{id}/role | POST | Admin only | Assign role to user |
| /logout | POST | All roles | Sign out |

---

## Architecture and Design Decisions

### Layered Architecture

The application follows a strict 4-layer MVC pattern:

- **Controller** — handles HTTP requests, calls services, returns Thymeleaf template names
- **Service** — contains all business logic; injected via @Autowired
- **Repository** — Spring Data JPA interfaces with custom JPQL for aggregations
- **Model** — JPA entities mapped directly to MySQL tables

### Security Design

- Spring Security 7 with form-based login and session management
- BCryptPasswordEncoder (strength 10) for password hashing
- Three-level access control: URL rules in SecurityConfig, @PreAuthorize on controller methods, sec:authorize in Thymeleaf templates
- CSRF protection is enabled — all POST forms include a hidden CSRF token
- Session-based authentication is used instead of JWT because this is a server-rendered Thymeleaf application

### Soft Delete

Financial records are never permanently deleted. A `deleted` boolean flag is set to `true` instead. This preserves historical data for auditing and allows recovery. All list queries filter on `deleted = false`.

### Dependency Injection

All beans use `@Autowired` field injection. Constructor injection via Lombok's `@RequiredArgsConstructor` was avoided because STS was auto-completing constructor parameters incorrectly, causing null pointer errors at runtime in the Spring Boot 4 environment.

---

## Assumptions

1. New user registrations receive `ROLE_VIEWER` by default. Admins can upgrade roles from the admin panel.
2. Authentication uses HTTP sessions, not JWT — appropriate for a server-side Thymeleaf application.
3. The `DataInitializer` seeds default roles and the admin account on every startup but safely skips creation if they already exist.
4. Pagination is not implemented in this version to keep scope focused. It can be added by modifying repository methods to accept `Pageable` parameters.
5. No email verification is required for registration — the system is designed as an internal dashboard tool.
6. Spring Boot 4 with Spring Security 7 was used as per the environment. The `DaoAuthenticationProvider` constructor signature changed in Security 7 — `UserDetailsService` is now registered as a `@Bean` and Spring Security auto-detects it.

---

## Testing

### Sample Test Accounts

Insert sample users via MySQL after the app starts:
```sql
USE finance_db;

-- Sample analyst (password: password123)
INSERT INTO users (username, email, password, active) VALUES
('analyst1', 'analyst@finance.com',
'$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6G6sm', 1);

-- Sample viewer (password: password123)
INSERT INTO users (username, email, password, active) VALUES
('viewer1', 'viewer@finance.com',
'$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6G6sm', 1);

-- Assign roles
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username='analyst1' AND r.name='ROLE_ANALYST';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username='viewer1' AND r.name='ROLE_VIEWER';
```

### Test Scenarios

| Scenario | Expected Result |
|----------|----------------|
| Login as admin / admin123 | Redirects to dashboard with full access |
| Login as analyst1 / password123 | Can view, add, edit — no delete or admin |
| Login as viewer1 / password123 | Read-only, no add/edit/delete buttons |
| Access /records/new as viewer | 403 Forbidden |
| Access /admin/users as analyst | 403 Forbidden |
| Delete a record as admin | Disappears from list, still in DB with deleted=1 |
| Register a new user | Gets ROLE_VIEWER, can log in immediately |
| Deactivate viewer1 from admin panel | Login fails for that account |

---

## Submitted By

**Name:** Kallepalli Durga Bhavani  
**Email:** bhavanikallepalli93@gmail.com  
