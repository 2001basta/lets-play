# lets-play

A Spring Boot CRUD API for products, with JWT authentication and role-based access control (`USER` / `ADMIN`), backed by MongoDB.

- Java 17, Spring Boot 3.2.3
- Spring Web, Spring Security, Spring Data MongoDB, Bean Validation
- JWT auth via `jjwt`
- MongoDB (document store)
- Lombok

## 2. Run the app

```bash
./gradlew bootRun

```
The app starts on **http://localhost:8081**.

## 3. Test it

Open **http://localhost:8081/** in a browser. This serves a built-in test page (`src/main/resources/static/index.html`) that talks to the API on the same origin (no CORS setup needed). From there you can:

- Register a user, then log in (JWT is stored in `localStorage`)
- Create / edit / delete products
- View every request/response (method, path, status, JSON body) in the log panel
- List / delete users if the logged-in account has the `ADMIN` role


## Project structure

```
src/main/java/com/example/play/
├── controller/   REST endpoints (Auth, Product, User)
├── service/      Business logic
├── repository/   Spring Data MongoDB repositories
├── model/        MongoDB documents (User, Product)
├── dto/          Request/response records
├── security/     JWT filter/service, Spring Security config
└── exception/    Global exception handler

src/main/resources/
├── application.properties   Port, Mongo URI
└── static/index.html        Test frontend (served at "/")
```
