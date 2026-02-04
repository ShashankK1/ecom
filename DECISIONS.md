## Decision 1: Use Spring Boot with H2 (in-memory database)

Context:
The assignment required a working backend with persistence, but explicitly stated that a real database was not required.

Options Considered:

Option A: Use in-memory Java data structures (Maps / Lists)

Option B: Use an in-memory database (H2) with JPA

Choice:
Option B – Spring Boot with H2 and Spring Data JPA

## Decision 2: Keep business logic in the Service layer

Context:
The system includes non-trivial logic such as discount validation, coupon usage rules, order creation, and metrics calculation.

Options Considered:

Option A: Put logic directly in controllers

Option B: Move all business rules to service classes

Choice:
Option B – Service layer contains all business logic

Why:
Keeping controllers thin improves readability, testability, and separation of concerns.
This design allows isolated unit testing using Mockito without starting the Spring context and makes the code easier to reason about and extend.

## Decision 3: Generate discount codes after successful checkout (Nth order)

Context:
The discount system required generating a coupon after every Nth successful order.

Options Considered:

Option A: Generate coupon when checkout is initiated

Option B: Generate coupon only after a successful order is completed

Choice:
Option B – Generate coupon after order persistence

Why:
Generating coupons only after successful checkout ensures accuracy and prevents coupon generation from failed or abandoned checkouts.
This approach aligns better with real e-commerce systems and avoids edge cases.

## Decision 4: Store purchased item count in Order instead of using Cart

Context:
Admin metrics require total items purchased, but the cart is cleared after checkout.

Options Considered:

Option A: Calculate items purchased from the cart

Option B: Persist purchased item count in the Order entity

Choice:
Option B – Store totalItems in Order

Why:
The cart is a temporary structure and is cleared after checkout, making it unreliable for historical metrics.
Persisting item count in the Order entity ensures accurate reporting and avoids incorrect metrics (such as itemsPurchased = 0).

## Decision 5: Use Mockito-based unit tests instead of SpringBootTest

Context:
The assignment required unit tests for core business logic.

Options Considered:

Option A: Use @SpringBootTest with full context loading

Option B: Use Mockito with isolated service testing

Choice:
Option B – Mockito + JUnit 5 unit tests

## Decision 6: Single-use discount coupons

Context:
Discount codes must be validated and applied during checkout.

Options Considered:

Option A: Allow coupons to be reused multiple times

Option B: Mark coupons as used after one successful application

Choice:
Option B – Single-use coupons

## Decision 7: No authentication or authorization

Context:
The assignment focused on backend logic, not security.

Options Considered:

Option A: Add authentication/authorization

Option B: Skip authentication for simplicity

Choice:
Option B – No authentication
