# MONETIQ-AI

**Instagram Profile Analyzer & Monetization Planner**
*Professional SaaS MVP – Version 1*

---

## Overview

**MONETIQ-AI** is a full-stack SaaS application designed for **Instagram micro-creators** who want a clear, structured, and technically transparent understanding of their profile performance and monetization potential.

The platform combines **profile analysis**, **actionable improvement suggestions**, and **deterministic monetization planning** within a single, coherent SaaS architecture. The focus is placed on **architectural clarity**, **business logic correctness**, and **realistic SaaS workflows**, deliberately avoiding exaggerated claims, opaque automation, or unverifiable analytics.

This project represents **Version 1 (MVP)**. Although developed in an academic context, the system is intentionally structured as a solid foundation for future **production-grade SaaS evolution**.

---

## Vision and Design Philosophy

The core vision of **MONETIQ-AI** is to demonstrate **how a creator-focused SaaS product should be structured**, rather than to replicate live social-media analytics.

Key design principles:

* deterministic and explainable backend logic
* explicit modeling of business workflows
* strict separation of concerns across system layers
* realistic subscription tiers and access control

The system does **not** rely on real-time Instagram data or unofficial scraping. Instead, it models how such a SaaS solution would be **architected, evaluated, and extended** in a professional environment.

---

## Evaluation

The application was evaluated through **functional testing** and **architectural review**, with emphasis on:

* correctness and predictability of backend logic
* clean separation between frontend, backend, and data layers
* correct enforcement of subscription tiers (Free, Premium, Ultra Premium)
* validity of modeled business workflows

The system uses a rule-based AI approach, where analyses and monetization plans are generated using predefined rules and heuristics, rather than machine learning models or generative AI systems.

All core user flows were manually tested, including API communication, data persistence, authentication, and UI rendering. System outputs are deterministic and explainable by design.

---

## Feature Set

### Subscription Tiers

| Tier              | Capabilities                 | Description                                                             |
| ----------------- | ---------------------------- | ----------------------------------------------------------------------- |
| **Free / Demo**   | Profile analysis             | Displays up to three concrete suggestions for profile improvement       |
| **Premium**       | Monetization planning        | Generates a structured 30-day monetization plan with daily action cards |
| **Ultra Premium** | Business workflow simulation | Simulates a digital product creation request and review process         |

---

## Premium Tier – Monetization Planning

The **Premium** tier focuses on **execution-oriented planning**, not automated content generation.

Users receive:

* a structured monetization document
* **30 daily action cards**, each defining a concrete task (posting strategy, engagement actions, optimization steps)

All outputs are produced using **rule-based backend logic**, ensuring transparency, repeatability, and explainability.

---

## Ultra Premium – Business Process Simulation

The **Ultra Premium** tier simulates a real-world **SaaS / agency workflow**, rather than automatically generating digital products.

The workflow includes:

* request submission from the user interface
* backend validation and persistence
* temporary processing via Redis
* administrative review through a dedicated Angular dashboard

Users are explicitly informed that this functionality represents a **business process simulation**, not automated digital product creation.

---

## System Architecture

MONETIQ-AI follows a **polyglot persistence architecture**, selecting each storage technology based on the nature of the data it manages.

### Backend – Spring Boot

The backend is implemented as a layered **Spring Boot** application and serves as the central orchestration layer.

Responsibilities include:

* execution of business and planning logic
* coordination across multiple data stores
* JWT-based authentication and tier-based authorization
* exposure of RESTful APIs consumed by frontend applications

Layered structure:

* **Controller layer** – REST API endpoints
* **Service layer** – core business logic and workflows
* **Repository layer** – MongoDB and PostgreSQL persistence
* **Configuration layer** – database, Redis, and security configuration

---

### Frontend – Angular (Admin Dashboard)

The **Angular** application represents the **administrative interface** of the system and is dedicated exclusively to handling **Ultra Premium workflows**.

Its responsibilities include:

* authenticated access for administrative users only
* visualization and management of Ultra Premium requests
* request filtering, status updates, and review actions
* interaction with secured backend endpoints

The Angular frontend does **not** expose Free or Premium functionality. Its sole purpose is to model a realistic **internal admin panel**, as commonly found in SaaS and agency systems.

This clear separation between user-facing and admin-facing frontends improves security, maintainability, and architectural clarity.

---

### MongoDB – Analytical & Semi-Structured Data

MongoDB is used for document-oriented data, including:

* profile analysis snapshots
* improvement suggestions
* monetization planning outputs
* analysis metadata

---

### PostgreSQL – Relational & Business-Critical Data

PostgreSQL stores structured and business-critical data:

* user accounts and authentication data
* user profiles and history
* monetization plans
* Ultra Premium request records

---

### Redis – Workflow State & Temporary Data

Redis is used for:

* temporary Ultra Premium request handling
* short-lived workflow state
* simulation of near-real-time processing

---

## Technology Stack

| Layer            | Technology           |
| ---------------- | -------------------- |
| Backend          | Java (Spring Boot)   |
| Frontend (User)  | Vue 3 + TypeScript   |
| Frontend (Admin) | Angular              |
| Databases        | MongoDB, PostgreSQL  |
| Cache / Workflow | Redis + RedisInsight |
| HTTP Client      | Axios                |

---

## Frontends

### User App (Vue 3)

The **Vue** frontend is the primary user-facing application. It covers:

* authentication and user onboarding
* Free tier profile analysis and suggestions
* Premium tier monetization plan generation and display
* Ultra Premium request submission (user-side)

### Admin Dashboard (Angular)

The **Angular** frontend serves as an internal admin panel for handling **Ultra Premium** requests. It provides:

* secure admin access (admin-only UI)
* request list/queue view with filtering and status selection
* request status updates (e.g., `PENDING`, `IN_REVIEW`, `APPROVED`, `REJECTED`)
* operational visibility into the Ultra Premium workflow

This separation intentionally models a realistic SaaS setup where the user product and internal operations tooling are delivered as **two distinct frontends**.

---

## Project Structure

```
/monetiq-ai
  /backend
    /controller
    /service
    /model
    /repository
    /config
    application.properties

  /frontend-vue        # Free, Premium & Ultra Premium user interface
  /frontend-angular    # Ultra Premium admin dashboard
```

---

## Setup

### Prerequisites

* Java 21
* IntelliJ IDEA
* MongoDB (Atlas or local)
* PostgreSQL
* Redis + RedisInsight
* Node.js & npm
* Vue CLI
* Angular CLI

---

### Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

---

### Frontend – Vue (User Interface)

```bash
cd frontend-vue
npm install
npm run dev
```

---

### Frontend – Angular (Admin Dashboard)

```bash
cd frontend-angular
npm install
ng serve
# or
npm run start
```

---

## Admin Access (Demo Credentials)

The Angular **Admin Dashboard** is protected and intended only for internal use.

For demonstration and evaluation purposes, the following **predefined admin accounts** are available:

| Role        | Email                                           | Password   |
| ----------- | ----------------------------------------------- | ---------- |
| **Admin**   | [admin@monetiq.ai](mailto:admin@monetiq.ai)     | admin123   |
| **Manager** | [manager@monetiq.ai](mailto:manager@monetiq.ai) | manager123 |

These credentials are **hardcoded for demo purposes only** and are not intended for production use.

---

## Local Application Endpoints

* **User Frontend (Vue)**
  [http://localhost:5173](http://localhost:5173)

* **Admin Dashboard (Angular)**
  [http://localhost:4200](http://localhost:4200)

* **Backend API (Spring Boot)**
  [http://localhost:8080](http://localhost:8080)

---

## Security

* JWT-based authentication
* Tier-based access control
* Protected endpoints for Premium and Ultra features

---

## What the SaaS Actually Does

MONETIQ-AI implements a **deterministic, tier-based SaaS workflow**:

The system logic is based on rule-based AI mechanisms, enabling deterministic, explainable, and reproducible outputs.

* **Free tier** provides rule-based profile evaluation with limited suggestions
* **Premium tier** generates a structured 30-day monetization plan
* **Ultra Premium tier** simulates a real-world digital product request workflow

When a user submits an Ultra Premium request, the system displays the following message:

> “Our team will review your request and contact you.”

⚠️ Ultra Premium represents a **business process simulation** and does not generate an actual digital product.

The system does **not** consume live Instagram data and does **not** perform automated content analysis. Its primary purpose is to model **realistic SaaS architecture and business logic** in a technically correct and extensible way.

---

## Limitations

* Instagram data is simulated
* No official Instagram API integration
* Planning logic is rule-based AI
* Ultra Premium functionality is a workflow simulation

---

## Future Work

Possible future extensions include:

* integration with official social-media APIs
* data-driven or ML-based planning logic
* background job processing
* production deployment (Docker, monitoring, CI/CD)

---

## License

MIT License

