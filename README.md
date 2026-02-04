# Ecommerce Discount Store Backend

A Spring Boot backend application that supports cart management, checkout with discount coupons, automatic coupon generation, and admin metrics reporting.

This project was built as a backend-focused assignment to demonstrate API design, business logic, testing, and clean architecture.

---

## Features

- Add items to cart
- Checkout with or without discount coupons
- Automatic discount coupon generation after every Nth order
- Single-use discount coupons
- Admin metrics API (items purchased, revenue, discounts)
- In-memory persistence using H2
- Unit tests for core business logic

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database (in-memory)
- JUnit 5
- Mockito
- Maven (optional for local run)

---

## Setup Instructions

### Prerequisites

- Java 17+
- Git
- VS Code / IntelliJ IDEA (any Java IDE)

> Maven is optional. Tests and application can be run directly from the IDE.

---

### 1. Clone the Repository

```bash
git clone <your-github-repo-url>
cd <repo-folder-name>
```

### Open the project root (where pom.xml exists).

    If using VS Code:

    Install Extension Pack for Java

    (Optional) Install Lombok Annotations Support for VS Code

    Wait for dependency indexing to complete

### Run from IDE (Recommended)

    Open the main Spring Boot application class

    Right-click → Run Java

### Access H2 Database Console: http://localhost:8080/h2-console
