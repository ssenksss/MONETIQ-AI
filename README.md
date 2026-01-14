# MONETIQ-AI
Instagram Profile Analyzer &amp; Monetization Planner , Ultra Premium tier -> Digital Product Creation

MONETIQ AI je full-stack aplikacija namenjena **micro-creatorima na Instagramu**, koja omogućava:

- Analizu javnog Instagram profila u realnom vremenu  
- Prikaz actionable predloga za poboljšanje sadržaja i engagement-a  
- Generisanje detaljnog 30-dnevnog monetization plana  
- Ultra Premium tier: simulacija pravljenja digitalnog proizvoda uz kontakt sa stručnim timom  


---

## Features

| Tier              | Funkcionalnost                                         | Napomena                                             |   
| ----------------- | ------------------------------------------------------ | ----------------------------------------------------  |
| **Free / Demo**   | 3 predloga za poboljšanje profila                      | Realno radi, REST API vraća JSON                      |                                                          
| **Premium**       | 30-dnevni monetization plan                            | Rule-based AI generator u Javi, detaljni plan         |                                                   
| **Ultra Premium** | Kontakt sa timom i digital product creation simulation | Samo simulacija, request se čuva u PostgreSQL / Redis,|                                                                               | frontend prikazuje poruku, ne pravi stvarni proizvod  |

---

## Tech Stack

| Komponenta | Tehnologija / alat     | Funkcija                                                                     |
| ---------- | ---------------------- | ---------------------------------------------------------------------------- |
| Backend    | Java (Spring Boot)     | REST API, analiza profila, monetization plan, Ultra Premium request handling |
| Frontend   | Vue3 + TypeScript      | Free & Premium UI                                                            |
| Frontend   | Angular                | Admin dashboard za Ultra Premium requeste                                    |
| Baze       | MongoDB                | Čuvanje profila, analiza, predlozi                                           |
| Baze       | PostgreSQL             | Ultra Premium requests                                                       |
| Cache      | Redis                  | Real-time request handling, simulacija Ultra Premium                         |
| GUI        | RedisInsight           | Vizuelni pregled podataka u Redis-u                                          |
| NLP / AI   | Stanford NLP / OpenNLP | Besplatna AI analiza teksta, hashtags, captions                              |

---

## Project Structure

```
/monetiq-ai
    /backend
        /controller    <-- REST API endpoints
        /service       <-- analiza profila, monetization plan, request handling
        /model         <-- User, Profile, Analysis, Plan, Request
        /repository    <-- MongoDB / PostgreSQL repositories
        /config        <-- DB, Redis, security configs
        application.properties
    /frontend-vue      <-- Free & Premium UI
    /frontend-angular  <-- Admin dashboard
```

---

## Getting Started

### Prerequisites

* Java 21
* IntelliJ IDEA
* MongoDB
* PostgreSQL
* Redis + RedisInsight
* Node.js + npm
* Vue3 + Angular CLI

### Setup

1. Clone repository:

```bash
git clone https://github.com/yourusername/monetiq-ai.git
cd monetiq-ai
```

2. Backend:

   * Open `backend` u IntelliJ
   * Konfiguriši `application.properties` za MongoDB, PostgreSQL i Redis
   * Run Spring Boot aplikaciju

3. Frontend Vue3:

```bash
cd frontend-vue
npm install
npm run serve
```

4. Frontend Angular (Admin dashboard):

```bash
cd frontend-angular
npm install
ng serve
```

---

## Usage

* Free tier: prikazuje 3 predloga za poboljšanje profila
* Premium tier: generiše 30-dnevni monetization plan
* Ultra Premium tier: omogućava slanje request-a za **digital product creation** i prikazuje poruku:

> “Our team will review your request.”

> ⚠️ Ultra Premium je simulacija poslovnog procesa, ne pravi stvarni digitalni proizvod.

---

## License

MIT License
